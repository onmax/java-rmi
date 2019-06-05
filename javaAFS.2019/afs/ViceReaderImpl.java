// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;
import java.rmi.*;
import java.rmi.server.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.*;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "./AFSDir/";
    private RandomAccessFile file;
    private String fileName;
    ViceImpl viceImpl;
    ReentrantReadWriteLock lock;

    public ViceReaderImpl(String fileName, String mode, ViceImpl viceImpl)
		    throws IOException, RemoteException {
        this.file = new RandomAccessFile(AFSDir + fileName, mode);
        this.fileName = fileName;
        this.viceImpl = viceImpl;
    }
    
    public byte[] read(int tam) throws IOException, RemoteException {
        lock = ViceImpl.viceImpl.lockManager.bind(this.fileName);
        lock.readLock().lock();
        byte[] bytes = new byte[tam];
        return this.file.read(bytes) < 0 ? null : bytes;
    }

    public long getFileSize() throws IOException, RemoteException {
        return this.file.length();
    }
    
    public void close() throws IOException, RemoteException {
        lock.readLock().unlock();
        this.file.close();
    }
}       

