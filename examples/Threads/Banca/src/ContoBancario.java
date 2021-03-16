import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ContoBancario {

    private int saldo = 0;
    private Collection<Operazione> estratto;
    private Collection<Intestatario> intestatari;

    public ContoBancario() {
        this(0);
    }

    public ContoBancario(int saldo) {
        this.saldo = saldo;
        estratto = new ArrayList<>();
        intestatari = new HashSet<>();
        estratto.add(new Operazione(TipoOperazione.APERTURA, saldo, null));
    }

    public boolean addIntestatario(Intestatario i) {
        return intestatari.add(i);
    }

    public synchronized boolean preleva(int somma) {
        Intestatario ordinante = (Intestatario) Thread.currentThread();
        if (intestatari.contains(ordinante)) {
            boolean result = saldo >= somma;
            if (result) {
                saldo -= somma;
                estratto.add(new Operazione(TipoOperazione.PRELIEVO, somma, ordinante));
            }
            return result;
        } else {
            estratto.add(new Operazione(TipoOperazione.TENTATOFURTO, somma, ordinante));
            throw new UnsupportedOperationException("ordinante non intestatario");
        }
    }

    public synchronized boolean deposita(int somma) {
        saldo += somma;
        estratto.add(new Operazione(TipoOperazione.DEPOSITO, somma, (Intestatario) Thread.currentThread()));
        return true;
    }

    public int getSaldo() {
        return saldo;
    }

    public Collection<Operazione> getEstratto() {
        return new ArrayList<>(estratto);
    }

    @Override
    public String toString() {
        int total = 0;
        StringBuilder builder = new StringBuilder("ContoBancario: saldo " + saldo);
        for (Operazione operazione : estratto) {
            builder.append("\n" + operazione);
            switch (operazione.getTipo()) {
            case APERTURA:
            case DEPOSITO:
                total += operazione.getImporto();
                break;
            case PRELIEVO:
                total -= operazione.getImporto();
                break;
            default:
            }
        }
        builder.append(total == saldo ? "\nSaldo verificato" : "\nSaldo ERRATO!!!");
        return builder.toString();
    }

}
