// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
  static LockManager lockManager;
  static HashMap<String,  ArrayList<VenusCBImpl>> map = new HashMap<String, ArrayList<VenusCBImpl>>();

  public ViceImpl() throws IOException, RemoteException {
    ViceImpl.lockManager = new LockManager();
  }

  private addCallback(String fileName) {
    if(!ViceImpl.map.containsKey(fileName)) {
      ViceImpl.map.put(fileName, new ArrayList<>());
    }
    ViceImpl.map.get(fileName).add(new VenusCBImpl());
  }

  public ViceReader download(String fileName, String mode, VenusCBImpl venusCBImpl)
      throws IOException, RemoteException {
    addCallback(fileName);
    return new ViceReaderImpl(fileName, mode, venusCBImpl);
  }

  public ViceWriter upload(String fileName, VenusCBImpl venusCBImpl) throws IOException, RemoteException {
    return new ViceWriterImpl(fileName, venusCBImpl);
  }
}
