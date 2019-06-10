// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile file;
    private String fileName;
    ReentrantReadWriteLock lock;
    LockManager lockManager;

    public ViceWriterImpl(String fileName, LockManager lockManager) throws IOException, RemoteException {
        file = new RandomAccessFile(AFSDir + fileName, "rw");
        this.fileName = fileName;
        this.lockManager = lockManager;
        this.lock = this.lockManager.bind(fileName);
    }

    public void removeContent() throws IOException, RemoteException {
        this.lock.writeLock().lock();
        file.setLength(0);
        this.lock.writeLock().unlock();
    }

    public void write(byte[] b) throws IOException, RemoteException {
        this.lock.writeLock().lock();
        file.write(b);
        this.lock.writeLock().unlock();
    }

    public void close() throws IOException, RemoteException {
        this.lockManager.unbind(this.fileName);
        file.close();
    }
}
