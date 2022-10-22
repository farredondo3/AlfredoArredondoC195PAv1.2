package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Code for the main page containing options for users to select
 */
public class MainScreenController  {

    @FXML private Button appointmentsBtn;
    @FXML private Button customersBtn;
    @FXML private Button reportssBtn;
    @FXML private Button exitBtn;

    /**
     * Appointment button that takes user to the main appointment page
     * @param event event
     * @throws IOException IO exception
     */
    @FXML
    void mainMenuBtn(ActionEvent event) throws IOException
    {
        Parent appointmentMenu = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
        Scene scene = new Scene(appointmentMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.centerOnScreen();
        window.show();
    }

    /**
     * Customer button that connects user to the customer page
     * @param event event
     * @throws IOException IO exception
     */
    @FXML
    void customerBtn(ActionEvent event) throws IOException
    {
        Parent customerMenu = FXMLLoader.load(getClass().getResource("/Views/Customer.fxml"));
        Scene scene = new Scene(customerMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Report button that takes user to the reports page
     * @param event Event
     * @throws IOException Io exception
     */
    @FXML
    void reportsBtn(ActionEvent event) throws IOException
    {
        Parent reportMenu = FXMLLoader.load(getClass().getResource("/Views/Reports.fxml"));
        Scene scene = new Scene(reportMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Exit button, when pressed exits the program.
     * @param ExitButton exit button
     */
    public void exitBtn(ActionEvent ExitButton)
    {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }
}