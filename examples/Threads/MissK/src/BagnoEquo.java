import java.util.ArrayDeque;
import java.util.Queue;

public class BagnoEquo extends Bagno {

    private Queue<Persona> coda;

    public BagnoEquo(Sesso sesso) {
        super(sesso);
        coda = new ArrayDeque<>();
    }

    @Override
    public int getPersoneInCoda() {
        return coda.size();
    }

    @Override
    protected boolean canEnter() {
        boolean result = super.canEnter() && (coda.peek() == null || coda.peek().equals(Thread.currentThread()));
        if (result) {
            coda.remove(Thread.currentThread());
        } else {
            if (!coda.contains(Thread.currentThread())) {
                coda.add((Persona) Thread.currentThread());
            }
        }
        return result;
    }

}
