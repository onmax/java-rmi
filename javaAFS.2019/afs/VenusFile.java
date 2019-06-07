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
    private boolean modified;

    public VenusFile(Venus venus, String fileName, String mode)
            throws RemoteException, IOException, FileNotFoundException {
        this.venus = venus;
        this.fileName = fileName;
        this.mode = mode;
        this.modified = false;
        String filePath = "./" + cacheDir + fileName;

        venus.getSrv().addToCallbacksMap(this.fileName, this.venus.getVenusCBImpl());
    
        // Checks if files is already in cache
        File f = new File(filePath);
        if (f.exists())
            downloadFile(filePath);
            
        file = new RandomAccessFile(filePath, this.mode);
    }

    private void downloadFile(String filePath) throws RemoteException, IOException {

        boolean fileExistsInServer = venus.getSrv().fileExists(fileName);
        if (!fileExistsInServer && this.mode.equals("rw"))
            return;

        ViceReader viceReader = (ViceReader) venus.getSrv().download(this.fileName);
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
        this.modified = true;
        file.write(b);
    }

    public void seek(long p) throws RemoteException, IOException {
        file.seek(p);
    }

    public void setLength(long l) throws RemoteException, IOException {
        this.modified = true;
        file.setLength(l);
    }

    public void close() throws RemoteException, IOException {
        if (mode.equals("rw") && this.modified) {
            // Eliminamos el contenido del fichero en el servidor
            ViceWriter viceWriter = (ViceWriter) venus.getSrv().upload(fileName, this.venus.getVenusCBImpl());
            viceWriter.removeContent();

            // Leemos el contenido del fichero de bloque en bloque y lo escribimos en el
            // servidor
            file.seek(0);
            int nblocks = (int) file.length() / venus.getBlockSize();
            byte[] content = new byte[venus.getBlockSize()];
            for (int i = 0; i < nblocks; i++) {
                read(content);
                viceWriter.write(content);
            }

            int lastBlockSize = (int) file.length() % venus.getBlockSize();
            if (lastBlockSize > 0) {
                content = new byte[lastBlockSize];
                read(content);
                viceWriter.write(content);
            }

            viceWriter.close();
        }
        file.close();
    }
}
