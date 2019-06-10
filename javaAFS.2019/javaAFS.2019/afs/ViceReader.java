// Interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.rmi.*;
import java.io.*;

public interface ViceReader extends Remote {
    public byte[] read(int tam) throws IOException, RemoteException;
    public void close() throws IOException, RemoteException;
    public long getFileSize() throws IOException, RemoteException;
    /* añada los métodos remotos que requiera */
}       

