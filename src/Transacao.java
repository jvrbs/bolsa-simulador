import java.time.LocalDateTime;

public class Transacao {
    private Usuario usuario;
    private Empresa empresa;
    private String tipo;
    private int quantidade;
    private double valorUnitario;
    private LocalDateTime dataHora;

    public Transacao(Usuario usuario, Empresa empresa, String tipo, int quantidade, double valorUnitario) {
        this.usuario = usuario;
        this.empresa = empresa;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataHora = LocalDateTime.now();
    }

    public Usuario getUsuario() {return usuario;}
    public Empresa getEmpresa() {return empresa;}
    public String getTipo() {return tipo;}
    public int getQuantidade() {return quantidade;}
    public double getValorUnitario() {return valorUnitario;}
    public LocalDateTime getDataHora() {return dataHora;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public void setEmpresa(Empresa empresa) {this.empresa = empresa;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public void setValorUnitario(double valorUnitario) {this.valorUnitario = valorUnitario;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    }


