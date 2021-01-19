public class ThreadTrasferitoreInOrdine extends Thread {
    private ContoBancario origine;
    private ContoBancario destinazione;
    private int somma;

    public ThreadTrasferitoreInOrdine(int somma, ContoBancario origine, ContoBancario destinazione) {
        this.somma = somma;
        this.origine = origine;
        this.destinazione = destinazione;
    }

    @Override
    public void run() {
        origine.trasferisciInOrdine(somma, destinazione);
    }

}
