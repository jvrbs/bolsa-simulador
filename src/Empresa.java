import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Empresa {
    private String nome;
    private String ticker;
    private double precoAcao;
    private double precoAnterior;
    private Boolean pagaDividendos;
    private double percentualDividendos;
    private static List<Empresa> empresas = new ArrayList<>();


    public Empresa(String nome, String ticker, double precoAcao, Boolean pagaDividendos, double percentualDividendos) {
        this.nome = nome;
        this.ticker = ticker;
        this.precoAcao = precoAcao;
        this.precoAnterior = precoAcao;
        this.pagaDividendos = pagaDividendos;
        this.percentualDividendos = percentualDividendos;
        empresas.add(this);
    }

    public static List<Empresa> getEmpresas(){
        return new ArrayList<>(empresas);
    }

    public String getNome() {return nome;}
    public String getTicker() {return ticker;}
    public double getPrecoAcao() {return precoAcao;}
    public double getPrecoAnterior(){return precoAnterior;}

    public void setNome(String nome) {this.nome = nome;}
    public void setTicker(String ticker) {this.ticker = ticker;}
    public void setPrecoAcao(double precoAcao) {this.precoAcao = precoAcao;}



    public void Variacao() {
        precoAnterior = precoAcao;

        Random random = new Random();
        int passos = (int) ((10.0 - (-15.0)) / 0.5) + 1; // total de possibilidades
        int indice = random.nextInt(passos);             // sorteia o índice
        double variacaoPercentual = -15.0 + (indice * 0.5); // calcula a % sorteada
        precoAcao *= (1 + variacaoPercentual / 100.0);
        if (precoAcao < 1.0) {
            precoAcao = 1.0;
        }
        System.out.printf("Variação aplicada: %.1f%%%n", variacaoPercentual);
    }

    public double calculoDividendos(Double valor){
        if(!pagaDividendos) return 0.0;
        return valor * percentualDividendos;
    }

    public double getDividendos() {
        if (!pagaDividendos || precoAcao <= precoAnterior) return 0.0;
        double valorizacao = precoAcao - precoAnterior;
        return valorizacao * percentualDividendos;
    }

    public void visualizarEmpresas(){

    }


}
