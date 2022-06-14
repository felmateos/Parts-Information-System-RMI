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
    private boolean running = false;

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
        waitQueue();
        running = true;
        Remote r = UnicastRemoteObject.exportObject(new PartQuantImpl(this, quant), 1000);
        running = false;
        return r;
    }

    public boolean setSubParts(List<PartQuant>  subParts) {
        try {
            this.subParts = subParts;
            return true;
        } catch (Exception e) { return false; }
    }

    public boolean unexportPart() throws RemoteException {
        waitQueue();
        running = true;
        boolean r = UnicastRemoteObject.unexportObject(this, true);
        running = false;
        return r;
    }

    public String getInfo() throws RemoteException {
        waitQueue();
        running = true;
        String subParts = "";
        if (this.subParts != null) {
            for (PartQuant pq : this.subParts) {
                subParts += "[" +pq.getPart().getPartCode() + "," + pq.getPart().getRepoName() + "," + pq.getQuant() + "] ";
            }
        }
        running = false;
        return ("   " + this.partCode + 
                "   |" + this.partName + 
                "| " + this.partDesc + 
                " |    " + this.repoName +
                "    | " + subParts);
    }

    private void waitQueue() {
        while(running);
    }

}