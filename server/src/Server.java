import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            PartRepositoryImpl repo = new PartRepositoryImpl("repo");

            repoSample(repo);

            PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, 1);

            Registry registry = LocateRegistry.createRegistry(5005);

            registry.bind("repo", stub);

            System.out.println("servidor ta funfando");

        } catch (Exception e) {
            System.out.println("ERRO NO SERVIDOR:\n" + e.toString());
        }
    }

    static void repoSample(PartRepository repo) {
        try {
            repo.insertPart(new PartImpl(1, "peca 1", "eh uma peca", null));
            repo.insertPart(new PartImpl(2, "peca 2", "eh uma peca", null));
            repo.insertPart(new PartImpl(3, "peca 3", "eh uma peca", null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
