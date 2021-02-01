import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class Lettore extends Thread {
    private final File file;
    private final BufferPool pool;
    private long totRead = 0;
    private Duration elapsed = Duration.ZERO;

    public Lettore(File file, BufferPool pool) {
        super("Lettore di " + file.getName());
        this.file = file;
        this.pool = pool;
    }

    @Override
    public void run() {
        Temporal started = LocalDateTime.now();
        try (FileInputStream fis = new FileInputStream(file)) {
            while (fis.available() > 0) {
                Buffer buffer = pool.getEmpty();
                int read = fis.read(buffer.getData());
                buffer.setSize(read);
                totRead += read;
                pool.putFull(buffer);
            }
            // last empty chunk
            Buffer buffer = pool.getEmpty();
            buffer.setSize(0);
            pool.putFull(buffer);
        } catch (Exception e) {
            System.out.println(e);
        }
        elapsed = Duration.between(started, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Lettore [file=" + file + ", pool=" + pool + ", read " + totRead + "B in " + elapsed + "]";
    }

}
