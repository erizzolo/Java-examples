import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Car {
    
    private static Set<String> plates = new HashSet<>();

    private String plate;

    public Car(String plate) {
        this.plate = Objects.requireNonNull(plate, "null plate");
        if (plates.contains(plate)) {
            throw new IllegalArgumentException("duplicate plate number " + plate);
        }
        plates.add(plate);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((plate == null) ? 0 : plate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (plate == null) {
            if (other.plate != null)
                return false;
        } else if (!plate.equals(other.plate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Car [plate=" + plate + "]";
    }

    
}
