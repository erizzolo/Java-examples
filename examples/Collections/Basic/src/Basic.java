public class Basic {
    public static void main(String[] args) {
        try {
            puntowithoutequals.Collections.main(args);
        } catch (Exception e) {
            System.out.println("");
        }
        try {
            puntowithequals.Collections.main(args);
        } catch (Exception e) {
            System.out.println("");
        }
        try {
            puntowithequalsandhashcode.Collections.main(args);
        } catch (Exception e) {
            System.out.println("");
        }
        puntocomparable.Collections.main(args);
    }

}
