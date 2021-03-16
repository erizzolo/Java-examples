import java.util.Random;

public class Driver extends Thread {

    private Random random = new Random();
    public final Car car;
    public final Parking parking;

    public Driver(Car car, Parking parking) {
        super("Driver di " + car + " using " + parking);
        this.car = car;
        this.parking = parking;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            ParkingPlace place = parking.park();
            place.park(car);
            try {
                sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parking.leave(place);
        }
    }

    @Override
    public String toString() {
        return "Driver [car=" + car + ", parking=" + parking + "]";
    }

}
