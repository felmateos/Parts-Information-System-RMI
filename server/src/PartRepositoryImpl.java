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

    PartRepositoryImpl(String name) throws RemoteException {
        this.name = name;
    }

    public boolean insertPart(int partCode, String partName, String partDesc, String repoName) throws RemoteException {
        waitQueue();
        this.running = true;
        boolean r = allParts.add(new PartImpl(partCode, partName, partDesc, repoName));
        this.running = false;
        return r;
    }

    public int[] getAllPartsCodes() throws RemoteException {
        waitQueue();
        this.running = true;
        int[] b = new int[this.allParts.size()];
        for (int i = 0; i < this.allParts.size(); i++)
            b[i] = this.allParts.get(i).getPartCode();
        this.running = false;
        return b;
    }

    public Remote getPartRemoteByCode(int partCode) throws RemoteException {
        if (exportedPart != null && exportedPart.getPartCode() == partCode) return exportedPart;
        waitQueue();
        this.running = true;
        for (Part p : allParts) {
            if(p.getPartCode() == partCode) {
                try {
                    if (exportedPart != null) exportedPart.unexportPart();
                    exportedPart = (Part) UnicastRemoteObject.exportObject(p, 3000);
                } catch (RemoteException re) { System.out.println(re); }
                this.running = false;
                return exportedPart;
            }
        }
        this.running = false;
        return null;
    }


    //serializa Part p dar certo
    public List<Part> getAllParts() throws RemoteException {
        return allParts;
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