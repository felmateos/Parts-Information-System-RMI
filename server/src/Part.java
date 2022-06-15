import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

interface Part extends Remote, Serializable {

    int getPartCode() throws RemoteException;
    String getPartName() throws RemoteException;
    String getPartDesc() throws RemoteException;
    String getRepoName() throws RemoteException;

    Remote createPartQuantRemote(int quant) throws RemoteException;

    boolean setSubParts(List<PartQuant> subParts) throws RemoteException;

    boolean unexportPart() throws RemoteException;

    String getInfo() throws RemoteException;
}