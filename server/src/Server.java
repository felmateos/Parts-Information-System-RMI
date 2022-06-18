import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private static int serverId = 0;

    public static void main(String[] args) {
        try {

            PartRepositoryImpl repo = new PartRepositoryImpl("repo " + serverId);
            PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(repo, 4000 + serverId);
            Registry registry = LocateRegistry.createRegistry(5000 + serverId);
            registry.bind("repo " + serverId, stub);
            System.out.println("servidor esta funcionando.");

        } catch (Exception e) { System.out.println("ERRO NO SERVIDOR:\n" + e.toString()); }
    }
}