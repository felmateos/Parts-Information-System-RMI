import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PartImpl implements Part {

    private int partCode;
    private String partName;
    private String partDesc;
    private String repoName;
    private List<PartQuant> subParts;

    PartImpl(int partCode, String partName, String partDesc, String repoName) throws RemoteException {
        this.partCode = partCode;
        this.partName = partName;
        this.partDesc = partDesc;
        this.repoName = repoName;
    }

    public int getPartCode() throws RemoteException {
        return partCode;
    }

    public String getPartName() throws RemoteException {
        return partName;
    }

    public String getPartDesc() throws RemoteException {
        return partDesc;
    }

    public String getRepoName() throws RemoteException {
        return repoName;
    }

    public Remote createPartQuantRemote(int quant) throws RemoteException {
        return UnicastRemoteObject.exportObject(new PartQuantImpl(this, quant), 1001);
    }

    public Remote getSubPartsRemote() throws RemoteException {
        return UnicastRemoteObject.exportObject((Remote) this.subParts, 1000);
    }

    public boolean setSubParts(List<PartQuant>  subParts) {
        try {
            this.subParts = subParts;
            return true;
        } catch (Exception e) { return false; }
    }

    public Remote getSubPartRemote(int index) throws RemoteException {
        return UnicastRemoteObject.exportObject(this.subParts.get(index), 100);
    }

    public boolean unexport() throws RemoteException {
        return UnicastRemoteObject.unexportObject(this, true);
    }

    public String getInfo() throws RemoteException {
        String subParts = "";
        if (this.subParts != null) {
            for (PartQuant pq : this.subParts) {
                subParts += "[" +pq.getPart().getPartCode() + "," + pq.getPart().getRepoName() + "," + pq.getQuant() + "] ";
            }
        }
        return ("   " + this.partCode + 
                "   |" + this.partName + 
                "| " + this.partDesc + 
                " |    " + this.repoName +
                "    | " + subParts);
    }

}