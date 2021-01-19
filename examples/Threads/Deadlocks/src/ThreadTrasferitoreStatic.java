public class ThreadTrasferitoreStatic extends Thread {
    private ContoBancario origine;
    private ContoBancario destinazione;
    private int somma;

    public ThreadTrasferitoreStatic(int somma, ContoBancario origine, ContoBancario destinazione) {
        this.somma = somma;
        this.origine = origine;
        this.destinazione = destinazione;
    }

    @Override
    public void run() {
        ContoBancario.trasferisci(somma, origine, destinazione);
    }

}
