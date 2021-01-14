public class App {
    public static void main(String[] args) {
        ContoBancario c1 = new ContoBancario();
        ContoBancario c2 = new ContoBancario();
        // probabilmente nessuna "anomalia"
        test(c1, c2, 10);
        // quasi certamente almeo una "anomalia"
        test(c1, c2, 100000);
    }

    private static void test(ContoBancario da, ContoBancario a, int tests) {
        System.out.println("test di " + da + " e " + a);
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
    }
}
