public class Main {
    public static void main(String[] args) {
        Empresa Petrobras = new Empresa("Petrobras", "PETR4", 35.0, true, 0.05);
        Empresa Vale = new Empresa("Vale", "VALE3", 70.0, false, 0);

        Usuario joao = new Usuario("joão", 1000.0);

        joao.comprar(Petrobras, 10);
        System.out.println(joao.getSaldo());
        Petrobras.Variacao();
        Petrobras.Variacao();
        Petrobras.Variacao();
        joao.vender(Petrobras, 10);
        System.out.println(joao.getSaldo());
        joao.visualizarHistorico();
    }
}