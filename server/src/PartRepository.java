import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartRepository extends Remote {
    
    boolean insertPart(int partCode, String partName, String partDesc, String repoName) throws RemoteException;
    int[] getAllPartsCodes() throws RemoteException;
    Remote getPartRemoteByCode(int partCode) throws RemoteException;
    List<Part> getAllParts() throws RemoteException;
    int getPartsQuant() throws RemoteException;

    // Apenas para testes (remover dps)
    String getName() throws RemoteException;

}