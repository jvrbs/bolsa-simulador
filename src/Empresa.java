// FileName: /Empresa.java
//
// Propósito: Gerencia informações e comportamentos de uma empresa na simulação da bolsa.
//
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Empresa {
    private String nome;          // Nome da empresa.
    private String ticker;        // Símbolo de negociação.
    private double precoAcao;     // Preço atual da ação.
    private double precoAnterior; // Preço da ação no período anterior.
    private boolean pagaDividendos;  // Indica se a empresa paga dividendos.
    private double dividendYield;    // Percentual de dividendo anual.
    private static List<Empresa> empresas = new ArrayList<>(); // Lista de todas as empresas.
    private double lucroAnual;    // Lucro anual da empresa.
    private int acoesNaBolsa;     // Número de ações em circulação.

    // Construtor: Cria uma nova empresa e a adiciona à lista global.
    public Empresa(String nome, String ticker, double precoAcao, boolean pagaDividendos, double dividendYield, int acoesNaBolsa, double lucroAnual) {
        this.nome = nome;
        this.ticker = ticker;
        this.precoAcao = precoAcao;
        this.precoAnterior = precoAcao;
        this.pagaDividendos = pagaDividendos;
        this.dividendYield = dividendYield;
        empresas.add(this);
        this.acoesNaBolsa = acoesNaBolsa;
        this.lucroAnual = lucroAnual;
    }

    // Retorna uma cópia da lista de todas as empresas.
    public static List<Empresa> getEmpresas() {
        return new ArrayList<>(empresas);
    }

    // Getters para acessar os atributos da empresa.
    public String getNome() { return nome; }
    public String getTicker() { return ticker; }
    public double getPrecoAcao() { return precoAcao; }
    public double getPrecoAnterior() { return precoAnterior; }
    public double getLucroAnual() { return lucroAnual; }
    public int getAcoesNaBolsa() { return acoesNaBolsa; }
    public double getMarketCap() { return precoAcao * acoesNaBolsa; } // Capitalização de mercado.
    public double getPL() { // Índice Preço/Lucro.
        double lucroPorAcao = lucroAnual / acoesNaBolsa;
        return precoAcao / lucroPorAcao;
    }

    public boolean pagaDividendos() { return this.pagaDividendos; } // Verifica se paga dividendos.
    public double getDividendYield() { return this.dividendYield; } // Retorna o dividend yield.

    // Setters para modificar atributos da empresa.
    public void setNome(String nome) { this.nome = nome; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public void setPrecoAcao(double precoAcao) { this.precoAcao = precoAcao; }

    // Simula a variação diária do preço da ação.
    public void Variacao() {
        precoAnterior = precoAcao;
        Random random = new Random();
        double prob = random.nextDouble();
        double variacaoPercentual = 0.0;
        if (prob < 0.6) {
            variacaoPercentual = 0.5 + (random.nextInt(20) * 0.5);
        } else {
            variacaoPercentual = -0.5 - (random.nextInt(30) * 0.5);
        }
        precoAcao *= (1 + variacaoPercentual / 100.0);
        if (precoAcao < 1.0) {
            precoAcao = 1.0;
        }
    }

    // Simula o impacto de uma compra no preço da ação.
    public void variacaoCompra(int qnt) {
        double valorInvestido = qnt * precoAcao;
        double impactoBase = (valorInvestido / 50000.0) * 0.01;
        double fatorReducao = 100000000.0 / getMarketCap();
        if (fatorReducao > 1.0) fatorReducao = 1.0;
        double valorizacao = impactoBase * fatorReducao;
        if (valorizacao > 0.03) valorizacao = 0.03;
        precoAnterior = precoAcao;
        precoAcao *= (1 + valorizacao);
        System.out.printf("%s valorizou %.2f%% após a compra%n", ticker, valorizacao * 100);
    }

    // Calcula os dividendos com base em um valor e no dividend yield.
    public double calculoDividendos(double valor) {
        if (!pagaDividendos) return 0.0;
        return valor * dividendYield;
    }
}