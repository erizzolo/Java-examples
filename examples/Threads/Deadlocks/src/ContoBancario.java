public class ContoBancario {
    private int saldo = 0;

    synchronized void preleva(int somma) {
        saldo -= somma;
    }

    synchronized void deposita(int somma) {
        saldo += somma;
    }

    synchronized void trasferisci(int somma, ContoBancario destinazione) {
        preleva(somma);
        destinazione.deposita(somma);
    }

    public int getSaldo() {
        return saldo;
    }

}
