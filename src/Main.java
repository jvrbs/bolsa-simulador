// FileName: /Main.java
//
// Propósito: Ponto de entrada do simulador, gerencia o menu, interações do usuário e o fluxo da simulação.
//
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializa as empresas disponíveis na simulação.
        Empresa Petrobras = new Empresa("Petrobras", "PETR4", 25.0, true, 0.005, 10000000, 36.6);
        Empresa Vale = new Empresa("Vale", "VALE3", 35.0, false, 0.0, 15000000, 31.6);

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        Usuario u1 = null; // O usuário principal da simulação.

        // Loop principal do menu interativo.
        do {
            System.out.println("\n- Bem vindo ao simulador de Bolsa -");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Criar Usuario");
            System.out.println("2 - Comprar ação");
            System.out.println("3 - Vender ação");
            System.out.println("4 - Simular tempo");
            System.out.println("5 - Exibir dados");
            System.out.println("6 - Exibir transações");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opção: ");

            // Leitura e validação da opção do menu.
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
            } else {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.next();
                opcao = 0;
            }
            scanner.nextLine();

            // Processa a opção escolhida pelo usuário.
            switch (opcao) {
                case 1: // Cria um novo usuário com nome e saldo inicial.
                    System.out.print("Digite o nome do usuário: ");
                    String nome = scanner.nextLine();
                    double saldo = 0.0;
                    boolean saldoValido = false;
                    while (!saldoValido) { // Garante um saldo inicial válido.
                        System.out.print("Digite o saldo inicial: ");
                        if (scanner.hasNextDouble()) {
                            saldo = scanner.nextDouble();
                            scanner.nextLine();
                            if (saldo > 0) {
                                saldoValido = true;
                            } else {
                                System.out.println("Saldo deve ser maior que zero!");
                            }
                        } else {
                            System.out.println("Valor inválido! Digite um número.");
                            scanner.next();
                            scanner.nextLine();
                        }
                    }
                    u1 = new Usuario(nome, saldo);
                    System.out.println("Usuário criado com sucesso!");
                    System.out.println("Nome: " + u1.getNome());
                    System.out.printf("Saldo: R$ %.2f%n", u1.getSaldo());
                    break;

                case 2: // Permite ao usuário comprar ações.
                    if (u1 == null) { System.out.println("Você precisa criar um usuário primeiro!"); break; }
                    System.out.println("\n=== EMPRESAS DISPONÍVEIS ===");
                    List<Empresa> empresas = Empresa.getEmpresas();
                    for (int i = 0; i < empresas.size(); i++) { // Exibe as empresas disponíveis.
                        Empresa emp = empresas.get(i);
                        System.out.printf("%d - %s (%s) - Preço: R$ %.2f - Dividendos: %s (%.1f%%)%n",
                                i + 1, emp.getNome(), emp.getTicker(), emp.getPrecoAcao(),
                                emp.pagaDividendos() ? "Sim" : "Não", emp.getDividendYield() * 100);
                    }
                    int empresaIndexCompra = -1;
                    while (empresaIndexCompra == -1) { // Seleção da empresa.
                        System.out.print("Digite o índice da empresa (1-" + empresas.size() + "): ");
                        if (scanner.hasNextInt()) {
                            int escolha = scanner.nextInt(); scanner.nextLine();
                            if (escolha >= 1 && escolha <= empresas.size()) { empresaIndexCompra = escolha - 1; } else { System.out.println("Índice inválido!"); }
                        } else { System.out.println("Digite um número válido!"); scanner.next(); scanner.nextLine(); }
                    }
                    int quantidadeCompra = 0;
                    while (quantidadeCompra == 0) { // Seleção da quantidade.
                        System.out.print("Digite a quantidade de ações: ");
                        if (scanner.hasNextInt()) {
                            int qnt = scanner.nextInt(); scanner.nextLine();
                            if (qnt > 0) { quantidadeCompra = qnt; } else { System.out.println("Quantidade deve ser maior que zero!"); }
                        } else { System.out.println("Digite um número válido!"); scanner.next(); scanner.nextLine(); }
                    }
                    Empresa empresaSelecionada = empresas.get(empresaIndexCompra);
                    double custoTotal = empresaSelecionada.getPrecoAcao() * quantidadeCompra;
                    System.out.printf("\n=== RESUMO DA COMPRA ===%n"); // Resumo e confirmação da compra.
                    System.out.printf("Empresa: %s (%s)%n", empresaSelecionada.getNome(), empresaSelecionada.getTicker());
                    System.out.printf("Quantidade: %d ações%n", quantidadeCompra);
                    System.out.printf("Preço unitário: R$ %.2f%n", empresaSelecionada.getPrecoAcao());
                    System.out.printf("Custo total: R$ %.2f%n", custoTotal);
                    System.out.printf("Saldo atual: R$ %.2f%n", u1.getSaldo());
                    if (u1.getSaldo() >= custoTotal) {
                        System.out.print("Confirmar compra? (s/n): "); String confirmacao = scanner.nextLine().toLowerCase();
                        if (confirmacao.equals("s") || confirmacao.equals("sim")) {
                            u1.comprar(empresaSelecionada, quantidadeCompra);
                            empresaSelecionada.variacaoCompra(quantidadeCompra);
                            System.out.println("Compra realizada com sucesso!"); System.out.printf("Novo saldo: R$ %.2f%n", u1.getSaldo());
                        } else { System.out.println("Compra cancelada."); }
                    } else { System.out.printf("Saldo insuficiente! Faltam R$ %.2f%n", custoTotal - u1.getSaldo()); }
                    break;

                case 3: // Permite ao usuário vender ações de sua carteira.
                    if (u1 == null) { System.out.println("Você precisa criar um usuário primeiro!"); break; }
                    List<Acao> carteira = u1.getCarteira();
                    if (carteira.isEmpty()) { System.out.println("Sua carteira está vazia. Compre ações primeiro!"); break; }
                    System.out.println("\n=== SUA CARTEIRA ===");
                    for (int i = 0; i < carteira.size(); i++) { // Exibe as ações na carteira do usuário.
                        Acao acao = carteira.get(i); Empresa emp = acao.getEmpresa();
                        System.out.printf("%d - %s (%s): %d ações (Preço: R$ %.2f)%n",
                                i + 1, emp.getNome(), emp.getTicker(), acao.getQuantidade(), emp.getPrecoAcao());
                    }
                    int empresaIndexVenda = -1;
                    while (empresaIndexVenda == -1) { // Seleção da ação a vender.
                        System.out.print("Digite o índice da ação a vender (1-" + carteira.size() + "): ");
                        if (scanner.hasNextInt()) {
                            int escolha = scanner.nextInt(); scanner.nextLine();
                            if (escolha >= 1 && escolha <= carteira.size()) { empresaIndexVenda = escolha - 1; } else { System.out.println("Índice inválido!"); }
                        } else { System.out.println("Digite um número válido!"); scanner.next(); scanner.nextLine(); }
                    }
                    Acao acaoSelecionada = carteira.get(empresaIndexVenda);
                    Empresa empresaVenda = acaoSelecionada.getEmpresa();
                    int qntMax = acaoSelecionada.getQuantidade();
                    int quantidadeVenda = 0;
                    while (quantidadeVenda == 0 || quantidadeVenda > qntMax) { // Seleção da quantidade a vender.
                        System.out.print("Digite a quantidade a vender (máx: " + qntMax + "): ");
                        if (scanner.hasNextInt()) {
                            int vendaQnt = scanner.nextInt(); scanner.nextLine();
                            if (vendaQnt > 0 && vendaQnt <= qntMax) { quantidadeVenda = vendaQnt; } else { System.out.println("Quantidade inválida! Deve ser entre 1 e " + qntMax); }
                        } else { System.out.println("Digite um número válido!"); scanner.next(); scanner.nextLine(); }
                    }
                    double valorVenda = empresaVenda.getPrecoAcao() * quantidadeVenda;
                    double saldoDivAtual = u1.getSaldoDividendos();
                    System.out.printf("\n=== RESUMO DA VENDA ===%n"); // Resumo e confirmação da venda.
                    System.out.printf("Empresa: %s (%s)%n", empresaVenda.getNome(), empresaVenda.getTicker());
                    System.out.printf("Quantidade: %d ações (você tem %d)%n", quantidadeVenda, qntMax);
                    System.out.printf("Preço unitário: R$ %.2f%n", empresaVenda.getPrecoAcao());
                    System.out.printf("Valor da venda: R$ %.2f%n", valorVenda);
                    System.out.printf("Dividendos acumulados (serão transferidos): R$ %.2f%n", saldoDivAtual);
                    System.out.printf("Saldo atual: R$ %.2f%n", u1.getSaldo());
                    System.out.print("Confirmar venda? (s/n): "); String confirmacaoVenda = scanner.nextLine().toLowerCase();
                    if (confirmacaoVenda.equals("s") || confirmacaoVenda.equals("sim")) {
                        u1.vender(empresaVenda, quantidadeVenda);
                        if (saldoDivAtual > 0) { // Transfere dividendos acumulados se houver.
                            u1.transferirDividendosParaSaldo();
                            System.out.printf("Dividendos (R$ %.2f) transferidos para o saldo!%n", saldoDivAtual);
                        }
                        System.out.println("Venda realizada com sucesso!"); System.out.printf("Novo saldo: R$ %.2f%n", u1.getSaldo());
                    } else { System.out.println("Venda cancelada."); }
                    break;

                case 4: // Simula a passagem de tempo (dias), variando preços e pagando dividendos.
                    if (u1 == null) { System.out.println("Você precisa criar um usuário primeiro!"); break; }
                    int qntTempo = 0;
                    while (qntTempo == 0) { // Define quantos dias simular.
                        System.out.print("Quanto tempo simular (em dias): ");
                        if (scanner.hasNextInt()) {
                            int dias = scanner.nextInt(); scanner.nextLine();
                            if (dias > 0) { qntTempo = dias; } else { System.out.println("Deve ser maior que zero!"); }
                        } else { System.out.println("Digite um número válido!"); scanner.next(); scanner.nextLine(); }
                    }
                    System.out.println("\nIniciando simulação de " + qntTempo + " dias...");
                    List<Empresa> todasEmpresas = Empresa.getEmpresas();
                    for (int i = 0; i < qntTempo; i++) { // Loop para cada dia da simulação.
                        System.out.println("\n--- Dia " + (i + 1) + " ---");
                        for (Empresa emp : todasEmpresas) { emp.Variacao(); } // Varia o preço das ações.
                        u1.receberDividendos(); // Usuário recebe dividendos.
                        u1.mostrarDividendosDoDia(); // Exibe dividendos do dia.
                    }
                    System.out.println("\nSimulação concluída!");
                    System.out.println("Dica: Use opção 5 para ver dividendos acumulados e transferi-los ao saldo.");
                    break;

                case 5: // Exibe os dados do usuário, saldo, dividendos e carteira de ações.
                    if (u1 == null) { System.out.println("Você precisa criar um usuário primeiro!"); break; }
                    System.out.println("\n=== DADOS DO USUÁRIO ===");
                    System.out.println("Nome: " + u1.getNome());
                    System.out.printf("Saldo (dinheiro disponível): R$ %.2f%n", u1.getSaldo());
                    System.out.printf("Dividendos acumulados: R$ %.2f%n", u1.getSaldoDividendos());
                    System.out.println("\n--- CARTEIRA ---");
                    List<Acao> carteiraExibir = u1.getCarteira();
                    if (carteiraExibir.isEmpty()) { System.out.println("Carteira vazia."); }
                    else { // Exibe as ações na carteira e o valor total.
                        double valorTotalCarteira = 0.0;
                        for (Acao acao : carteiraExibir) {
                            Empresa emp = acao.getEmpresa(); double valorAcao = acao.getQuantidade() * emp.getPrecoAcao();
                            valorTotalCarteira += valorAcao;
                            System.out.printf("  - %s (%s): %d ações (R$ %.2f cada) - Valor: R$ %.2f%n",
                                    emp.getNome(), emp.getTicker(), acao.getQuantidade(), emp.getPrecoAcao(), valorAcao);
                        }
                        System.out.printf("Valor total da carteira: R$ %.2f%n", valorTotalCarteira);
                    }
                    double saldoDivAtualExibir = u1.getSaldoDividendos();
                    if (saldoDivAtualExibir > 0) { // Oferece a opção de transferir dividendos.
                        System.out.print("\nTransferir dividendos acumulados (R$ " + String.format("%.2f", saldoDivAtualExibir) + ") para o saldo? (s/n): ");
                        String transferir = scanner.nextLine().toLowerCase();
                        if (transferir.equals("s") || transferir.equals("sim")) {
                            u1.transferirDividendosParaSaldo(); System.out.printf("Transferido! Novo saldo: R$ %.2f%n", u1.getSaldo());
                        } else { System.out.println("Transferência cancelada."); }
                    } else { System.out.println("\nNenhum dividendo para transferir."); }
                    break;

                case 6: // Exibe o histórico de transações do usuário.
                    if (u1 == null) { System.out.println("Você precisa criar um usuário primeiro!"); break; }
                    System.out.println("\n=== HISTÓRICO DE TRANSAÇÕES ===");
                    u1.visualizarHistorico();
                    break;

                case 7: // Sai do simulador.
                    System.out.println("Saindo do simulador. Até mais!");
                    break;

                default: // Opção inválida.
                    System.out.println("Opção inválida! Escolha de 1 a 7.");
                    break;
            }

        } while (opcao != 7); // Continua o menu até que o usuário escolha sair.

        scanner.close();
    }
}