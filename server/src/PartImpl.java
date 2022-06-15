import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

public class PartImpl implements Part {

    private int partCode;
    private String partName;
    private String partDesc;
    private String repoName;
    private List<PartQuant> subParts;
    private boolean running = false;

    PartImpl(String partName, String partDesc, String repoName, List<PartQuant> subParts) throws RemoteException {
        this.partCode = generateUniqueId();
        this.partName = partName;
        this.partDesc = partDesc;
        this.repoName = repoName;
        this.subParts = subParts;
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

    public boolean setSubParts(List<PartQuant> subParts) {
        this.subParts = subParts;
        return true;
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

    public static int generateUniqueId() throws RemoteException{      
        UUID idOne = UUID.randomUUID();
        String str=""+idOne;        
        Integer uid=str.hashCode();
        String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        uid = Integer.parseInt(str);
        return (uid >= 0 && uid < 9999999) ? uid : generateUniqueId();
    }

    private void waitQueue() {
        while(running);
    }

}