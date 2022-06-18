import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PartRepositoryImpl implements PartRepository {

    private List<Part> allParts = new LinkedList<>();
    private Part exportedPart = null;
    private String name;
    private boolean running = false;
    private int serverId = 0;

    PartRepositoryImpl(String name) throws RemoteException {
        this.name = name;
        this.serverId = Integer.parseInt(name.split(" ")[1]);
    }

    public boolean insertPart(String partName, String partDesc, String repoName, List<PartQuant> subParts) throws RemoteException {
        waitQueue();
        this.running = true;
        boolean r = this.allParts.add(new PartImpl(partName, partDesc, repoName, subParts));
        this.running = false;
        return r;
    }

    public String getAllPartsInfos() throws RemoteException {
        waitQueue();
        running = true;
        String infos = "";
        for (Part p : this.allParts)
            infos += p.getInfo() + "\n";
        running = false;
        return infos;
    }

    public Remote getPartRemoteByCode(int partCode) throws RemoteException {
        if (exportedPart != null && exportedPart.getPartCode() == partCode) return exportedPart;
        waitQueue();
        this.running = true;
        for (Part p : allParts) {
            if(p.getPartCode() == partCode) {
                try {
                    if (exportedPart != null) exportedPart.unexportPart();
                    exportedPart = (Part) UnicastRemoteObject.exportObject(p, 3000 + serverId);
                } catch (RemoteException re) { System.out.println(re); }
                this.running = false;
                return exportedPart;
            }
        }
        this.running = false;
        return null;
    }

    public int getPartsQuant() throws RemoteException {
        return allParts.size();
    }

    public String getName() throws RemoteException {
        return name;
    }

    public void waitQueue() {
        while (running);
    }
}