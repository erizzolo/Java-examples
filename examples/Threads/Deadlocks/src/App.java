import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class App {
    public static void main(String[] args) {
        // probabilmente nessuna "anomalia"
        test(new ContoBancario(), new ContoBancario(), 10);
        // quasi certamente almeno una "anomalia"
        test(new ContoBancario(), new ContoBancario(), 100_000);
        // certamente nessuna "anomalia"
        testStatic(new ContoBancario(), new ContoBancario(), 1_000_000);
        // certamente nessuna "anomalia"
        testOrdine(new ContoBancario(), new ContoBancario(), 1_000_000);
        System.exit(0); // needed because there may be hanging threads
    }

    private static void test(ContoBancario da, ContoBancario a, int tests) {
        System.out.println("test di " + da + " e " + a);
        Temporal started = LocalDateTime.now();
        for (int i = 0; i < tests; i++) {
            ThreadTrasferitore t1 = new ThreadTrasferitore(250, da, a);
            ThreadTrasferitore t2 = new ThreadTrasferitore(350, a, da);
            t1.start();
            t2.start();
            try {
                t2.join(1000); // 1000 ms = 1 s
                if (t1.isAlive() && t2.isAlive()) {
                    System.out.println("test " + i + ": DEADLOCK!!!");
                    return;
                }
            } catch (InterruptedException ex) {
            }
        }
        Duration elapsed = Duration.between(started, LocalDateTime.now());
        System.out.println("Completed in " + elapsed);
    }

    private static void testStatic(ContoBancario da, ContoBancario a, int tests) {
        System.out.println("test (Static) di " + da + " e " + a);
        Temporal started = LocalDateTime.now();
        for (int i = 0; i < tests; i++) {
            ThreadTrasferitoreStatic t1 = new ThreadTrasferitoreStatic(250, da, a);
            ThreadTrasferitoreStatic t2 = new ThreadTrasferitoreStatic(350, a, da);
            t1.start();
            t2.start();
            try {
                t2.join(1000); // 1000 ms = 1 s
                if (t1.isAlive() && t2.isAlive()) {
                    System.out.println("test " + i + ": DEADLOCK!!!");
                    return;
                }
            } catch (InterruptedException ex) {
            }
        }
        Duration elapsed = Duration.between(started, LocalDateTime.now());
        System.out.println("Completed in " + elapsed);
    }

    private static void testOrdine(ContoBancario da, ContoBancario a, int tests) {
        System.out.println("test (in ordine) di " + da + " e " + a);
        Temporal started = LocalDateTime.now();
        for (int i = 0; i < tests; i++) {
            ThreadTrasferitoreInOrdine t1 = new ThreadTrasferitoreInOrdine(250, da, a);
            ThreadTrasferitoreInOrdine t2 = new ThreadTrasferitoreInOrdine(350, a, da);
            t1.start();
            t2.start();
            try {
                t2.join(1000); // 1000 ms = 1 s
                if (t1.isAlive() && t2.isAlive()) {
                    System.out.println("test " + i + ": DEADLOCK!!!");
                    return;
                }
            } catch (InterruptedException ex) {
            }
        }
        Duration elapsed = Duration.between(started, LocalDateTime.now());
        System.out.println("Completed in " + elapsed);
    }
}
