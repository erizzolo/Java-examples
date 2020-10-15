package cronometro;

/**
 *
 * @author emanuele
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cronometro first = new Cronometro();
        Cronometro second = new Cronometro();
        System.out.println(first);
        System.out.println(second);
        first.start();
        second.start();
        System.out.println(first);
        System.out.println(first);
        first.start();
        System.out.println(first);
        System.out.println(first);
        first.stop();
        second.stop();
        System.out.println(first);
        System.out.println(second);
        first.stop();
        System.out.println(first);
        first.reset();
        System.out.println(first);
    }
    
}
