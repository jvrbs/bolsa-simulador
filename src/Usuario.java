// FileName: /Usuario.java
//
// Propósito: Representa um investidor, gerenciando seu saldo, carteira de ações, histórico e dividendos.
//
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {
    private String nome;          // Nome do usuário.
    private double saldo;         // Saldo em dinheiro do usuário.
    private List<Acao> carteira;  // Ações que o usuário possui.
    private List<Transacao> historicoTransacoes; // Registro de todas as operações.
    private double saldoDividendos; // Dividendos acumulados pendentes de transferência.
    private double dividendosRecebidosNoDia; // Dividendos recebidos no dia atual da simulação.

    // Construtor: Inicializa um usuário com nome, saldo e estruturas de dados vazias.
    public Usuario(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.carteira = new ArrayList<>();
        this.historicoTransacoes = new ArrayList<>();
        this.saldoDividendos = 0.0;
        this.dividendosRecebidosNoDia = 0.0;
    }

    // Construtor padrão.
    public Usuario() {
        this("Não Definido", 0.0);
    }

    // Getters para acessar os atributos do usuário.
    public List<Transacao> getHistoricoTransacoes() { return historicoTransacoes; }
    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    public List<Acao> getCarteira() { return carteira; }
    public double getSaldoDividendos() { return saldoDividendos; }

    // Setters para modificar atributos do usuário.
    public void setNome(String nome) { this.nome = nome; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setCarteira(List<Acao> carteira) { this.carteira = carteira; }

    // Realiza a compra de ações, atualizando saldo e carteira.
    public void comprar(Empresa empresa, int quantidade) {
        double custo = empresa.getPrecoAcao() * quantidade;
        if (saldo < custo) { System.out.println("Saldo insuficiente!"); return; }
        saldo -= custo;
        boolean acaoExistente = false;
        for (Acao acao : carteira) {
            if (acao.getEmpresa().equals(empresa)) { acao.setQuantidade(acao.getQuantidade() + quantidade); acaoExistente = true; break; }
        }
        if (!acaoExistente) { carteira.add(new Acao(empresa, quantidade)); }
        System.out.println(nome + " comprou " + quantidade + " de " + empresa.getTicker());
        historicoTransacoes.add(new Transacao(this, empresa, "COMPRA", quantidade, empresa.getPrecoAcao()));
    }

    // Realiza a venda de ações, atualizando saldo e carteira.
    public void vender(Empresa empresa, int quantidade) {
        Acao acaoParaRemover = null;
        boolean vendaRealizada = false;
        for (Acao acao : carteira) {
            if (acao.getEmpresa().equals(empresa)) {
                if (acao.getQuantidade() >= quantidade) {
                    double valorVenda = empresa.getPrecoAcao() * quantidade;
                    saldo += valorVenda; acao.setQuantidade(acao.getQuantidade() - quantidade);
                    if (acao.getQuantidade() == 0) { acaoParaRemover = acao; }
                    System.out.println(nome + " vendeu " + quantidade + " de " + empresa.getTicker());
                    historicoTransacoes.add(new Transacao(this, empresa, "VENDA", quantidade, empresa.getPrecoAcao()));
                    vendaRealizada = true; break;
                } else { System.out.println("Você não possui ações suficientes desta empresa para vender essa quantidade."); return; }
            }
        }
        if (acaoParaRemover != null) { carteira.remove(acaoParaRemover); }
        else if (!vendaRealizada) { System.out.println("Você não possui ações desta empresa."); }
    }

    // Exibe o histórico de todas as transações do usuário.
    public void visualizarHistorico() {
        System.out.println("\n=== HISTÓRICO DE TRANSAÇÕES ===");
        System.out.println("Investidor: " + nome);
        System.out.println("--------------------------------");
        if (historicoTransacoes.isEmpty()) { System.out.println("Nenhuma transação realizada."); return; }
        for (Transacao transacao : historicoTransacoes) {
            System.out.printf("%s | %s | %d ações | R$ %.2f cada | %s%n",
                    transacao.getTipo(), transacao.getEmpresa().getTicker(),
                    transacao.getQuantidade(), transacao.getValorUnitario(),
                    transacao.getDataHora().toLocalDate());
        }
    }

    // Calcula e acumula dividendos com base nas ações da carteira.
    public void receberDividendos() {
        this.dividendosRecebidosNoDia = 0.0;
        for (Acao acao : carteira) {
            Empresa empresa = acao.getEmpresa();
            if (empresa.pagaDividendos()) {
                double valorTotalAcoes = acao.getQuantidade() * empresa.getPrecoAcao();
                double dividendos = empresa.calculoDividendos(valorTotalAcoes);
                if (dividendos > 0) {
                    saldoDividendos += dividendos;
                    dividendosRecebidosNoDia += dividendos;
                    historicoTransacoes.add(new Transacao(this, empresa, "DIVIDENDO", acao.getQuantidade(), dividendos / acao.getQuantidade()));
                }
            }
        }
    }

    // Exibe os dividendos recebidos no dia atual.
    public void mostrarDividendosDoDia() {
        if (dividendosRecebidosNoDia > 0) { System.out.printf("  %s recebeu R$ %.2f em dividendos hoje.%n", nome, dividendosRecebidosNoDia); }
        else { System.out.println("  Nenhum dividendo recebido hoje."); }
    }

    // Transfere dividendos acumulados para o saldo principal.
    public void transferirDividendosParaSaldo() {
        if (saldoDividendos > 0) {
            saldo += saldoDividendos;
            System.out.printf("R$ %.2f em dividendos transferidos para o saldo principal.%n", saldoDividendos);
            saldoDividendos = 0.0;
        } else { System.out.println("Nenhum dividendo acumulado para transferir."); }
    }
}