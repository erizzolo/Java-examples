import java.util.ArrayDeque;
import java.util.Queue;

public class BufferPool {

    public static boolean DEBUG = true;

    private void debug(Object what) {
        debug(what, DEBUG);
    }

    private void debug(Object what, boolean really) {
        if (really)
            System.out.println(what.toString());
    }

    public final int bufferSize;
    public final int numBuffers;
    private final Queue<Buffer> emptyBuffers;
    private final Queue<Buffer> fullBuffers;

    public BufferPool(int bufferSize, int numBuffers) {
        this.bufferSize = check(bufferSize, 1, 1 << 20, "bufferSize");
        this.numBuffers = check(numBuffers, 1, 1 << 6, "numBuffers");
        emptyBuffers = new ArrayDeque<>(numBuffers);
        for (int i = 0; i < numBuffers; i++) {
            emptyBuffers.offer(new Buffer(bufferSize));
        }
        fullBuffers = new ArrayDeque<>(numBuffers);
    }

    public synchronized Buffer getEmpty() {
        while (getNumEmptyBuffers() == 0) { // no empty buffers
            debug(Thread.currentThread() + " waiting for Empty buffer");
            try {
                wait(); // go to sleep... and try again
            } catch (InterruptedException e) {
            }
        }
        debug(Thread.currentThread() + " got Empty buffer " + emptyBuffers.peek());
        return emptyBuffers.poll();
    }

    public synchronized void putFull(Buffer filled) {
        while (!fullBuffers.offer(filled)) { // full queue...
            debug("NEVER!!! " + Thread.currentThread() + " writing Full buffer");
            try {
                wait(); // go to sleep... and try again
            } catch (InterruptedException e) {
            }
        }
        notifyAll(); // wake up all sleeping threads !!!
    }

    public synchronized Buffer getFull() {
        while (getNumFullBuffers() == 0) {
            debug(Thread.currentThread() + " waiting for Full buffer");
            try {
                wait(); // go to sleep... and try again
            } catch (InterruptedException e) {
            }
        }
        debug(Thread.currentThread() + " got Full buffer " + fullBuffers.peek());
        return fullBuffers.poll();
    }

    public synchronized void putEmpty(Buffer emptied) {
        while (!emptyBuffers.offer(emptied)) { // full queue...
            debug("NEVER!!! " + Thread.currentThread() + " returning Empty buffer");
            try {
                wait(); // go to sleep... and try again
            } catch (InterruptedException e) {
            }
        }
        notifyAll(); // wake up all sleeping threads !!!
    }

    private final int check(int value, int min, int max, String name) {
        if (value < min || value > max)
            throw new IllegalArgumentException(name + " out of range [" + min + ", " + max + "]");
        return value;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getNumBuffers() {
        return numBuffers;
    }

    public int getNumEmptyBuffers() {
        return emptyBuffers.size();
    }

    public int getNumFullBuffers() {
        return fullBuffers.size();
    }

}
