public class App extends Thread {

    static long totale;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new App();
        Thread t2 = new App();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Totale = " + totale);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1_000_000; i++) {
            totale = totale + 1;
        }
    }

}
