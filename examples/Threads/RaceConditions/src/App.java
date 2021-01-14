public class App {
    public static void main(String[] args) {
        // probabilmente nessuna "anomalia"
        test(new ContoBancario(500, false), 100000);
        // quasi certamente alcune "anomalie"
        test(new ContoBancario(500, true), 20);
        // sicuramente nessuna "anomalia"
        test(new ContoBancarioSync(500, true), 100000);
    }

    private static void test(ContoBancario conto, int tests) {
        System.out.println("test di " + conto);
        for (int i = 0; i < tests; i++) {
            conto.deposita(500 - conto.getSaldo());
            ThreadPrelevatore p1 = new ThreadPrelevatore(250, conto);
            ThreadPrelevatore p2 = new ThreadPrelevatore(350, conto);
            p1.start();
            p2.start();
            try {
                p1.join();
                p2.join();
            } catch (InterruptedException e) {
            }
            if (conto.getSaldo() < 0) {
                System.out.println("OOPS!!! " + conto);
            }
        }
    }
}
