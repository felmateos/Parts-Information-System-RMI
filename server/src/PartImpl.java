import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PartImpl implements Part {

    int partCode;
    String partName;
    String partDesc;
    List<PartQuant> subParts;

    PartImpl(int partCode, String partName, String partDesc, List<PartQuant> subParts) throws RemoteException{
        this.partCode = partCode;
        this.partName = partName;
        this.partDesc = partDesc;
        this.subParts = subParts;
    }

    @Override
    public int getPartCode() throws RemoteException{
        return partCode;
    }

    @Override
    public String getPartName() throws RemoteException{
        return partName;
    }

    @Override
    public String getPartDesc() throws RemoteException{
        return partDesc;
    }

    @Override
    public List<PartQuant> getSubParts() throws RemoteException{
        return subParts;
    }

    @Override
    public boolean unexport() throws RemoteException {
        return UnicastRemoteObject.unexportObject(this, true);
    }

}