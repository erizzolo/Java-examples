import java.util.Objects;

public class Bagno {

    public static final void debug(Object what) {
        if (true) {
            System.out.println("" + what);
        }
    }

    private static long nextID;

    private final long id;
    private final Sesso sesso;

    private Persona occupante;
    private int waiting;

    public Bagno(Sesso sesso) {
        this.sesso = Objects.requireNonNull(sesso, "null is not sexy");
        id = ++nextID;
    }

    public long getId() {
        return id;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public Persona getOccupante() {
        throw new UnsupportedOperationException("None of your business");
    }

    public boolean isOccupato() {
        return occupante != null;
    }

    public int getPersoneInCoda() {
        return waiting;
    }

    public void usa() {
        Persona bisognoso = (Persona) Thread.currentThread();
        debug(bisognoso + " wants to use " + this);
        if (bisognoso.getSesso() == sesso) {
            entra();
            doIt();
            esci();
        } else {
            throw new UnsupportedOperationException("Wrong sex");
        }
    }

    protected boolean canEnter() {
        return !isOccupato();
    }

    private synchronized void entra() {
        Persona bisognoso = (Persona) Thread.currentThread();
        ++waiting;
        while (!canEnter()) {
            debug(bisognoso + " waiting for " + this);
            try {
                wait();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
        --waiting;
        occupante = bisognoso;
        debug(bisognoso + " finally enters " + this);
    }

    private void doIt() {
        Persona bisognoso = (Persona) Thread.currentThread();
        if (bisognoso != occupante) {
            debug(bisognoso + " is doing it outside " + this);
            bisognoso.setPriority(Thread.MIN_PRIORITY);
            Thread.yield();
            throw new UnsupportedOperationException("get out of here!");
        } else {
            debug(occupante + " is doing it inside " + this);
            try {
                Thread.sleep(occupante.howLongDoesItTake());
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
            debug(occupante + " is done with " + this);
        }
    }

    private synchronized void esci() {
        Persona bisognoso = (Persona) Thread.currentThread();
        if (bisognoso != occupante) {
            throw new UnsupportedOperationException("you're not here!");
        } else {
            debug(occupante + " is leaving " + this);
            occupante = null;
            notify();
        }
    }

    @Override
    public String toString() {
        return "Bagno " + id + " per " + sesso + " (" + (occupante == null ? "FREE-" : "BUSY-")
                + (getPersoneInCoda() == 0 ? "NONE" : getPersoneInCoda()) + " WAITING)";
    }

}
