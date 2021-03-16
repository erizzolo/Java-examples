import java.util.Objects;

public class ParkingPlace {

    private Car vehicle;

    public boolean isFree() {
        return vehicle == null;
    }

    public Car getVehicle() {
        return vehicle;
    }

    public boolean park(Car car) {
        car = Objects.requireNonNull(car, "null car");
        boolean result = isFree();
        if (result) {
            vehicle = car;
        }
        return result;
    }

    @Override
    public String toString() {
        return "ParkingPlace " + (isFree() ? "FREE" : vehicle);
    }

}
