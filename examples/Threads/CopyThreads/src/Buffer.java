public class Buffer {

    private final byte[] data;
    private int size;

    public Buffer(int maxSize) {
        data = new byte[check(maxSize, 1, 1 << 20, "maxSize")];
    }

    public byte[] getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = check(size, 0, data.length, "size");
    }

    private final int check(int value, int min, int max, String name) {
        if (value < min || value > max)
            throw new IllegalArgumentException(name + " out of range [" + min + ", " + max + "]");
        return value;
    }

}
