package basefx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author emanuele
 */
public class BaseFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("In handle:");
                showThreads();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        showThreads();
        launch(args);
    }

    public static void showThreads() {
        System.out.println("Thread.currentThread(): " + Thread.currentThread());
        Thread[] ta = new Thread[Thread.activeCount() + 3];
        int len = Thread.enumerate(ta);
        System.out.println("Active Threads count is " + len);
        for (int i = 0; i < len; i++) {
            System.out.println("Thread " + i + ": " + ta[i]);
        }
    }
}
