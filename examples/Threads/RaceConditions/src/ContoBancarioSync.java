public class ContoBancarioSync extends ContoBancario {

    public ContoBancarioSync(int saldo, boolean withYield) {
        super(saldo, withYield);
    }

    @Override
    synchronized boolean preleva(int somma) {
        return super.preleva(somma);
    }

}
