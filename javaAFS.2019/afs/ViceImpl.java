// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
  private static final String AFSDir = "./AFSDir/";

  public ViceImpl() throws IOException, RemoteException {
  }

  public ViceReader download(String fileName) throws IOException, RemoteException {
    return new ViceReaderImpl(fileName);
  }

  public ViceWriter upload(String fileName) throws IOException, RemoteException {
    return new ViceWriterImpl(fileName);
  }

  public boolean fileExists(String fileName) throws RemoteException {
    File f = new File(AFSDir + fileName);
    return f.exists();
  }

}
