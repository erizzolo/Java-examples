import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Customer extends Thread {

    private static final Random rnd = new Random();
    private final Collection<TicketOffice.Ticket> tickets;
    private final TicketOffice office;
    private Duration timeWaited = Duration.ZERO;

    public Customer(String name, TicketOffice office) {
        super(name);
        this.office = office;
        tickets = new ArrayList<>();
    }

    public TicketOffice getOffice() {
        return office;
    }

    public Duration getTimeWaited() {
        return timeWaited;
    }

    public Collection<TicketOffice.Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    @Override
    public void run() {
        while (rnd.nextInt(5) != 0) {
            LocalDateTime started = LocalDateTime.now();
            try {
                TicketOffice.Ticket ticket = office.getTicket();
                System.out.println(this + " got " + ticket);
                tickets.add(ticket);
            } catch (Exception e) {
                System.out.println(this + " is not lucky: " + e.getMessage());
            }
            timeWaited = Duration.between(started, LocalDateTime.now()).plus(timeWaited);
        }
    }

    @Override
    public String toString() {
        return getName();
    }

}
