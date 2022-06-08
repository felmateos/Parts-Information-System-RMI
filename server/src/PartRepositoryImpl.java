import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class PartRepositoryImpl implements PartRepository{

    List<Part> allParts = new LinkedList<>();
    Part exportedPart1 = null;
    Part exportedPart2 = null;

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
        if (partCode == 1) {
            System.out.println("1a");
            if (exportedPart2 != null) {
                System.out.println("1b");
                //UnicastRemoteObject.unexportObject(exportedPart2, true);
                exportedPart2 = null;
            }
            exportedPart1 = (Part) UnicastRemoteObject.exportObject(allParts.get(partCode-1), 10);
            System.out.println("1c");
            return exportedPart1;
        } else if(partCode == 2) {
            System.out.println("2a");
            if (exportedPart1 != null) {
                System.out.println("2b");
                
                exportedPart1 = null;
            }
            exportedPart2 = (Part) UnicastRemoteObject.exportObject(allParts.get(partCode-1), 10);
            System.out.println("2c");
            return exportedPart2;
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