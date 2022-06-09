import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PartRepositoryImpl implements PartRepository{

    List<Part> allParts = new LinkedList<>();
    Part exportedPart = null;

    String name;

    PartRepositoryImpl(String name) throws RemoteException{
        this.name = name;
    }

    @Override
    public void insertPart(Part part) throws RemoteException{
        allParts.add(part);
    }

    @Override
    public Remote getPartByCode(int partCode) throws RemoteException {
        if (exportedPart != null && exportedPart.getPartCode() == partCode) return exportedPart;
        for (Part p : allParts) {
            if(p.getPartCode() == partCode) {
                try {
                    if (exportedPart != null) exportedPart.unexport();
                    exportedPart = (Part) UnicastRemoteObject.exportObject(p, 10);
                } catch (RemoteException re) {
                    System.out.println(re);
                }
                return exportedPart;
            }
        }
        return null;
    }

    @Override
    public List<Part> getAllParts() throws RemoteException {
        return allParts;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }
}