import java.util.Objects;
import java.util.Random;

public class Persona extends Thread {

    private static final Random random = new Random();

    private final Sesso sesso;
    private final Bagno bagno;
    private int maxWaiting;

    public Persona(Sesso sesso, Bagno bagno, int maxWaiting) {
        this.sesso = Objects.requireNonNull(sesso, "null is not sexy");
        this.bagno = Objects.requireNonNull(bagno, "null is not a wc");
        this.maxWaiting = maxWaiting;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public Bagno getBagno() {
        return bagno;
    }

    public int howLongDoesItTake() {
        return random.nextInt(1000);
    }

    @Override
    public void run() {
        while (random.nextInt(4) != 0) {
            bagno.usa(maxWaiting);
            try {
                sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return sesso + " " + getId();
    }

}
