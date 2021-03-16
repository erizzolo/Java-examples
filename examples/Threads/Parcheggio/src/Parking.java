import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class Parking {

    public static boolean DEBUG = true;

    private void debug(Object what) {
        debug(what, DEBUG);
    }

    private void debug(Object what, boolean really) {
        if (really)
            System.out.println(what.toString());
    }

    public final int places;
    private final Queue<ParkingPlace> emptyPlaces;

    public Parking(int places) {
        this.places = check(places, 1, 1 << 6, "numPlaces");
        emptyPlaces = new ArrayDeque<>(places);
        for (int i = 0; i < places; i++) {
            emptyPlaces.offer(new ParkingPlace());
        }
    }

    public synchronized ParkingPlace park() {
        while (getNumEmptyPlaces() == 0) { // no empty places
            debug(Thread.currentThread() + " waiting for Empty place");
            try {
                wait(); // go to sleep... and try again
            } catch (InterruptedException e) {
            }
        }
        debug(Thread.currentThread() + " got Empty place " + emptyPlaces.peek());
        return emptyPlaces.poll();
    }

    public synchronized void leave(ParkingPlace occupied) {
        emptyPlaces.add(Objects.requireNonNull(occupied));
        debug(Thread.currentThread() + " leaving " + occupied);
        notifyAll(); // wake up all sleeping threads !!!
    }

    private final int check(int value, int min, int max, String name) {
        if (value < min || value > max)
            throw new IllegalArgumentException(name + " out of range [" + min + ", " + max + "]");
        return value;
    }

    public int getNumplaces() {
        return places;
    }

    public int getNumEmptyPlaces() {
        return emptyPlaces.size();
    }

}
