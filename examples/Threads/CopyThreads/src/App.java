import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static boolean copyFile(File source, File destinationDirectory) {
        if (source.canRead()) {
            if (destinationDirectory.canWrite()) {
                File dest = new File(destinationDirectory, source.getName());
                if (dest.exists()) {
                    Alert confirm = new Alert(AlertType.WARNING,
                            "File " + dest.getAbsolutePath() + " already exists!\nOverwrite?", ButtonType.YES,
                            ButtonType.NO);
                    Optional<ButtonType> confirmed = confirm.showAndWait();
                    if (!confirmed.isPresent() || confirmed.get() == ButtonType.NO) {
                        System.out.println("Canceled by user");
                        return false;
                    }
                }
                doCopy(source, dest);
                return true;
            } else {
                System.out.println("Can't write destination directory " + destinationDirectory.getName());
                return false;
            }
        } else {
            System.out.println("Can't read source file " + source.getName());
            return false;
        }
    }

    private static void doCopy(File source, File dest) {
        BufferPool pool = new BufferPool(1 << 12, 4);
        Lettore lettore = new Lettore(source, pool);
        lettore.start(); // lettore goes first !!!
        Scrittore scrittore = new Scrittore(dest, pool);
        scrittore.start();
        try {
            scrittore.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(lettore);
        System.out.println(scrittore);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FileChooser source = new FileChooser();
        source.setTitle("Scegli il file da copiare");
        File sourceFile = source.showOpenDialog(null);
        if (sourceFile != null) {
            DirectoryChooser destination = new DirectoryChooser();
            destination.setTitle("Scegli la directory di destinazione");
            File destinationDirectory = destination.showDialog(null);
            if (destinationDirectory != null) {
                LocalDateTime start = LocalDateTime.now();
                boolean copied = copyFile(sourceFile, destinationDirectory);
                Duration elapsed = Duration.between(start, LocalDateTime.now());
                System.out.println("Copia " + (copied ? "" : "non ") + "effettuata in " + elapsed);
            }
        }
        Platform.exit();
    }
}
