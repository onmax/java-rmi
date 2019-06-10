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

  public ViceReader download(String fileName, String mode, VenusCB venusCB) throws IOException, RemoteException {
    if (!fileExists(fileName) && mode.equals("rw")) {
      addCallback(fileName, venusCB);
      return null;
    } else {
      ViceReaderImpl viceReaderImpl = new ViceReaderImpl(fileName, ViceImpl.lockManager);
      addCallback(fileName, venusCB);
      return viceReaderImpl;
    }
  }

  synchronized private void addCallback(String fileName, VenusCB venusCB) {
    if (!ViceImpl.callbacksMap.containsKey(fileName)) {
      ViceImpl.callbacksMap.put(fileName, new ArrayList<VenusCB>());
    }
    ViceImpl.callbacksMap.get(fileName).add(venusCB);
  }

  public ViceWriter upload(String fileName, VenusCB venusCB) throws IOException, RemoteException {
    ArrayList<VenusCB> callbacks = ViceImpl.callbacksMap.get(fileName);
    for (int i = 0; i < callbacks.size(); i++) {
      if (!callbacks.get(i).equals(venusCB))
        callbacks.get(i).invalidate("./Cache/" + fileName);
    }
    return new ViceWriterImpl(fileName, ViceImpl.lockManager);
  }

  public boolean fileExists(String fileName) throws RemoteException {
    File f = new File(AFSDir + fileName);
    return f.exists();
  }
}
