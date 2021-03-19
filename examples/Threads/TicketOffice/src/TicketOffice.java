import java.time.LocalDateTime;

public class TicketOffice {

    public static final int COUNTERS = 5;
    public static final int TICKETS = 100;

    private int customers;
    private int issuedTickets;

    public TicketOffice() {
    }

    public int getCustomers() {
        return customers;
    }

    public int getAvailableTickets() {
        return TICKETS - issuedTickets;
    }

    public Ticket getTicket() {
        enter(); // try to...
        // if successful, get the ticket and leave
        return buyTicketAndLeave();
    }

    private synchronized void enter() {
        // while there's a reason to wait
        while (isFull() && !isSoldOut()) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // no reason to wait, none to really enter ..,
        if (isSoldOut()) {
            throw new UnsupportedOperationException("Sold out !!!");
        }
        // you lucky !!!
        customers++;
    }

    private boolean isFull() {
        return customers == COUNTERS;
    }

    private boolean isSoldOut() {
        return getAvailableTickets() <= customers;
    }

    private synchronized Ticket buyTicketAndLeave() {
        Ticket result = new Ticket();
        issuedTickets++;
        customers--;
        notify();
        return result;
    }

    public static class Ticket {

        private static long nextNumber;
        private final long number;
        private final LocalDateTime issued;

        public Ticket() {
            number = nextNumber++;
            issued = LocalDateTime.now();
        }

        public long getNumber() {
            return number;
        }

        public LocalDateTime getIssued() {
            return issued;
        }

        @Override
        public String toString() {
            return "Ticket #" + number + " issued " + issued;
        }

    }

}
