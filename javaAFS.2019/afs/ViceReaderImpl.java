// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.rmi.*;
import java.rmi.server.*;

import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "./AFSDir/";
    private String fileName;
    private RandomAccessFile file;

    public ViceReaderImpl(String fileName /* añada los parámetros que requiera */)
		    throws IOException, RemoteException {
        this.fileName = fileName;
        file = new RandomAccessFile(AFSDir + fileName, "r");
    }
    public String getFileName()  throws RemoteException {
        return fileName;
    }
    public byte[] read(int tam) throws IOException, RemoteException {
        byte[] bytes = new byte[tam];
        if(file.read(bytes) < 0) return null;
        return bytes;
    }
    public void close() throws IOException, RemoteException {
        file.close();
    }
}       

