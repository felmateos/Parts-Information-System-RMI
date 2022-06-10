import java.rmi.registry.*;

public class Client {

    private static Part currentPart = null;
    private static PartRepository currentRepo = null;
    private static int PartQuantCurrentRepo = 0;

    public static void main(String[] args) {

        callServer("repo1", "127.0.0.1", 5001);
        showParts();


        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");


        callServer("repo2", "127.0.0.1", 5002);
        showParts();
        
    }

    private static void callServer(String repoName, String host, int port) {
        try {
            
            Registry remoteRegistry = LocateRegistry.getRegistry(host, port);
            currentRepo = (PartRepository) remoteRegistry.lookup(repoName);
            System.out.println(currentRepo.getName());
            PartQuantCurrentRepo = currentRepo.getPartsQuant();
            
        } catch (Exception e) {
            System.out.println("ERRO NO CLIENTE:\n" + e.toString());
        }
    }

    private static void showParts() {
        try {
            int i = currentRepo.getName().contains("1") ? 1 : 5;
            int l = i + PartQuantCurrentRepo;
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas");
            for (; i < l; i++) {
                currentPart = (Part) currentRepo.getPartByCode(i);
                System.out.println(currentPart.printInfo());
            }
            currentRepo.insertPart(i, "peca "+i, "eh uma peca", currentRepo.getName(), null);
            currentPart = (Part) currentRepo.getPartByCode(i);
            System.out.println(currentPart.printInfo());

        } catch (Exception e) {
            System.out.println("ERRO NO CLIENTE:\n" + e.toString());
        }
    }

}