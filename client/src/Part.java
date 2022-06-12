import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

interface Part extends Remote {

    int getPartCode() throws RemoteException;
    String getPartName() throws RemoteException;
    String getPartDesc() throws RemoteException;
    String getRepoName() throws RemoteException;
    Remote getSubParts() throws RemoteException;
    boolean setSubParts(List<PartQuant> subParts) throws RemoteException;
    Remote getSubPart(int index) throws RemoteException;
    boolean unexport() throws RemoteException;
    String printInfo() throws RemoteException;
}