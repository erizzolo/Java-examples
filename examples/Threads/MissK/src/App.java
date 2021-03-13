public class App {
    public static void main(String[] args) throws Exception {
        test(new Bagno(Sesso.MASCHIO));
        test(new BagnoEquo(Sesso.MASCHIO));
    }

    private static void test(Bagno b) {
        System.out.println("Test of " + b);
        new Persona(Sesso.FEMMINA, b).start();
        new Persona(Sesso.MASCHIO, b).start();
        for (int i = 0; i < 5; i++) {
            new Persona(b.getSesso(), b).start();
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
