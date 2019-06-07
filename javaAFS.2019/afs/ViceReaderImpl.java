// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "./AFSDir/";
    private RandomAccessFile file;

    public ViceReaderImpl(String fileName) throws IOException, RemoteException {
        file = new RandomAccessFile(AFSDir + fileName, "r");
    }

    public byte[] read(int tam) throws IOException, RemoteException {
        byte[] bytes = new byte[tam];
        return file.read(bytes) < 0 ? null : bytes;
    }

    public long getFileSize() throws IOException, RemoteException {
        return file.length();
    }

    public void close() throws IOException, RemoteException {
        file.close();
    }
}
