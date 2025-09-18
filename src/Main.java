import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Empresa Petrobras = new Empresa("Petrobras", "PETR4", 35.0, true, 0.05);
        Empresa Vale = new Empresa("Vale", "VALE3", 70.0, false, 0);
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        Usuario u1 = null;

        do {
            System.out.println("\n- Bem vindo ao simulador de Bolsa -");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Criar Usuario");
            System.out.println("2 - Comprar ação");
            System.out.println("3 - Vender ação");
            System.out.println("4 - Receber dividendos");
            System.out.println("5 - Exibir dados");
            System.out.println("6 - Exibir transações");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do usuário: ");
                    String nome = scanner.nextLine();

                    double saldo = 0;
                    boolean saldoValido = false;
                    while (!saldoValido) {
                        try {
                            System.out.print("Digite o saldo inicial: ");
                            saldo = scanner.nextDouble();
                            scanner.nextLine(); 

                            if (saldo <= 0) {
                                System.out.println("Saldo deve ser maior que zero!");
                                continue;
                            }
                            saldoValido = true;
                        } catch (Exception e) {
                            System.out.println("Valor inválido! Digite um número.");
                            scanner.nextLine(); 
                        }
                    }

                    u1 = new Usuario(nome, saldo);
                    System.out.println("Usuário criado com sucesso!!");
                    System.out.println("Nome: " + u1.getNome());
                    System.out.printf("Saldo: R$ %.2f%n", u1.getSaldo());
                    break;
                case 2:
                    if (u1 == null){
                        System.out.println("Você precisa criar um usuário");
                        break;
                    }
                    System.out.println("\\n=== EMPRESAS DISPONÍVEIS ===");
                    List<Empresa> empresas = Empresa.getEmpresas();

                    for (int i = 0; i < empresas.size(); i++){
                        Empresa emp = empresas.get(i);
                        System.out.printf("%d - %s (%s) - Preço: R$ %.2f%n", i + 1, emp.getNome(), emp.getTicker(), emp.getPrecoAcao());
                    }

                    try (
                        System.out.println("Qual empresa você deseja comprar?: ")
                        String nomeEmpresa = scanner.nextLine();

                        int t = 0;
                        for (empresa : Empresa)
                    )

                    
                
                case 3:




                default:
                    if (opcao != 7) {
                        System.out.println("Opção inválida ou não implementada!");
                    }
                    break;
            }

        } while (opcao != 7);

        scanner.close();
    }
}