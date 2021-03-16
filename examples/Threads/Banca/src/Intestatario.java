import java.util.Random;

public class Intestatario extends Thread {

    private final ContoBancario conto;
    private final String nome;
    private boolean attivo;

    private final Random rnd = new Random();

    public Intestatario(String nome, ContoBancario conto) {
        this.nome = nome;
        this.conto = conto;
        attivo = true;
    }

    @Override
    public void run() {
        while (attivo) {
            if (rnd.nextDouble() < 0.5) {
                conto.deposita(rnd.nextInt(1000));
            } else {
                conto.preleva(rnd.nextInt(1000));
            }
            try {
                sleep(rnd.nextInt(100));
            } catch (InterruptedException e) {
            }
        }
    }

    public ContoBancario getConto() {
        return conto;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void kill() {
        attivo = false;
    }

    @Override
    public String toString() {
        return "Intestatario [nome=" + nome + "]";
    }

}
