public class BufferStack implements Buffer {

    private Integer[] data;

    private int size;

    public synchronized Integer get() {
        while (size == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        size--;
        Integer result = data[size];
        data[size] = null;
        System.out.println("\t" + Thread.currentThread().getName() + " got " + result);
        notifyAll();
        return result;
    }

    public synchronized void put(Integer i) {
        while (size == data.length) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        data[size] = i;
        size++;
        System.out.println(Thread.currentThread().getName() + " put " + i);
        notifyAll();
    }

    public BufferStack() {
        this(10);
    }

    public BufferStack(int length) {
        data = new Integer[length];
    }

    public int size() {
        return size;
    }

}
