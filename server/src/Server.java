import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        try {

            PartRepositoryImpl repo1 = new PartRepositoryImpl("repo1");
            repoSample(repo1, 1);
            initRepo(repo1);

            PartRepositoryImpl repo2 = new PartRepositoryImpl("repo2");
            repoSample(repo2, 5);
            initRepo(repo2);

            System.out.println("servidor ta funfando");

        } catch (Exception e) { System.out.println("ERRO NO SERVIDOR:\n" + e.toString()); }
    }

    private static void initRepo(PartRepository repo) throws RemoteException, AlreadyBoundException{
        int c = repo.getName().contains("1") ? 1 : 2;
        PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, c);
        Registry registry = LocateRegistry.createRegistry(4000+c);
        registry.bind("repo" + c, stub);
    }

    private static void repoSample(PartRepository repo, int i) {
        try {

            repo.insertPart("peca x", "eh uma peca", repo.getName(), null);
            repo.insertPart("peca y", "eh uma peca", repo.getName(), null);
            repo.insertPart("peca z", "eh uma peca", repo.getName(), null);

            List<PartQuant> subs = new LinkedList<>();

            subs.add(new PartQuantImpl((Part) repo.getAllParts().get(1), 1));
            subs.add(new PartQuantImpl((Part) repo.getAllParts().get(2), 1));

            ((Part) repo.getAllParts().get(0)).setSubParts(subs);

        } catch (RemoteException e) { e.printStackTrace(); }
    }
}