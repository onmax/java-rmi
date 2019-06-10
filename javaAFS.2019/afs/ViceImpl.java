// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
  private static final String AFSDir = "./AFSDir/";
  private static HashMap<String, ArrayList<VenusCB>> callbacksMap;
  private static LockManager lockManager;

  public ViceImpl() throws IOException, RemoteException {
    ViceImpl.lockManager = new LockManager();
    ViceImpl.callbacksMap = new HashMap<String, ArrayList<VenusCB>>();
  }

  public void addToCallbacksMap(String fileName, VenusCB venusCB) throws RemoteException {
    if(!ViceImpl.callbacksMap.containsKey(fileName)) {
      ViceImpl.callbacksMap.put(fileName, new ArrayList<VenusCB>());
    }
    ViceImpl.callbacksMap.get(fileName).add(venusCB);
  }

  public ViceReader download(String fileName) throws IOException, RemoteException {
    return new ViceReaderImpl(fileName, ViceImpl.lockManager);
  }

  public ViceWriter upload(String fileName, VenusCB venusCB) throws IOException, RemoteException {
    ArrayList<VenusCB> callbacks = ViceImpl.callbacksMap.get(fileName);
    int index = -1;
    for(int i = 0; i < callbacks.size(); i++) {
      if(!callbacks.get(i).equals(venusCB))
        callbacks.get(i).invalidate("./Cache/" + fileName);
      else
        index = i;
    }
    ViceImpl.callbacksMap.get(fileName).remove(index);
    
    return new ViceWriterImpl(fileName, ViceImpl.lockManager);
  }

  public boolean fileExists(String fileName) throws RemoteException {
    File f = new File(AFSDir + fileName);
    return f.exists();
  }

}
