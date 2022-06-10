import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            PartRepositoryImpl repo1 = new PartRepositoryImpl("repo1");
            repoSample(repo1, 1);
            PartRepository stub1 = (PartRepository) UnicastRemoteObject.exportObject(repo1, 1);
            Registry registry1 = LocateRegistry.createRegistry(5001);
            registry1.bind("repo1", stub1);

            PartRepositoryImpl repo2 = new PartRepositoryImpl("repo2");
            repoSample(repo2, 5);
            PartRepository stub2 = (PartRepository) UnicastRemoteObject.exportObject(repo2, 2);
            Registry registry2 = LocateRegistry.createRegistry(5002);
            registry2.bind("repo2", stub2);

            System.out.println("servidor ta funfando");

        } catch (Exception e) {
            System.out.println("ERRO NO SERVIDOR:\n" + e.toString());
        }
    }

    static void repoSample(PartRepository repo, int i) {
        try {
            repo.insertPart(i+0, "peca "+(i+0), "eh uma peca", repo.getName(), null);
            repo.insertPart(i+1, "peca "+(i+1), "eh uma peca", repo.getName(), null);
            repo.insertPart(i+2, "peca "+(i+2), "eh uma peca", repo.getName(), null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}