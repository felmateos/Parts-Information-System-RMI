import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String[] args) {
        try {

            PartRepositoryImpl repo = new PartRepositoryImpl("repo");
            PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, 4000);
            Registry registry = LocateRegistry.createRegistry(5000);
            registry.bind("repo", stub);
            System.out.println("servidor esta funcionando.");

        } catch (Exception e) { System.out.println("ERRO NO SERVIDOR:\n" + e.toString()); }
    }
}