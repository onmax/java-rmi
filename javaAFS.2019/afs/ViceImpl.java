// Implementación de la interfaz de servidor que define los métodos remotos
// para iniciar la carga y descarga de ficheros
package afs;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceImpl extends UnicastRemoteObject implements Vice {
    public ViceImpl() throws IOException, RemoteException {
    }
    public ViceReader download(String fileName /* añada los parámetros que requiera */)
          throws IOException, RemoteException {
        return new ViceReaderImpl(fileName);
    }
    public ViceWriter upload(String fileName /* añada los parámetros que requiera */)
          throws RemoteException {
        return null;
    }
}
