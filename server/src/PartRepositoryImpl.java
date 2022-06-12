import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PartRepositoryImpl implements PartRepository {

    private List<Part> allParts = new LinkedList<>();
    private Part exportedPart = null;
    private String name;

    PartRepositoryImpl(String name) throws RemoteException{
        this.name = name;
    }

    @Override
    public boolean insertPart(int partCode, String partName, String partDesc, String repoName, List<PartQuant> subParts) throws RemoteException{
        return allParts.add(new PartImpl(partCode, partName, partDesc, repoName, subParts));
    }

    @Override
    public boolean insertPart(int partCode, String partName, String partDesc, String repoName) throws RemoteException{
        return allParts.add(new PartImpl(partCode, partName, partDesc, repoName));
    }

    @Override
    public Remote getPartByCode(int partCode) throws RemoteException {
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
    @Override
    public List<Part> getAllParts() throws RemoteException {
        return allParts;
    }

    @Override
    public int getPartsQuant() throws RemoteException {
        return allParts.size();
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }
}