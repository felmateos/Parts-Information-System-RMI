import java.rmi.registry.*;

public class Client {
    public static void main(String[] args) {
        try {
            
            Registry remoteRegistry = LocateRegistry.getRegistry("127.0.0.1", 5005);

            PartRepository repo = (PartRepository) remoteRegistry.lookup("repo");

            System.out.println(repo.getName()+"a");

            //Part actualPart = repo.getPartByCode(1);

            //System.out.println(actualPart.getPartName());
            
            //sugestao devolver uma lista com as info da peca
            //System.out.println(repo.getPartByCode(1).getPartName());

        } catch (Exception e) {
            System.out.println("ERRO NO CLIENTE:\n" + e.toString());
        }
    }
}