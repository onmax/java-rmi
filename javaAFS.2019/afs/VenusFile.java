// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*; 
import java.io.*;

public class VenusFile {
    public static final String cacheDir = "Cache/";
    private RandomAccessFile file;
    private Venus venus;

    public VenusFile(Venus venus, String fileName, String mode) throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;

        String filePath = "./" + cacheDir + fileName;
        
        // Checks if files is already in cache
        File f = new File(filePath);
        if(f.createNewFile()) {
            // File is not in cache, then file is downloaded and save it in cache directory
            file = new RandomAccessFile(filePath, "rw");
            downloadFile(fileName);
            file.close();
        }
        
        file = new RandomAccessFile(filePath, mode);
    }

    public int read(byte[] b) throws RemoteException, IOException {
        return 0;
    }

    public void write(byte[] b) throws RemoteException, IOException {
        return;
    }

    public void seek(long p) throws RemoteException, IOException {
        return;
    }

    public void setLength(long l) throws RemoteException, IOException {
        return;
    }

    public void close() throws RemoteException, IOException {
        return;
    }

    private void downloadFile(String fileName) throws RemoteException, IOException {
        ViceReader viceReader = (ViceReader) venus.getSrv().download(fileName);
        byte[] filecontent = new byte[venus.getBlockSize()];
        while(true) {
            filecontent = viceReader.read(venus.getBlockSize());
            if(filecontent == null) {
                break;
            }
            for(int i= 0; i < filecontent.length && filecontent[i] != 0; i++)
                file.write(filecontent[i]);
        }
    }
}

