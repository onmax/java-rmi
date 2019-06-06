// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile file;

    public ViceWriterImpl(String fileName /* añada los parámetros que requiera */)
		    throws IOException, RemoteException {
        file = new RandomAccessFile(AFSDir + fileName, "rw");
    }

    public void write(byte [] b) throws IOException, RemoteException {
        file.write(b);
    }

    public void close() throws IOException, RemoteException {
        file.close();
    }
}       

