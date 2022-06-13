import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PartRepositoryImpl implements PartRepository {

    private List<Part> allParts = new LinkedList<>();
    private Part exportedPart = null;
    private String name;

    PartRepositoryImpl(String name) throws RemoteException {
        this.name = name;
    }

    public boolean insertPart(int partCode, String partName, String partDesc, String repoName) throws RemoteException {
        return allParts.add(new PartImpl(partCode, partName, partDesc, repoName));
    }

    public int[] getAllPartsCodes() throws RemoteException{
        int[] b = new int[this.allParts.size()];
        for (int i = 0; i < this.allParts.size(); i++)
            b[i] = this.allParts.get(i).getPartCode();
        return b;
    }

    public Remote getPartRemoteByCode(int partCode) throws RemoteException {
        if (exportedPart != null && exportedPart.getPartCode() == partCode) return exportedPart;
        for (Part p : allParts) {
            if(p.getPartCode() == partCode) {
                try {
                    if (exportedPart != null) exportedPart.unexport();
                    exportedPart = (Part) UnicastRemoteObject.exportObject(p, 10);
                } catch (RemoteException re) { System.out.println(re); }
                return exportedPart;
            }
        }
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
}