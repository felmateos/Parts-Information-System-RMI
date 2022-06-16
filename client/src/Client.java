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
        HELP, BIND, LISTP, GETP, SHOWP, CLIST, ADDSUBP, ADDP, QUIT;
    }

    public static void main(String[] args) throws RemoteException {
        //teste();
        executeClient();
    }

    private static void executeClient() throws RemoteException {
        running = true;
        System.out.println("Ola! Este eh um sistema de informacoes de pecas distribuido.");
        System.out.println("Caso precise de ajuda digite \"help\" sem as aspas.");
        while (running) {
            try {
                String action = "";

                System.out.println("O que deseja fazer?");
                action = scanner.nextLine();

                String[] actionSplit = action.split(" ");

                actions chosenAction = actions.valueOf(actionSplit[0].toUpperCase());
                
                switch (chosenAction) {
                    case HELP    -> help();
                    case BIND    -> bind(actionSplit[1], Integer.parseInt(actionSplit[2]));
                    case LISTP   -> listp();
                    case GETP    -> getp(Integer.parseInt(actionSplit[1]));
                    case SHOWP   -> showp();
                    case CLIST   -> clearList();
                    case ADDSUBP -> addSubPart(Integer.parseInt(actionSplit[1]));
                    case ADDP    -> addPart(actionSplit[1], actionSplit[2]);
                    case QUIT    -> running = false;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Comando invalido!");
            }
        }
    }

    private static void help() {
        System.out.println("Comandos disponíveis:\n"
        +" Obs.: Ao utilizar os comandos abaixo desconsidere as aspas.\n\n"
        +" BIND: Usado para se conectar a um repositorio de um servidor.\n"
        +"  Sintaxe: bind \"nomeDoServidor\" \"porta\"\n\n"
        +" LISTP: Lista as pecas do repositorio atual e suas respectivas informacoes.\n"
        +"  Sintaxe: listp\n\n"
        +" GETP: Armazena a referencia de uma peca do repositorio atual em \"pecaAtual\", busca feita pelo codigo da peca.\n"
        +"  Sintaxe: getp \"codigoDaPeca\"\n\n"
        +" SHOWP: Exibe as informacoes da peca obtida com ao comando anterior.\n"
        +"  Sintaxe: showp\n\n"
        +" CLIST: Limpa a lista \"subPecas\" contidas no cliente.\n"
        +"  Sintaxe: clist\n\n"
        +" ADDSUBP: Adiciona certa quantidade da peca atual na lista \"subPecas\".\n"
        +"  Sintaxe: addsubp \"qunatidade\"\n\n"
        +" ADDP: Adiciona uma peca ao repositorio atual, a lista \"subPecas\" eh usada como lista de subpecas da peca inserida.\n"
        +"  Sintaxe: addp \"nomeDaPeca\" \"descricaoDaPeca\"\n\n"
        +" QUIT: Termina as operacoes e o processo cliente.\n"
        +"  Sintaxe: quit");
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

    private static void listp() throws RemoteException {
        System.out.println("\nInfos de todas as pecas: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        for (int partCode : currentRepo.getAllPartsCodes())
            System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
    }

    private static void getp(int partCode) throws RemoteException {
        currentPart = (Part) currentRepo.getPartRemoteByCode(partCode);
    }

    private static void showp() throws RemoteException {
        System.out.println("\nInfos da peca escolhida: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        System.out.println(currentPart.getInfo());
    }

    private static void clearList() {
        currentSubPartsList.clear();
    }

    private static void addSubPart(int quant) throws RemoteException {
        currentSubPartsList.add((PartQuant) currentPart.createPartQuantRemote(quant));
    }

    private static void addPart(String partName, String partDesc) throws RemoteException{
        currentRepo.insertPart(partName, partDesc, currentRepo.getName(), currentSubPartsList);
    }

    private static void teste() throws RemoteException{
        bind("repo1", 4001);
        System.out.println("Nome do repositório: " + currentRepo.getName());

        currentPart = (Part) currentRepo.getPartRemoteByCode(currentRepo.getAllPartsCodes()[0]);
        System.out.println("\nInfos da peca escolhida: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        System.out.println(currentPart.getInfo());

        System.out.println("\nInfos de todas as pecas: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        for (int partCode : currentRepo.getAllPartsCodes())
            System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
        
        System.out.println("\nAdicionando uma nova peca.");
        currentRepo.insertPart("peca a", "eh uma peca", "repo1", null);

        System.out.println("\nInfos de todas as pecas: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        for (int partCode : currentRepo.getAllPartsCodes())
            System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
        
        currentPart = (Part) currentRepo.getPartRemoteByCode(currentRepo.getAllPartsCodes()[currentRepo.getAllPartsCodes().length-1]);
        System.out.println("\nInfos da peca escolhida: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        System.out.println(currentPart.getInfo());

        currentSubPartsList.add((PartQuant) currentPart.createPartQuantRemote(8));

        currentPart = (Part) currentRepo.getPartRemoteByCode(currentRepo.getAllPartsCodes()[1]);

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
        currentRepo.insertPart("peca b", "eh uma peca", "repo2", null);

        System.out.println("\nInfos de todas as pecas: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        for (int partCode : currentRepo.getAllPartsCodes())
            System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());

        currentPart = (Part) currentRepo.getPartRemoteByCode(currentRepo.getAllPartsCodes()[1]);

        currentPart.setSubParts(currentSubPartsList);

        System.out.println("\nInfos de todas as pecas: ");
        System.out.println("Codigo | Nome |  Descricao  | Repositorio | SubPecas [Cod,Repo,Quant]");
        for (int partCode : currentRepo.getAllPartsCodes())
            System.out.println(((Part) currentRepo.getPartRemoteByCode(partCode)).getInfo());
    }

}