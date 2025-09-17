import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {
    private String nome;
    private double saldo;
    private List<Acao> carteira;
    private List<Transacao> historicoTransacoes;
    private double saldoDividendos;

    public Usuario(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.carteira = new ArrayList<>();
        this.historicoTransacoes = new ArrayList<>();
    }

    public Usuario() {
        this("Não Definido", 0.0); // chama o construtor principal
    }

    public List<Transacao> getHistoricoTransacoes() { return historicoTransacoes; }
    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    public List<Acao> getCarteira() { return carteira; }
    public double getSaldoDividendos() { return saldoDividendos; }

    public void setNome(String nome) { this.nome = nome; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setCarteira(List<Acao> carteira) { this.carteira = carteira; }

    public void comprar(Empresa empresa, int quantidade) {
        double custo = empresa.getPrecoAcao() * quantidade;
        if (saldo < custo) {
            System.out.println("Saldo insuficiente!");
            return;
        }

        saldo -= custo;

        for (Acao acao : carteira) {
            if (acao.getEmpresa().equals(empresa)) {
                acao.setQuantidade(acao.getQuantidade() + quantidade);
                System.out.println(nome + " comprou " + quantidade + " de " + empresa.getTicker());
                Transacao transacao = new Transacao(this, empresa, "COMPRA", quantidade, empresa.getPrecoAcao());
                historicoTransacoes.add(transacao);
                return;
            }
        }

        carteira.add(new Acao(empresa, quantidade));
        System.out.println(nome + " comprou " + quantidade + " de " + empresa.getTicker());
        Transacao transacao = new Transacao(this, empresa, "COMPRA", quantidade, empresa.getPrecoAcao());
        historicoTransacoes.add(transacao);
    }

    public void vender(Empresa empresa, int quantidade) {
        receberDividendos();

        Acao acaoParaRemover = null; // guarda referência se precisar remover

        for (Acao acao : carteira) {
            if (acao.getEmpresa().equals(empresa)) {
                if (acao.getQuantidade() >= quantidade) {
                    double valorVenda = empresa.getPrecoAcao() * quantidade;
                    saldo += valorVenda;
                    acao.setQuantidade(acao.getQuantidade() - quantidade);

                    if (acao.getQuantidade() == 0) {
                        acaoParaRemover = acao; // marca para remover depois
                    }

                    System.out.println(nome + " vendeu " + quantidade + " de " + empresa.getTicker());
                    Transacao transacao = new Transacao(this, empresa, "VENDA", quantidade, empresa.getPrecoAcao());
                    historicoTransacoes.add(transacao);
                    break;
                }
            }
        }

        if (acaoParaRemover != null) {
            carteira.remove(acaoParaRemover);
        } else {
            System.out.println("Você não possui ações desta empresa.");
        }
    }

    public String nomearUsuario() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public double definirSaldo() {
        Scanner scanner = new Scanner(System.in);
        double saldo = scanner.nextDouble();
        if (saldo <= 0) {
            throw new ArithmeticException("Saldo inválido!!");
        }
        this.saldo = saldo;
        return saldo;
    }

    public void visualizarHistorico() {
        System.out.println("\n=== HISTÓRICO DE TRANSAÇÕES ===");
        System.out.println("Investidor: " + nome);
        System.out.println("--------------------------------");

        if (historicoTransacoes.isEmpty()) {
            System.out.println("Nenhuma transação realizada.");
            return;
        }

        for (Transacao transacao : historicoTransacoes) {
            System.out.printf("%s | %s | %d ações | R$ %.2f cada | %s%n",
                    transacao.getTipo(),
                    transacao.getEmpresa().getTicker(),
                    transacao.getQuantidade(),
                    transacao.getValorUnitario(),
                    transacao.getDataHora().toLocalDate());
        }
    }

    public void receberDividendos() {
        for (Acao acao : carteira) {
            Empresa empresa = acao.getEmpresa();
            double dividendos = empresa.getDividendos() * acao.getQuantidade();

            if (dividendos > 0) {
                saldoDividendos += dividendos;
                saldo += dividendos;
                System.out.println(nome + " recebeu R$ " + String.format("%.2f", dividendos)
                        + " em dividendos da " + empresa.getNome());

                Transacao transacao = new Transacao(this, empresa, "DIVIDENDO",
                        acao.getQuantidade(), dividendos / acao.getQuantidade());
                historicoTransacoes.add(transacao);
            }
        }
    }
}
