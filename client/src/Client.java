import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static Part currentPart = null;
    private static PartRepository currentRepo = null;
    private static List<PartQuant> currentSubPartsList = new LinkedList<>();
    private static Scanner scanner = new Scanner(System.in);

    public enum actions {
        BIND("bind"), LISTP("listp");
        
        private final String valor;

        actions (String valorOpcao) {
            valor = valorOpcao;
        }
        public String getValor() {
            return valor;
        }
    }

    public static void main(String[] args) throws RemoteException {
        teste();
        //executeClient();
    }

    private static void executeClient() {
        System.out.println("Ola! Este eh um sistema de informacoes de pecas distribuido.");
        System.out.println("Caso precise de ajuda digite \"help\".");
        while (true) {
            String action = "";
            System.out.println("O que deseja fazer?");
            action = scanner.nextLine();
            String[] actionSplit = action.split(" ");
            if (action.contains(actions.BIND.getValor())) 
                bind(actionSplit[actionSplit.length-2],
                 "127.0.0.1", 
                 Integer.parseInt(actionSplit[actionSplit.length-1]));
            break;
        }
    }

    private static void bind(String repoName, String host, int port) {
        try {
            
            Registry remoteRegistry = LocateRegistry.getRegistry(host, port);
            currentRepo = (PartRepository) remoteRegistry.lookup(repoName);

            System.out.println("current repo: " + currentRepo.getName());
            
        } catch (Exception e) {
            System.out.println("ERRO NO CLIENTE:\n" + e.toString());
        }
    }

    private static void teste() throws RemoteException{
        bind("repo1", "127.0.0.1", 4001);
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


        bind("repo2", "127.0.0.1", 4002);
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
    }

}