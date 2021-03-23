public class BufferQueue implements Buffer {

    private Integer[] data;

    private int size;
    private int insertion;

    public synchronized void put(Integer i) {
        while (size >= data.length) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        ++size;
        data[insertion] = i;
        ++insertion;
        if (insertion == data.length) {
            insertion = 0;
        }
        System.out.println(Thread.currentThread().getName() + " put " + i);
        notifyAll();
    }

    public synchronized Integer get() {
        while (size == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // insertion = 4, size = 3
        // 0 1 2 3 4
        // . x y z .
        // removal = 1
        // insertion = 1, size = 3
        // 0 1 2 3 4
        // z . . x y
        // removal = 3
        int removal = insertion - size;
        if (removal < 0) {
            removal += data.length;
        }
        --size;
        Integer result = data[removal];
        data[removal] = null;
        System.out.println("\t" + Thread.currentThread().getName() + " got " + result);
        notifyAll();
        return result;
    }

    public BufferQueue() {
        this(10);
    }

    public BufferQueue(int length) {
        data = new Integer[length];
    }

    public int size() {
        return size;
    }

}
