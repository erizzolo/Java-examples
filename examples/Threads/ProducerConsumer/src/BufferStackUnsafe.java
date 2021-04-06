public class BufferStackUnsafe implements Buffer {

    private Integer[] data;

    private int size;

    public Integer get() {
        while (size == 0)
            ;
        size--;
        Integer result = data[size];
        data[size] = null;
        System.out.println("\t" + Thread.currentThread().getName() + " got " + result);
        return result;
    }

    public void put(Integer i) {
        while (size >= data.length)
            ;
        data[size] = i;
        size++;
        System.out.println(Thread.currentThread().getName() + " put " + i);
    }

    public BufferStackUnsafe() {
        this(10);
    }

    public BufferStackUnsafe(int length) {
        data = new Integer[length];
    }

    public int size() {
        return size;
    }

}
