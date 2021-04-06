import java.util.Objects;
import java.util.Random;

public class CentroDiCura implements Runnable {

    public static void debug(Object what) {
        System.out.println(what.toString());
    }

    private static final Random rnd = new Random(2);

    public static final int DEFAULT_SPECIALIZZATI = 5;
    public static final int DEFAULT_ATTENDENTI = 5;
    private final Citta citta;
    private final int maxSpecializzati;
    private final int maxAttendenti;
    private int specializzatiDisponibili;
    private int attendenti;

    public CentroDiCura(Citta citta) {
        this(citta, DEFAULT_SPECIALIZZATI, DEFAULT_ATTENDENTI);
    }

    public CentroDiCura(Citta citta, int maxSpecializzati, int maxAttendenti) {
        this.citta = Objects.requireNonNull(citta, "citta cannot be null");
        this.maxSpecializzati = check(maxSpecializzati, 1, Integer.MAX_VALUE, "specializzati");
        this.maxAttendenti = check(maxAttendenti, 1, Integer.MAX_VALUE, "attendenti");
        specializzatiDisponibili = this.maxSpecializzati;
        attendenti = 0;
    }

    private int check(int value, int minValue, int maxValue, String message) {
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException(message + " not in [" + minValue + "," + maxValue + "]");
        }
        return value;
    }

    public void getDose(int which) {
        check(which, 0, Terapia.NUM_DOSI - 1, "indice dose");
        getSpecializzato();
        attendiSomministrazione(which);
        esci();
    }

    private int getDisponibili() {
        return specializzatiDisponibili;
    }

    private synchronized void getSpecializzato() {
        while (getDisponibili() == 0) {
            ++attendenti;
            debug(Thread.currentThread() + " deve attendere");
            checkHelp();
            try {
                wait();
            } catch (InterruptedException e) {
            }
            --attendenti;
        }
        --specializzatiDisponibili;
        debug(Thread.currentThread() + " entra");
    }

    private void attendiSomministrazione(int which) {
        try {
            Thread.sleep(randomIn(Terapia.MIN_DURATA, Terapia.MAX_DURATA));
        } catch (InterruptedException e) {
        }
        debug(Thread.currentThread() + " ha ricevuto la pacca " + (which + 1));
    }

    private synchronized void esci() {
        ++specializzatiDisponibili;
        notifyAll();
    }

    private void checkHelp() {
        if (attendenti > maxAttendenti) {
            debug(this + " asks for help");
            new Thread(this).start();
        }
    }

    private synchronized int getHelp() {
        debug("somebody asks for help to " + this);
        int trasferiti = specializzatiDisponibili / 2; // San Martino
        if (trasferiti > 0) {
            specializzatiDisponibili -= trasferiti;
            debug(this + " presta " + trasferiti + " specializzati");
        }
        return trasferiti;
    }

    @Override
    public synchronized void run() {
        askForHelp(this);
    }

    public static synchronized void askForHelp(CentroDiCura needs) {
        for (Citta other : Citta.values()) {
            if (other.centroDiCura != needs) {
                if (other.centroDiCura.specializzatiDisponibili > 1) {
                    int help = other.centroDiCura.getHelp();
                    if (help > 0) {
                        debug(needs + " ottiene " + help + " specializzati");
                        needs.specializzatiDisponibili += help;
                        needs.notifyAll();
                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        return "CentroDiCura di " + citta + ", disponibili " + specializzatiDisponibili + ", in attesa " + attendenti;
    }

    private int randomIn(int minValue, int MaxValue) {
        return minValue + rnd.nextInt(MaxValue - minValue + 1);
    }

}
