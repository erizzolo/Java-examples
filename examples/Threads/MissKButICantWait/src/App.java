import java.util.Random;

public class App {
    public static final Random rnd = new Random();

    public static void main(String[] args) throws Exception {
        test(new Bagno(Sesso.MASCHIO));
    }

    private static void test(Bagno b) {
        System.out.println("Test of " + b);
        new Persona(Sesso.FEMMINA, b, rnd.nextInt(10)).start();
        new Persona(Sesso.MASCHIO, b, rnd.nextInt(10)).start();
        for (int i = 0; i < 10; i++) {
            new Persona(b.getSesso(), b, 10 + i * 10).start();
        }
        while (Thread.activeCount() > 1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("End of test of " + b);
    }
}
