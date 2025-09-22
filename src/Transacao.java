// FileName: /Transacao.java
//
// Propósito: Representa e armazena os detalhes de uma única transação financeira.
//
import java.time.LocalDateTime;

public class Transacao {
    private Usuario usuario;       // Usuário que realizou a transação.
    private Empresa empresa;       // Empresa envolvida na transação.
    private String tipo;           // Tipo da transação (COMPRA, VENDA, DIVIDENDO).
    private int quantidade;        // Quantidade de ações.
    private double valorUnitario;  // Valor por unidade na transação.
    private LocalDateTime dataHora; // Data e hora da transação.

    // Construtor: Cria uma nova transação com os dados fornecidos e a data/hora atual.
    public Transacao(Usuario usuario, Empresa empresa, String tipo, int quantidade, double valorUnitario) {
        this.usuario = usuario;
        this.empresa = empresa;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataHora = LocalDateTime.now();
    }

    // Getters para acessar os detalhes da transação.
    public Usuario getUsuario() {return usuario;}
    public Empresa getEmpresa() {return empresa;}
    public String getTipo() {return tipo;}
    public int getQuantidade() {return quantidade;}
    public double getValorUnitario() {return valorUnitario;}
    public LocalDateTime getDataHora() {return dataHora;}

    // Setters para modificar os detalhes da transação.
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public void setEmpresa(Empresa empresa) {this.empresa = empresa;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public void setValorUnitario(double valorUnitario) {this.valorUnitario = valorUnitario;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}
}