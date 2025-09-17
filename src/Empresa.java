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



    public void Variacao(){
        precoAnterior = precoAcao;
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        switch (randomNumber){
            case 0: precoAcao *= 0.5; break;
            case 1: precoAcao *= 0.4; break;
            case 2: precoAcao *= 0.3; break;
            case 3: precoAcao *= 0.2; break;
            case 4: precoAcao *= 0.1; break;
            case 5: precoAcao *= 1.1; break;
            case 6: precoAcao *= 1.2; break;
            case 7: precoAcao *= 1.3; break;
            case 8: precoAcao *= 1.4; break;
            case 9: precoAcao *= 1.5; break;
        } if (precoAcao < 1) precoAcao = 1.0;
        System.out.println(randomNumber);

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
