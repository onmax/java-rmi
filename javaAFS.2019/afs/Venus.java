// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.rmi.*; 
import java.rmi.server.*;
import afs.VenusCBImpl;

public class Venus {
    static private String registryHost;
    static private String registryPort;
    static private int blockSize;
    static private Vice srv;
    static private VenusCBImpl venusCBImpl;


    public Venus() throws RemoteException {
        Venus.registryHost = System.getenv("REGISTRY_HOST");
        Venus.registryPort = System.getenv("REGISTRY_PORT");
        Venus.blockSize = Integer.parseInt(System.getenv("BLOCKSIZE"));
        Venus.venusCBImpl = new VenusCBImpl();
        try {
            srv = (Vice) Naming.lookup("//" + Venus.registryHost + ":" + Venus.registryPort + "/AFS");
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        } catch (Exception e) {
            System.err.println("Excepcion en cliente:");
            e.printStackTrace();
        }
    }

    public String getRegistryHost() {
        return Venus.registryHost;
    }

    public String getRegistryPort() {
        return Venus.registryPort;
    }

    public int getBlockSize() {
        return Venus.blockSize;
    }

    public Vice getSrv() {
        return Venus.srv;
    }

    public VenusCBImpl getVenusCBImpl() {
        return Venus.venusCBImpl;
    }
}

