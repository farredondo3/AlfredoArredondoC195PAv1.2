package Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

/** Creates an application for inventory management and adds sample data. */
public class Main extends Application {

    /**
     * Abstract method to start the application and loads the log in screen.
     * @param stage stage
     * @throws IOException IO exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../Views/LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 337, 307);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method that begins connection to the database as well as starting the scheduler.
     *
     * @param args args
     * @throws Exception exception
     */
    public static void main(String[] args) throws Exception
    {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}