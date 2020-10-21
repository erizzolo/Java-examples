import java.lang.reflect.Constructor;

/**
 * Classe derivata che rappresenta un punto mobile nello spazio bidimensionale,
 * con memoria della distanza percorsa e della distanza da "casa".
 *
 * @author emanuele
 * @version 0.0 first version
 */

public class PuntoMobile extends Punto {

    // attributi aggiuntivi
    /**
     * x and y distance from "home": actual displacement from original position
     */
    private double dx, dy;
    /**
     * Total distance travelled
     */
    private double travelledDistance;
    /**
     * Maximum total distance travelled by any PuntoMobile
     */
    private static double maxTravelledDistance = 0.0;

    // Poiché la classe base Punto non ha un costruttore di default Punto(),
    // se non definiamo un costruttore abbiamo un ERRORE:
    // Implicit super constructor Punto() is undefined for default constructor.
    // Must define an explicit constructor Java(134217868)
    // cioè super() non funziona perché il costruttore Punto() non c'è !!!
    // quindi ne definiamo (almeno) uno (meglio se tutti quelli simili ai
    // super(...))

    // Per ogni costruttore, se non inseriamo un super(...) all'inizio abbiamo un
    // ERRORE:
    // Implicit super constructor Punto() is undefined.
    // Must explicitly invoke another constructor Java(134217871)
    // quindi nei costruttori inseriamo super(...) esplicito

    // complete constructor
    /**
     * Istanzia un punto mobile con attributi ricevuti come parametri.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws IllegalArgumentException se le coordinate non sono finite
     */
    public PuntoMobile(double x, double y) {
        super(x, y);
        debug("-> PuntoMobile(" + x + ", " + y + ") (after super(x,y)!!!)");
        // attributes initialized to 0
    }

    // copy constructor
    /**
     * Istanzia un punto mobile identico a quello originale (un clone).
     *
     * @param original the original PuntoMobile
     * @throws NullPointerException se original è null
     */
    public PuntoMobile(PuntoMobile original) {
        super(original); // original is-A Punto !!!
        // copy other attributes
        travelledDistance = original.travelledDistance;
        dx = original.dx;
        dy = original.dy;
    }

    // metodi aggiuntivi
    /**
     * Sposta il punto delle quantità specificate
     * 
     * @param dx x axis movement
     * @param dy y axis movement
     */
    public void move(double dx, double dy) {
        this.dx += dx;
        this.dy += dy;
        travelledDistance += Math.hypot(dx, dy);
        if (maxTravelledDistance < travelledDistance)
            maxTravelledDistance = travelledDistance;
    }

    // getters per attributi aggiuntivi
    public double getTravelledDistance() {
        return travelledDistance;
    }

    public double getHomeDistance() {
        return Math.hypot(dx, dy);
    }

    public static double getMaxTravelledDistance() {
        return maxTravelledDistance;
    }

    // override per i getter della classe base
    @Override
    public double getX() {
        return super.getX() + dx;
    }

    @Override
    public double getY() {
        return super.getY() + dy;
    }

    // Fare sempre l'override di toString per comodità in debug, errori e altro
    @Override
    public String toString() {
        return super.toString().replace(']', ',') + " travelledDistance=" + travelledDistance + "]";
    }

    /**
     * Restituisce un punto con coordinate casuali gaussiane
     */
    public static PuntoMobile getRandom() {
        debug("-> getRandom()");
        Punto result = Punto.getRandom();
        result = new PuntoMobile(result.getX(), result.getY());
        debug("getRandom() ->");
        return (PuntoMobile) result;
    }

    // a simple test of this class
    public static PuntoMobile test() {
        // Basic tests of PuntoMobile
        System.out.println("Test of PuntoMobile");
        // no need of "Punto." before static methods/attributes
        // like getRandom, getMedio, ORIGIN
        // because they are inherited
        PuntoMobile p = new PuntoMobile(1.5, 2.5);
        System.out.println(p);
        PuntoMobile rm = getRandom();
        System.out.println(rm);
        System.out.println(getMedio(p, rm)); // no need of "Punto." because inherited
        System.out.println(p.getDistanceFrom(ORIGIN));
        p.move(-1.5, -2.5);
        System.out.println(p + ": getX() = " + p.getX() + ", getY() = " + p.getY());
        System.out.println(p.getDistanceFrom(ORIGIN));
        return getRandom();
    }

}
