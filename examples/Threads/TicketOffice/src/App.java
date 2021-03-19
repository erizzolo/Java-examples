public class App {
    public static void main(String[] args) throws Exception {
        TicketOffice office = new TicketOffice();
        Customer[] customers = new Customer[TicketOffice.TICKETS];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer("C" + i, office);
            customers[i].start();
        }
        while (Thread.activeCount() > 1) {
            Thread.sleep(100);
        }
        long totalTickets = 0;
        for (int i = 0; i < customers.length; i++) {
            totalTickets += customers[i].getTickets().size();
        }
        if (totalTickets < TicketOffice.TICKETS) {
            System.out.println("Still " + office.getAvailableTickets() + " tickets available!");
        } else {
            System.out.println(totalTickets + "/" + TicketOffice.TICKETS + " sold!");
        }
    }
}
