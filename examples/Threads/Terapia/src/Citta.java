public enum Citta {

    PONTEVILI(40), MARZABOTTO(25);

    public final CentroDiCura centroDiCura;
    public final int citizens;

    private Citta(int citizens) {
        this.citizens = citizens;
        centroDiCura = new CentroDiCura(this);
    }

    public CentroDiCura getCentroDiCura() {
        return centroDiCura;
    }

}
