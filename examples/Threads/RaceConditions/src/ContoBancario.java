public class ContoBancario {
    private int saldo;
    private boolean withYield;

    public ContoBancario(int saldo, boolean withYield) {
        this.saldo = saldo;
        this.withYield = withYield;
    }

    boolean preleva(int somma) {
        if (saldo >= somma) {
            if (withYield)
                Thread.yield(); // ad hoc !!!
            saldo -= somma;
            return true;
        } else
            return false;
    }

    boolean deposita(int somma) {
        saldo += somma;
        return true;
    }

    public int getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return super.toString() + " [saldo = " + getSaldo() + " ]";
    }

}