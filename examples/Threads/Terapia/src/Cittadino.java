import java.util.Objects;

public class Cittadino extends Thread {

    private Citta residenza;

    public Cittadino(String name, Citta residenza) {
        super(name);
        this.residenza = Objects.requireNonNull(residenza, "residenza cannot be null");
    }

    public Citta getResidenza() {
        return residenza;
    }

    @Override
    public void run() {
        for (int i = 0; i < Terapia.NUM_DOSI; i++) {
            residenza.centroDiCura.getDose(i);
            try {
                sleep(Terapia.MIN_RITARDO);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public String toString() {
        return getName() + " di " + getResidenza();
    }

}
