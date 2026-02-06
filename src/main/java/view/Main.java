package view;
import java.io.IOException;

import ducky.Ducky;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Ducky using FXML.
 */
public class Main extends Application {

    private Ducky ducky = new Ducky();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDucky(ducky);  // inject the Duke instance
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}