public class BufferSingle implements Buffer {

    private Integer data;

    private boolean present;

    public synchronized Integer get() {
        while (!present) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        present = false;
        Integer result = data;
        data = null;
        System.out.println("\t" + Thread.currentThread().getName() + " got " + result);
        notifyAll();
        return result;
    }

    public synchronized void put(Integer i) {
        while (present) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        data = i;
        present = true;
        System.out.println(Thread.currentThread().getName() + " put " + i);
        notifyAll();
    }

    public BufferSingle() {
    }

    public int size() {
        return present ? 1 : 0;
    }

}
