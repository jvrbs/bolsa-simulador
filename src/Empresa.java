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
    private double lucroAnual;
    private int acoesNaBolsa;



    public Empresa(String nome, String ticker, double precoAcao, Boolean pagaDividendos, double percentualDividendos, int acoesNaBolsa, double lucroAnual) {
        this.nome = nome;
        this.ticker = ticker;
        this.precoAcao = precoAcao;
        this.precoAnterior = precoAcao;
        this.pagaDividendos = pagaDividendos;
        this.percentualDividendos = percentualDividendos;
        empresas.add(this);
        this.acoesNaBolsa = acoesNaBolsa;
        this.lucroAnual = lucroAnual;
    }

    public static List<Empresa> getEmpresas(){
        return new ArrayList<>(empresas);
    }

    public String getNome() {return nome;}
    public String getTicker() {return ticker;}
    public double getPrecoAcao() {return precoAcao;}
    public double getPrecoAnterior(){return precoAnterior;}
    public double getLucroAnual() { return lucroAnual; }
    public int getAcoesNaBolsa() { return acoesNaBolsa; }
    public double getMarketCap() { return precoAcao * acoesNaBolsa; }
    public double getPL() {
        double lucroPorAcao = lucroAnual / acoesNaBolsa;
        return precoAcao / lucroPorAcao;
    }

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

    public void variacaoCompra(int qnt) {
        // Valor investido na compra
        double valorInvestido = qnt * precoAcao;

        // Impacto base: R$ 50.000 investidos = 1% de valorização
        double impactoBase = (valorInvestido / 50000.0) * 0.01;

        // Fator de redução baseado no market cap (empresas maiores = menor impacto)
        double marketCap = getMarketCap();
        double fatorReducao = 100000000.0 / marketCap; // 100 milhões como referência
        if (fatorReducao > 1.0) fatorReducao = 1.0; // Não amplifica, só reduz

        // Calcula valorização final com limite de 3%
        double valorizacao = impactoBase * fatorReducao;
        if (valorizacao > 0.03) valorizacao = 0.03; // Máximo 3%

        // Aplica a valorização
        precoAnterior = precoAcao;
        precoAcao *= (1 + valorizacao);

        System.out.printf("%s valorizou %.2f%% após a compra%n", ticker, valorizacao * 100);
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
