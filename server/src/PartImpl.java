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
    private int serverId = 0;

    PartImpl(String partName, String partDesc, String repoName, List<PartQuant> subParts) throws RemoteException {
        this.partCode = generateUniqueId();
        this.partName = partName;
        this.partDesc = partDesc;
        this.repoName = repoName;
        this.subParts = subParts;
        this.serverId = Integer.parseInt(repoName.split(" ")[1]);
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
        Remote r = UnicastRemoteObject.exportObject(new PartQuantImpl(this, quant), 1000+serverId);
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
        String subParts = " Não possui sub-pecas";
        if (this.subParts != null && !this.subParts.isEmpty()) subParts = getSubsInfo();
        running = false;
        return ("Código: " + this.partCode
                + "\nNome: " + this.partName
                + "\nDescricao: "+this.partDesc
                + "\nRepositorio: " + this.repoName
                + "\nLista de sub-pecas:" + subParts
                + "\n==============================================================================================");
    }

    private String getSubsInfo() throws RemoteException {
        String subsInfos = "";
        for (PartQuant pq : this.subParts) {
            subsInfos += "\n\n  Código: " + pq.getPart().getPartCode()
            + "\n  Nome: " + pq.getPart().getPartName()
            + "\n  Descricao: "+pq.getPart().getPartDesc()
            + "\n  Repositorio: " + pq.getPart().getRepoName()
            + "\n  Quantidade: " + pq.getQuant();
        }
        return subsInfos;
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