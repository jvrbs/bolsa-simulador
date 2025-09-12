public class Acao {
    private Empresa empresa;
    private int quantidade;

    public Acao(Empresa empresa, int quantidade) {
        this.empresa = empresa;
        this.quantidade = quantidade;
    }

    public Empresa getEmpresa() {return empresa;}
    public int getQuantidade() {return quantidade;}

    public void setEmpresa(Empresa empresa) {this.empresa = empresa;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
}
