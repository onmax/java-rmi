
import java.rmi.*;

interface ServicioEco extends Remote {
	String eco (String s) throws RemoteException;
	String reverse (String s) throws RemoteException;
}
