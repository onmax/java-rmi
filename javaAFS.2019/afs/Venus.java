// Clase de cliente que inicia la interacción con el servicio de
// ficheros remotos
package afs;

import java.rmi.*; 
import java.rmi.server.*;

public class Venus {
    static private String registryHost;
    static private String registryPort;
    static private int blockSize;
    static private Vice srv;
    static private VenusCBImpl venusCBImpl;


    public Venus() {
        registryHost = System.getenv("REGISTRY_HOST");
        registryPort = System.getenv("REGISTRY_PORT");
        blockSize = Integer.parseInt(System.getenv("BLOCKSIZE"));
        venusCBImpl = new VenusCBImpl();
        
        try {
            srv = (Vice) Naming.lookup("//" + registryHost + ":" + registryPort + "/AFS");
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        } catch (Exception e) {
            System.err.println("Excepcion en cliente:");
            e.printStackTrace();
        }
    }

    public String getRegistryHost() {
        return registryHost;
    }

    public String getRegistryPort() {
        return registryPort;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public Vice getSrv() {
        return srv;
    }

    public VenusCBImpl getVenusCBImpl() {
        return venusCBImpl;
    }
}

