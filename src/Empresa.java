import java.util.Random;

public class Empresa {
    private String nome;
    private String ticker;
    private double precoAcao;

    public Empresa(String nome, String ticker, double precoAcao) {
        this.nome = nome;
        this.ticker = ticker;
        this.precoAcao = precoAcao;
    }

    public String getNome() {return nome;}
    public String getTicker() {return ticker;}
    public double getPrecoAcao() {return precoAcao;}

    public void setNome(String nome) {this.nome = nome;}
    public void setTicker(String ticker) {this.ticker = ticker;}
    public void setPrecoAcao(double precoAcao) {this.precoAcao = precoAcao;}

    public void Variacao(){
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


    }
}
