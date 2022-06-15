import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartRepository extends Remote {
    
    boolean insertPart(String partName, String partDesc, String repoName, List<PartQuant> subParts) throws RemoteException;
    int[] getAllPartsCodes() throws RemoteException;
    Remote getPartRemoteByCode(int partCode) throws RemoteException;
    List<Part> getAllParts() throws RemoteException;
    int getPartsQuant() throws RemoteException;
    String getName() throws RemoteException;

}