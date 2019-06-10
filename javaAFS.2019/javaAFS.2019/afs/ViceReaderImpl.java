// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la descarga de un fichero
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceReaderImpl extends UnicastRemoteObject implements ViceReader {
    private static final String AFSDir = "./AFSDir/";
    private RandomAccessFile file;
    private String fileName;
    ReentrantReadWriteLock lock;
    LockManager lockManager;

    public ViceReaderImpl(String fileName, LockManager lockManager) throws IOException, RemoteException {
        file = new RandomAccessFile(AFSDir + fileName, "r");
        this.fileName = fileName;
        this.lockManager = lockManager;
        this.lock = this.lockManager.bind(fileName);
    }

    public byte[] read(int tam) throws IOException, RemoteException {
        byte[] bytes = new byte[tam];

        // Sección crítica
        this.lock.readLock().lock();
        int nread = this.file.read(bytes);
        this.lock.readLock().unlock();

        return nread < 0 ? null : bytes;
    }

    public long getFileSize() throws IOException, RemoteException {
        this.lock.readLock().lock();
        long size = this.file.length();
        this.lock.readLock().unlock();
        return size;
    }

    public void close() throws IOException, RemoteException {
        this.lockManager.unbind(this.fileName);
        file.close();
    }
}
