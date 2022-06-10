import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

interface Part extends Remote {

    int getPartCode() throws RemoteException;
    String getPartName() throws RemoteException;
    String getPartDesc() throws RemoteException;
    String getRepoName() throws RemoteException;
    List<PartQuant> getSubParts() throws RemoteException;
    boolean unexport() throws RemoteException;
    String printInfo() throws RemoteException;
}