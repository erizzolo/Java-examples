public class App {

    public static final int PRODUCERS = 6;
    public static final int CONSUMERS = 5;

    public static void main(String[] args) throws Exception {
        test(new BufferStack());
        test(new BufferQueue());
        test(new BufferSingle());
    }

    private static void test(Buffer buffer) throws InterruptedException {
        System.out.println("");
        System.out.println("Test of " + buffer);

        for (int i = 0; i < PRODUCERS; i++) {
            new Producer("P" + i, buffer, i).start();
        }
        for (int i = 0; i < CONSUMERS; i++) {
            new Consumer("C" + i, buffer).start();
        }
        while (Thread.activeCount() > 1) {
            Thread.sleep(100);
        }
        System.out.println("Buffer contains " + buffer.size() + " elements");
    }
}
