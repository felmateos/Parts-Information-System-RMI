import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartRepository extends Remote{
    
    void insertPart(Part part) throws RemoteException;
    Remote getPartByCode(int partCode) throws RemoteException;
    List<Part> getAllParts() throws RemoteException;


    // Apenas para testes (remover dps)
    String getName() throws RemoteException;

}
