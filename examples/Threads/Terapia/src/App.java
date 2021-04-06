public class App {
    public static void main(String[] args) throws Exception {
        for (Citta citta : Citta.values()) {
            // everybody needs to be cured
            for (int i = 0; i < citta.citizens; i++) {
                new Cittadino("C" + (i + 1), citta).start();
            }
        }
        while(Thread.activeCount() > 1) {
            Thread.sleep(100);
        }
        for (Citta citta : Citta.values()) {
            System.out.println(citta.getCentroDiCura());
        }
    }
}
