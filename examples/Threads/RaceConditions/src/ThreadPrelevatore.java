public class ThreadPrelevatore extends Thread {
    private ContoBancario conto;
    private int somma;

    public ThreadPrelevatore(int somma, ContoBancario conto) {
        this.somma = somma;
        this.conto = conto;
    }

    @Override
    public void run() {
        conto.preleva(somma);
    }

}
