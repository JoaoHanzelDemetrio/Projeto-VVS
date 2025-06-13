package ifrs.vvs.service;

import ifrs.vvs.entities.Startup;

import java.util.*;

public class Batalha {

    private Batalha(){}
    static final int WIN = 30;
    // Método para mostrar a batalha que será administrada
    public static Startup batalha(List<Startup> startups) {
        int i = 0;
        Startup a = startups.get(0);
        Startup b = startups.get(1);

        String opMenu;


        Utils.limpaTela();
        imprimeConfronto(startups, i);
        Scanner sc = new Scanner(System.in);


        Set<String> opcoesUsadas = new HashSet<>();
        do{

            String equipes = "\n\nEscolha a equipe que deseja avaliar " +
                    "\n[1]" + a.getNome() + " -- Pontos: " + a.getPontos() +
                    "\n[2]" + b.getNome() + " -- Pontos: " + b.getPontos() +
                    "\nDigite sua opção:";
            System.out.println(equipes);
            opMenu = sc.nextLine();

            if (opcoesUsadas.contains(opMenu)){
                System.out.println("Equipe já avaliada durante essa rodada!");
            }

            switch (opMenu) {
                case "1":
                    if(!opcoesUsadas.contains("1")) {
                        avaliaEquipe(a, sc);
                        opcoesUsadas.add("1");
                    }
                    break;
                case "2":
                    if(!opcoesUsadas.contains("2")) {
                        avaliaEquipe(b, sc);
                        opcoesUsadas.add("2");
                    }
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        }while (opcoesUsadas.size() < 2);



        if (a.getPontos() == b.getPontos()) {
            sharkFight(startups);
            //aumenta aleatoriamente o ponto de alguma das duas
        }



        String vencedoraA = "\n\n------------------------------------------------------------------\n" +

                a.getNome() + " é a vencedora da batalha entre |" + a.getNome() + " (" + a.getPontos() + ") x (" + b.getPontos() + ") " + b.getNome() +
                "|\n------------------------------------------------------------------";

        String vencedoraB = "\n\n------------------------------------------------------------------\n" +
                b.getNome() + " é a vencedora da batalha entre |" + a.getNome() + " (" + a.getPontos() + ") x (" + b.getPontos() + ") " + b.getNome() +
                "|\n------------------------------------------------------------------";



        //Registra a batalha no histórico
        String registro = a.getNome() + " (" + a.getPontos() + ") x (" + b.getPontos() + ") " + b.getNome();
        HistoricoBatalhas.adicionarRegistro(registro);


        // Verifica o vencedor e retorna
        if (a.getPontos() > b.getPontos()) {
            a.setPontos(a.getPontos() + WIN);
            System.out.println(vencedoraA);
            return a;

        } else {
            b.setPontos(b.getPontos() + WIN);
            System.out.println(vencedoraB);
            return b;
        }
    }

    //Método para administrar a avaliacao da equipe
    public static void avaliaEquipe(Startup startup, Scanner sc) {
        Set<String> opcoesUsadas = new HashSet<>();
        String opMenu;

        do {
            String menu = construirMenu(startup);
            System.out.println(menu);
            opMenu = sc.nextLine().toUpperCase();

            if (opcoesUsadas.contains(opMenu) && !opMenu.equals("S")) {
                Utils.limpaTela();
                System.out.println("Essa equipe já registrou esse evento. O evento só pode ser registrado uma vez por rodada!\n");
                continue;
            }

            processarOpcao(opMenu, startup, opcoesUsadas);
        } while (!opMenu.equals("S"));
    }

    // Método extraído para facilitar testes
    private static String construirMenu(Startup startup) {
        return "Registrar avaliacao de " + startup.getNome() + " Pontos: " + startup.getPontos() +
                "\n[1] Pitch convincente (+6 pontos)" +
                "\n[2] Produto com bugs (-4 pontos)" +
                "\n[3] Boa tração de usuários (+3 pontos)" +
                "\n[4] Investidor irritado (-6 pontos)" +
                "\n[5] Fake news no pitch (-8 pontos)" +
                "\n[S] Finalizar o registro." +
                "\n\n Digite sua opção: ";
    }

    // Método extraído para facilitar testes
    private static void processarOpcao(String opcao, Startup startup, Set<String> opcoesUsadas) {
        switch (opcao) {
            case "1":
                aplicarEvento(startup, 6, "Pitch convincente", opcoesUsadas, "1");
                break;
            case "2":
                aplicarEvento(startup, -4, "Produto com bugs", opcoesUsadas, "2");
                break;
            case "3":
                aplicarEvento(startup, 3, "Boa tração de usuários", opcoesUsadas, "3");
                break;
            case "4":
                aplicarEvento(startup, -6, "Investidor irritado", opcoesUsadas, "4");
                break;
            case "5":
                aplicarEvento(startup, -8, "Fake news no pitch", opcoesUsadas, "5");
                break;
            case "S":
                Utils.limpaTela();
                System.out.println("Finalizando avaliação...");
                break;
            default:
                Utils.limpaTela();
                System.out.println("Opção inválida!");
        }
    }

    // Método auxiliar para aplicar eventos
    private static void aplicarEvento(Startup startup, int pontos, String mensagem, Set<String> opcoesUsadas, String opcao) {
        if (!opcoesUsadas.contains(opcao)) {
            startup.setPontos(startup.getPontos() + pontos);
            atualizarContador(startup, opcao);
            Utils.limpaTela();
            System.out.println("[" + mensagem + "] para a equipe " + startup.getNome());
            opcoesUsadas.add(opcao);
        }
    }

    // Método auxiliar para atualizar contadores
    private static void atualizarContador(Startup startup, String opcao) {
        switch (opcao) {
            case "1" -> startup.setTotalPitchConvincente(startup.getTotalPitchConvincente() + 1);
            case "2" -> startup.setTotalProdutoComBugs(startup.getTotalProdutoComBugs() + 1);
            case "3" -> startup.setTotalBoaTracaoDeUsuario(startup.getTotalBoaTracaoDeUsuario() + 1);
            case "4" -> startup.setTotalInvestidorIrritado(startup.getTotalInvestidorIrritado() + 1);
            case "5" -> startup.setTotalFakeNewsNoPitch(startup.getTotalFakeNewsNoPitch() + 1);
            default -> System.out.println("!!!");
        }
    }

    //Imprime as startups que vão se enfrentar
    public static void imprimeConfronto(List<Startup> startups, int i) {
        Startup a = startups.get(0);
        Startup b = startups.get(1);
        System.out.println(i + 1 + ".  |" + a.getNome() + " (" + a.getPontos() + ") X (" + b.getPontos() + ") " + b.getNome() + "|");
    }

    //Função para decidir um vencedor em caso de empate na batalha
    public static void sharkFight(List<Startup> startups) {
        Startup a = startups.get(0);
        Startup b = startups.get(1);
        System.out.println("\n\nEmpate entre " + a.getNome() + " e " + b.getNome() + ".");
        System.out.println("Será realizado o Shark Fight.");

        //Gera um index aleatório entre 0 e 1
        Random random = new Random();
        int indexSorteado = random.nextInt(2);
        int pontos = startups.get(indexSorteado).getPontos();
        pontos += 2;

        startups.get(indexSorteado).setPontos(pontos);
        System.out.println("\n\n-------RESULTADO DO SHARK FIGHT----------");
        System.out.println("\nA equipe sorteada foi: " + startups.get(indexSorteado).getNome());
        System.out.println("\n-----------------------------------------");
    }

}
