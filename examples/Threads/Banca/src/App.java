public class App {
    public static void main(String[] args){
        ContoBancario conto = new ContoBancario(1000);
        Intestatario principale = new Intestatario("Aldo", conto);
        Intestatario secondario = new Intestatario("Giovanni", conto);
        Intestatario terziario = new Intestatario("Giacomo", conto);
        conto.addIntestatario(principale);
        conto.addIntestatario(secondario);
        principale.start();
        secondario.start();
        terziario.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        principale.kill();
        secondario.kill();
        terziario.kill();
        System.out.println(conto);
        
    }
}
