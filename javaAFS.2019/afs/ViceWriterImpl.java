// Implementación de la interfaz de servidor que define los métodos remotos
// para completar la carga de un fichero
package afs;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class ViceWriterImpl extends UnicastRemoteObject implements ViceWriter {
    private static final String AFSDir = "AFSDir/";
    private RandomAccessFile file;
    ViceImpl viceImpl;
    ReentrantReadWriteLock lock;

    public ViceWriterImpl(String fileName, ViceImpl viceImpl)
		    throws IOException, RemoteException {
        file = new RandomAccessFile(AFSDir + fileName, "rw");
        this.fileName = fileName;
        this.viceImpl = viceImpl;
    }

    public void write(byte [] b) throws IOException, RemoteException {
        lock = ViceImpl.viceImpl.lockManager.bind(this.fileName);
        lock.writeLock().lock();
        file.setLength(0);
        file.write(b);
    }

    public void close() throws IOException, RemoteException {
        lock.writeLock().unlock();
        file.close();
    }
}       

