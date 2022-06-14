import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

interface PartQuant extends Remote, Serializable {

    Part getPart() throws RemoteException;
    int getQuant() throws RemoteException;

}