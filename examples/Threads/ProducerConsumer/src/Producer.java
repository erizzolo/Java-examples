public class Producer extends Thread {

    public static final int OBJECTS = 5;
    private Buffer buffer;
    private int index;

    public Producer(String name, Buffer buffer, int index) {
        super(name);
        this.buffer = buffer;
        this.index = index;
    }

    @Override
    public void run() {
        for (int o = 0; o < OBJECTS; ++o) {
            // genero il nuovo oggetto
            Integer obj = o + index * 100;
            // provo a depositarlo
            buffer.put(obj);
        }
    }

}
