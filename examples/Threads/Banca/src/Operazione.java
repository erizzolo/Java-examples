import java.time.LocalDateTime;

public class Operazione {

    private final TipoOperazione tipo;
    private final int importo;
    private final Intestatario ordinante;
    private final LocalDateTime istante;

    public Operazione(TipoOperazione tipo, int importo) {
        this(tipo, importo, null);
    }

    public TipoOperazione getTipo() {
        return tipo;
    }

    public int getImporto() {
        return importo;
    }

    public Intestatario getOrdinante() {
        return ordinante;
    }

    public LocalDateTime getIstante() {
        return istante;
    }

    public Operazione(TipoOperazione tipo, int importo, Intestatario ordinante) {
        this.tipo = tipo;
        this.importo = importo;
        this.ordinante = ordinante;
        istante = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return tipo + " di " + importo + " per conto di " + ordinante + " @" + istante;
    }

}
