import java.rmi.*;
import java.rmi.server.*;

class ServicioEcoImpl extends UnicastRemoteObject implements ServicioEco {
    ServicioEcoImpl() throws RemoteException {
    }
    public String eco(String s) throws RemoteException {
        return s.toUpperCase() + "JEJE";
    }
    public String reverse(String s) throws RemoteException {
        StringBuilder input1 = new StringBuilder(); 
        input1.append(s); 
        input1 = input1.reverse(); 
        return input1.toString();
    }
}
    