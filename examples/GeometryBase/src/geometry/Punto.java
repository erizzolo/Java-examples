package geometry;

/**
 * La classe Punto rappresenta un punto (immutabile) nello spazio
 * bidimensionale.
 *
 * @author emanuele
 * @version 0.0 first version
 */
public class Punto {

    // per attivare/disattivare output di debug
    public static boolean DEBUG = false;

    public static void debug(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    // Attributi static, cioè relativi alla categoria dei Punti 
    // e final, cioè non modificabili dopo l'assegnazione
    /**
     * Default x coordinate
     */
    public final static double X_DEFAULT = 1.0;

    /**
     * Default y coordinate
     */
    public final static double Y_DEFAULT = 1.0;

    static {
        debug("Building ORIGIN...");
    }
    /**
     * The origin of the coordinate system
     */
    public static final Punto ORIGIN = new Punto(0.0, 0.0);

    // Attributi non static, cioè relativi ai singoli oggetti / istanze
    // Information hiding; attributi private
    /**
     * x coordinate
     */
    private double x;
    /**
     * y coordinate
     */
    private double y;

    // default constructor
    /**
     * Costruisce ed inizializza, cioè istanzia, un punto con attributi di
     * default.
     *
     * Richiama il costruttore completo
     */
    public Punto() {
        // this(...) dev'essere la prima istruzione
        // debug("-> Punto()");
        this(X_DEFAULT, Y_DEFAULT);
        debug("Punto() ->");
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
    public Punto(double x, double y) {
        debug("-> Punto(" + x + "," + y + ")");
        setX(x);
        setY(y);
        debug("Punto(" + x + "," + y + ") ->");
    }

    // Information hiding; getters
    /**
     * Get the value of the x coordinate of this Punto
     *
     * @return the value of the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Get the value of the y coordinate of this Punto
     *
     * @return the value of the y coordinate
     */
    public double getY() {
        return y;
    }

    // Information hiding; setters usabili ma non ridefinibili dalle sottoclassi
    /**
     * Check and set the value of the x coordinate
     *
     * @param x new value of the x coordinate
     */
    protected final void setX(double x) {
        debug("-> setX(" + x + ")");
        if (x == Double.NaN || x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException("x coordinate cannot be NaN, +INf or -INF.");
        }
        this.x = x;
        debug("setX(" + x + ") ->");
    }

    /**
     * Check and set the value of the y coordinate
     *
     * @param y new value of the y coordinate
     */
    protected final void setY(double y) {
        debug("-> setY(" + y + ")");
        if (y == Double.NaN || y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException("y coordinate cannot be NaN, +INf or -INF.");
        }
        this.y = y;
        debug("setY(" + y + ") ->");
    }

    // Altri getter utili
    /**
     * Get the distance from another Punto
     *
     * @param other the other Punto
     * @return Nan if other is null, otherwise the distance from other and this
     * Punto
     */
    public double getDistanceFrom(Punto other) {
        if (other == null) {
            return Double.NaN;
        }
        double dx = getX() - other.getX(), dy = getY() - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Fare sempre l'override di toString per comodità in debug, errori e altro
    @Override
    public String toString() {
        return super.toString() + "{" + "x=" + x + ", y=" + y + '}';
    }

}
