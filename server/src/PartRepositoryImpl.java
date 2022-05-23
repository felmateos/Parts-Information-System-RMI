import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class PartRepositoryImpl implements PartRepository{

    List<Part> allParts = new LinkedList<>();

    String name;

    PartRepositoryImpl(String name){
        this.name = name;
    }

    @Override
    public void insertPart(Part part) throws RemoteException{
        allParts.add(part);
    }

    @Override
    public Part getPartByCode(int partCode) throws RemoteException {
        for (Part p : allParts)
            if (p.getPartCode() == partCode) return p;
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