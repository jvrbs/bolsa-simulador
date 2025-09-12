import java.util.List;
import java.util.ArrayList;


public class Usuario {
    private String nome;
    private double saldo;
    private List<Acao> carteira;

    public Usuario(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.carteira = new ArrayList<>();
    }

    public String getNome() {return nome;}
    public double getSaldo() {return saldo;}
    public List<Acao> getCarteira() {return carteira;}

    public void setNome(String nome) {this.nome = nome;}
    public void setSaldo(double saldo) {this.saldo = saldo;}
    public void setCarteira(List<Acao> carteira) {this.carteira = carteira;}

    public void comprar(Empresa empresa, int quantidade) {
        double custo = empresa.getPrecoAcao() * quantidade;
        if (saldo < custo){System.out.println("Saldo insuficiente!"); return;}
        saldo -= custo;
        carteira.add(new Acao(empresa, quantidade));
        System.out.println(nome + " Comprou " + quantidade + " de " + empresa.getTicker());
    }
}
