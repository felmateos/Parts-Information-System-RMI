import java.net.SocketTimeoutException;
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
    private static String host = "127.0.0.1";
    private static boolean running = false;

    public enum actions {
        HELP, BIND, LISTP, QUIT;
    }

    public static void main(String[] args) throws RemoteException {
        //teste();
        executeClient();
    }

    private static void executeClient() throws RemoteException {
        running = true;
        System.out.println("Ola! Este eh um sistema de informacoes de pecas distribuido.");
        System.out.println("Caso precise de ajuda digite \"help\".");
        while (running) {
            try {
                String action = "";

                System.out.println("O que deseja fazer?");
                action = scanner.nextLine();

                String[] actionSplit = action.split(" ");

                actions chosenAction = actions.valueOf(actionSplit[0].toUpperCase());
                
                switch (chosenAction) {
                    case HELP  -> help();
                    case BIND  -> bind(actionSplit[1], Integer.parseInt(actionSplit[2]));
                    case LISTP -> listp();
                    case QUIT  -> running = false;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Comando invalido!");
            }
        }
    }

    private static void help() {
        System.out.println("help commands");
    }

    private static void bind(String repoName, int port) {
        try {
            
            Registry remoteRegistry = LocateRegistry.getRegistry(host, port);
            currentRepo = (PartRepository) remoteRegistry.lookup(repoName);

            System.out.println("repositorio atual: " + currentRepo.getName() + " qtd. de pecas: " + currentRepo.getPartsQuant());
            
        } catch (Exception e) {
            System.out.println("ERRO NO CLIENTE:\n" + e.toString());
        }
    }

    private static void listp() throws RemoteException{
        System.out.println("\nInfos de todas as pecas: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        for (int partCode : currentRepo.getAllPartsCodes())
            System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
    }

    private static void teste() throws RemoteException{
        bind("repo1", 4001);
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


        bind("repo2", 4002);
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