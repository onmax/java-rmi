// Interfaz de servidor que define los métodos remotos para iniciar
// la carga y descarga de ficheros
package afs;

import java.rmi.*;
import java.io.*;

public interface Vice extends Remote {
      public ViceReader download(String fileName) throws IOException, RemoteException;
      public ViceWriter upload(String fileName) throws IOException, RemoteException;
      public boolean fileExists(String fileName) throws RemoteException;
}
