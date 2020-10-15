package cronometro;

public class Cronometro {

    private boolean running;

    private long reading;

    public Cronometro() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public double getReading() {
        return (running ? System.nanoTime() - reading : reading) * 1e-9;
    }

    public void start() {
        if (!running) {
            running = true;
            reading = System.nanoTime();
        }
    }

    public void stop() {
        if (running) {
            running = false;
            reading = System.nanoTime() - reading;
        }
    }

    public void reset() {
        if (!running) {
            reading = 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "{" + (running ? "" : "not ") + "running, reading " + getReading() + '}';
    }

}
