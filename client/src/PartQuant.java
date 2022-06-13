import java.rmi.Remote;
import java.rmi.RemoteException;

interface PartQuant extends Remote {

    Part getPart() throws RemoteException;
    int getQuant() throws RemoteException;

}