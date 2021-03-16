public class App {

    public static void main(String[] args) throws Exception {
        Car peugeot = new Car("DX955NG");
        Car renault = new Car("BZ970YC");
        Car camper = new Car("EW631NR");
        Car scooter = new Car("DJ45240");
        Car dream = new Car("AMARTIN");

        Parking parking = new Parking(3);

        Driver me = new Driver(peugeot, parking);
        Driver you = new Driver(camper, parking);
        Driver somebody = new Driver(scooter, parking);
        Driver other = new Driver(dream, parking);
        Driver someone = new Driver(renault, parking);

        me.start();
        you.start();
        somebody.start();
        other.start();
        someone.start();

        for (int i = 0; i < 10; i++) {
            Car car = new Car("PLATE " + i);
            Driver driver = new Driver(car, parking);
            driver.start();
        }

        while (Thread.activeCount() > 1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
