package geometry;

/**
 *
 * @author emanuele
 */
public class Geometry {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Punto.DEBUG = true;
        Punto.debug("Building a default Punto...");
        Punto pDefault = new Punto();
        System.out.println("Default Punto: " + pDefault);
        Punto.debug("Building another Punto...");
        Punto p = new Punto(5.0, -3.0);
        System.out.println("Altro   Punto: " + p);
        Punto.debug("Building a default PuntoMobile...");
        PuntoMobile pmDefault = new PuntoMobile();
        System.out.println("Default PuntoMobile: " + pmDefault);
        pmDefault.move(1.0, 1.0);
        System.out.println("Default PuntoMobile traslato: " + pmDefault);
        try {
            Punto.debug("Cerco di impostare coordinate non valide...");
            pmDefault.moveToBlackHole();
            Punto.debug("Questa istruzione non sarà eseguita...");
        } catch (IllegalArgumentException e) {
            Punto.debug("Intercettata eccezione: " + e);
        } finally {
            Punto.debug("Questa istruzione sarà comunque eseguita...");
        }
        Punto.debug("Termine applicazione");
    }

}
