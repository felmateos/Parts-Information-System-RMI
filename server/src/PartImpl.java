import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PartImpl implements Part {

    private int partCode;
    private String partName;
    private String partDesc;
    private String repoName;
    private List<PartQuant> subParts = new LinkedList<>();

    PartImpl(int partCode, String partName, String partDesc, String repoName, List<PartQuant> subParts) throws RemoteException{
        this.partCode = partCode;
        this.partName = partName;
        this.partDesc = partDesc;
        this.repoName = repoName;
        this.subParts = subParts;
    }

    PartImpl(int partCode, String partName, String partDesc, String repoName) throws RemoteException{
        this.partCode = partCode;
        this.partName = partName;
        this.partDesc = partDesc;
        this.repoName = repoName;
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
    public String getRepoName() throws RemoteException{
        return repoName;
    }

    @Override
    public Remote getSubParts() throws RemoteException{
        return UnicastRemoteObject.exportObject((Remote) this.subParts, 100);
    }

    public boolean setSubParts(List<PartQuant>  subParts) throws RemoteException {
        try {
            this.subParts = subParts;
            return true;
        } catch (Exception e) { return false; }
    }

    public Remote getSubPart(int index) throws RemoteException {
        return UnicastRemoteObject.exportObject(this.subParts.get(index), 100);
    }

    @Override
    public boolean unexport() throws RemoteException {
        return UnicastRemoteObject.unexportObject(this, true);
    }

    @Override
    public String printInfo() throws RemoteException {
        return ("   " + this.partCode + 
                "   |" + this.partName + 
                "| " + this.partDesc + 
                " |    " + this.repoName +
                "    | " + this.subParts);
    }

}