// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.rmi.*;
import java.rmi.server.*;

import java.io.RandomAccessFile;
import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "AFSDir/";
    private static String fileName;
    private static RandomAccessFile file;

    public ViceReaderImpl(String fileName /* añada los parámetros que requiera */)
		    throws IOException, RemoteException {
        this.fileName = fileName;
        this.file = new RandomAccessFile(fileName, "r");
    }
    public byte[] read(int tam) throws IOException, RemoteException {
        byte[] bytes = new byte[tam];
        file.read(bytes);
        return bytes;
    }
    public void close() throws IOException, RemoteException {
        file.close();
    }
}       

