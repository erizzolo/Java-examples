
import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class Scrittore extends Thread {
    private final File file;
    private final BufferPool pool;
    private long totWritten = 0;
    private Duration elapsed = Duration.ZERO;

    public Scrittore(File file, BufferPool pool) {
        super("Scrittore di " + file.getName());
        this.file = file;
        this.pool = pool;
    }

    @Override
    public void run() {
        Temporal started = LocalDateTime.now();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            Buffer buffer;
            do {
                buffer = pool.getFull();
                fos.write(buffer.getData(), 0, buffer.getSize());
                totWritten += buffer.getSize();
                pool.putEmpty(buffer);
            } while (buffer.getSize() != 0);
        } catch (Exception e) {
            System.out.println(e);
        }
        elapsed = Duration.between(started, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Scrittore [file=" + file + ", pool=" + pool + ", written " + totWritten + "B in " + elapsed + "]";
    }

}
