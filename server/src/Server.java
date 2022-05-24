import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {

            System.out.println("U bixo ta vino mlk");

            PartRepositoryImpl repo = new PartRepositoryImpl("repo");

            repoTeste(repo);

            System.out.println(repo.getPartByCode(1).getPartName());

            PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, 0);

            Registry registry = LocateRegistry.createRegistry(5005);

            registry.bind("repo", stub);

            System.out.println("servidor ta funfando");

        } catch (Exception e) {
            System.out.println("ERRO NO SERVIDOR:\n" + e.toString());
        }
    }

    static void repoTeste(PartRepository repo) {
        try {
            repo.insertPart(new PartImpl(1, "peca 1", "eh uma peca", null));
            repo.insertPart(new PartImpl(2, "peca 2", "eh uma peca", null));
            repo.insertPart(new PartImpl(3, "peca 3", "eh uma peca", null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
