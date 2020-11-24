package posizioni;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

public class Posizione {

    private final double latitudine;
    private final double longitudine;
    private final String indirizzo;

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Posizione(double latitudine, double longitudine, String indirizzo) {
        this.latitudine = check(latitudine, "latitudine");
        this.longitudine = check(longitudine, "longitudine");
        this.indirizzo = Objects.requireNonNull(indirizzo, "indirizzo non specificato");
    }

    private static double check(double value, String name) {
        if (Double.isFinite(value)) {
            return value;
        }
        throw new IllegalArgumentException(name + " non valido");
    }

    @Override
    public String toString() {
        return indirizzo + " (" + latitudine + ", " + longitudine + ")";
    }

    public static Collection<Posizione> readFromFile(String filename) {
        Collection<Posizione> result = new ArrayList<>();
        Scanner input = null;
        try {
            input = new Scanner(new File(filename));
            // operazioni sul file
            // IOException --> catch
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(":" + line + ":");
                if (line.startsWith("#")) {
                    // commento
                } else {
                    String[] parti = line.split(",", 3);
                    if (parti.length == 3) {
                        try {
                            double latitudine = Double.parseDouble(parti[0]);
                            double longitudine = Double.parseDouble(parti[1]);
                            String indirizzo = parti[2];
                            Posizione posizione = new Posizione(latitudine, longitudine, indirizzo);
                            result.add(posizione);
                            System.out.println("Aggiunto " + posizione);
                        } catch (RuntimeException ex) {
                            System.err.println(ex);
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            // System.err.println(ex);
        } finally {
            // chiusura del file
            if (input != null) {
                input.close();
            }
        }
        return result;
    }

    public static Collection<Posizione> tryReadFromFile(String filename) throws FileNotFoundException {
        Collection<Posizione> result = new ArrayList<>();
        Scanner input = new Scanner(new File(filename));
        // operazioni sul file
        // IOException --> catch
        while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println(":" + line + ":");
            if (line.startsWith("#")) {
                // commento
            } else {
                String[] parti = line.split(",", 3);
                if (parti.length == 3) {
                    try {
                        double latitudine = Double.parseDouble(parti[0]);
                        double longitudine = Double.parseDouble(parti[1]);
                        String indirizzo = parti[2];
                        Posizione posizione = new Posizione(latitudine, longitudine, indirizzo);
                        result.add(posizione);
                        System.out.println("Aggiunto " + posizione);
                    } catch (RuntimeException e) {
                    }
                }
            }
        }
        // chiusura del file
        input.close();
        return result;
    }

}
