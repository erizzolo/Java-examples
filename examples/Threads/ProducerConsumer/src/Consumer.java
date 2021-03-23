public class Consumer extends Thread {

    public static final int OBJECTS = 6;
    private Buffer buffer;
    private int total;

    public Consumer(String name, Buffer buffer) {
        super(name);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int o = 0; o < OBJECTS; ++o) {
            // provo a prelevare un nuovo oggetto
            Integer obj = buffer.get();
            // consumo il nuovo oggetto
            total += obj;
        }
        System.out.println(getName() + "'s total = " + total);

    }

}
