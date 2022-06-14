import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.LinkedList;
import java.util.List;

public class Client {

    private static Part currentPart = null;
    private static PartRepository currentRepo = null;
    private static List<PartQuant> currentSubPartsList = new LinkedList<>();

    public static void main(String[] args) throws RemoteException {
        while (true) {
            callServer("repo1", "127.0.0.1", 5001);
            System.out.println("Nome do repositório: " + currentRepo.getName());

            currentPart = (Part) currentRepo.getPartRemoteByCode(1);
            System.out.println("\nInfos da peca escolhida: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            System.out.println(currentPart.getInfo());

            System.out.println("\nInfos de todas as pecas: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            for (int partCode : currentRepo.getAllPartsCodes())
                System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
            
            System.out.println("\nAdicionando uma nova peca.");
            currentRepo.insertPart(8, "peca 8", "eh uma peca", "repo1");

            System.out.println("\nInfos de todas as pecas: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            for (int partCode : currentRepo.getAllPartsCodes())
                System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
            
            currentPart = (Part) currentRepo.getPartRemoteByCode(currentRepo.getAllPartsCodes()[currentRepo.getAllPartsCodes().length-1]);
            System.out.println("\nInfos da peca escolhida: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            System.out.println(currentPart.getInfo());

            currentSubPartsList.add((PartQuant) currentPart.createPartQuantRemote(8));

            currentPart = (Part) currentRepo.getPartRemoteByCode(2);

            currentPart.setSubParts(currentSubPartsList);
            System.out.println("\nInfos da peca escolhida: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            System.out.println(currentPart.getInfo());

            System.out.println("\nInfos de todas as pecas: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            for (int partCode : currentRepo.getAllPartsCodes())
                System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());


            callServer("repo2", "127.0.0.1", 5002);
            System.out.println("Nome do repositório: " + currentRepo.getName());

            System.out.println("\nInfos de todas as pecas: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            for (int partCode : currentRepo.getAllPartsCodes())
                System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());

            System.out.println("\nAdicionando uma nova peca.");
            currentRepo.insertPart(9, "peca 9", "eh uma peca", "repo2");

            System.out.println("\nInfos de todas as pecas: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            for (int partCode : currentRepo.getAllPartsCodes())
                System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());

            currentPart = (Part) currentRepo.getPartRemoteByCode(6);

            currentPart.setSubParts(currentSubPartsList);

            System.out.println("\nInfos de todas as pecas: ");
            System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
            for (int partCode : currentRepo.getAllPartsCodes())
                System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
            
            
            /* Adicionar em subpartslists e dps criar umapart contendo essa lista
             */

            break;
        }
        
    }

    private static void callServer(String repoName, String host, int port) {
        try {
            
            Registry remoteRegistry = LocateRegistry.getRegistry(host, port);
            currentRepo = (PartRepository) remoteRegistry.lookup(repoName);
            
        } catch (Exception e) {
            System.out.println("ERRO NO CLIENTE:\n" + e.toString());
        }
    }
}