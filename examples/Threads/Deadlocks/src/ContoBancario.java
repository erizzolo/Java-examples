public class ContoBancario {
    private int saldo = 0;

    synchronized void preleva(int somma) {
        saldo -= somma;
    }

    synchronized void deposita(int somma) {
        saldo += somma;
    }

    /* deadlock prone */
    synchronized void trasferisci(int somma, ContoBancario destinazione) {
        preleva(somma);
        destinazione.deposita(somma);
    }

    /* NO deadlock here */
    synchronized static void trasferisci(int somma, ContoBancario origine, ContoBancario destinazione) {
        origine.preleva(somma);
        destinazione.deposita(somma);
    }

    /* NO deadlock here */
    void trasferisciInOrdine(int somma, ContoBancario destinazione) {
        // ordino le richieste in base all'hashCode del conto
        if (this.hashCode() < destinazione.hashCode()) {
            trasferisci(somma, destinazione);
        } else {
            destinazione.trasferisci(-somma, this);
        }
    }

    public int getSaldo() {
        return saldo;
    }

}
