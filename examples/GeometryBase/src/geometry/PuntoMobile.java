package geometry;

/**
 * La classe PuntoMobile rappresenta un Punto nello spazio bidimensionale con
 * possibilità di spostarsi e memoria della distanza percorsa.
 *
 * @author emanuele
 * @version 0.0 first version
 */
public class PuntoMobile extends Punto {

    // sottoclasse di Punto, quindi ne eredita tutti gli attributi e metodi
    // Attributi non static, cioè relativi ai singoli oggetti / istanze
    // Information hiding; attributi private
    /**
     * total travelled distance
     */
    private double distance;

    // default constructor
    /**
     * Costruisce ed inizializza, cioè istanzia, un punto con attributi di
     * default.
     *
     * Richiama il costruttore completo
     */
    public PuntoMobile() {
        // this(...) dev'essere la prima istruzione
        // debug("-> Punto()");
        this(X_DEFAULT, Y_DEFAULT);
        debug("PuntoMobile() ->");
    }

    // complete constructor
    /**
     * Costruisce ed inizializza, cioè istanzia, un punto con attributi ricevuti
     * come parametri.
     *
     * Usa i metodi setter per effettuare i controlli di validità
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public PuntoMobile(double x, double y) {
        // super(...) dev'essere la prima istruzione
        // debug("-> PuntoMobile(" + x + "," + y + ")");
        super(x, y);
        distance = 0.0;
        debug("PuntoMobile(" + x + "," + y + ") ->");
    }

    // Information hiding; getters
    /**
     * Get the value of the distance travelled by this Punto
     *
     * @return the value of the distance travelled
     */
    public double getDistance() {
        return distance;
    }

    // new methods
    /**
     * Trasla il punto delle quantità specificate
     *
     * Usa i setter protetti della classe base
     *
     * @param dx traslazione lungo l'asse x
     * @param dy traslazione lungo l'asse y
     */
    public void move(double dx, double dy) {
        debug("-> PuntoMobile.move(" + dx + "," + dy + ")");
        setX(getX() + dx);
        setY(getY() + dy);
        distance += Math.sqrt(dx * dx + dy * dy);
        debug("PuntoMobile.move(" + dx + "," + dy + ") ->");
    }

    /**
     * Trasla il punto a coordinate non valide, generando un'eccezione
     * 
     * @throws IllegalArgumentException
     */
    public void moveToBlackHole() {
        debug("-> PuntoMobile.moveToBlackHole()");
        setX(Double.NaN);
        setY(Double.NaN);
        debug("PuntoMobile.moveToBlackHole() ->");
    }

    @Override
    public String toString() {
        String result = super.toString();
        return result.substring(0, result.length() - 1) + ", distance=" + distance + '}';
    }

}
