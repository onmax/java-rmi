// Clase de cliente que define la interfaz a las aplicaciones.
// Proporciona la misma API que RandomAccessFile.
package afs;

import java.rmi.*;
import java.io.*;

public class VenusFile {
    public static final String cacheDir = "Cache/";
    private RandomAccessFile file;
    private String fileName;
    private String mode;
    private Venus venus;

    public VenusFile(Venus venus, String fileName, String mode)
            throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;
        this.fileName = fileName;
        this.mode = mode;
        String filePath = "./" + cacheDir + fileName;

        // Checks if files is already in cache
        File f = new File(filePath);
        if (!f.exists() && this.mode.equals("r"))
            downloadFile(filePath);
        file = new RandomAccessFile(filePath, this.mode);
    }

    private void downloadFile(String filePath) throws RemoteException, IOException {
        ViceReader viceReader = (ViceReader) venus.getSrv().download(fileName, mode, venus.getVenusCBImpl());
        RandomAccessFile f = new RandomAccessFile(filePath, "rw");
        long size = viceReader.getFileSize();
        byte[] buffer;
        while (true) {
            buffer = viceReader.read(venus.getBlockSize());
            if (buffer == null)
                break;
            for (int i = 0; i < buffer.length && size-- > 0; i++)
                f.write(buffer[i]);
        }
        viceReader.close();
        f.close();
    }

    public int read(byte[] b) throws RemoteException, IOException {
        return file.read(b);
    }

    public void write(byte[] b) throws RemoteException, IOException {
        file.write(b);
    }

    public void seek(long p) throws RemoteException, IOException {
        file.seek(p);
    }

    public void setLength(long l) throws RemoteException, IOException {
        file.setLength(l);
    }

    public void close() throws RemoteException, IOException {
        if (this.mode.equals("rw")) {
            file.seek(0);
            int fileSize = (int) file.length();
            byte[] content = new byte[fileSize];
            read(content);
            uploadFile(content);
        }
        file.close();
    }

    private void uploadFile(byte[] content) throws RemoteException, IOException {
        ViceWriter viceWriter = (ViceWriter) venus.getSrv().upload(fileName, venus.getVenusCBImpl());
        viceWriter.write(content);
        viceWriter.close();
    }
}
