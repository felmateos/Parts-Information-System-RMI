import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

interface Part extends Remote{

    int getPartCode() throws RemoteException;
    String getPartName() throws RemoteException;
    String getPartDesc() throws RemoteException;
    List<PartQuant> getSubParts() throws RemoteException;

    boolean unexport() throws RemoteException;
}