// FileName: /Acao.java
//
// Propósito: Representa uma quantidade de ações de uma empresa específica na carteira de um usuário.
//
public class Acao {
    private Empresa empresa;  // A empresa à qual as ações pertencem.
    private int quantidade;   // O número de ações.

    // Construtor: Cria uma nova instância de Acao.
    public Acao(Empresa empresa, int quantidade) {
        this.empresa = empresa;
        this.quantidade = quantidade;
    }

    // Getters para acessar a empresa e a quantidade.
    public Empresa getEmpresa() {return empresa;}
    public int getQuantidade() {return quantidade;}

    // Setters para modificar a empresa e a quantidade.
    public void setEmpresa(Empresa empresa) {this.empresa = empresa;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
}