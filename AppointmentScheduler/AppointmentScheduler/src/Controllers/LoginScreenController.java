package Controllers;

import Utilities.AppointmentAccess;
import Utilities.UserAccess;
import Models.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Log in screen of the project
 */
public class LoginScreenController implements Initializable {
    @FXML private Button loginButton;
    @FXML private TextField loginLocation;
    @FXML private TextField loginPassword;
    @FXML private TextField loginScreenUsername;
    @FXML private Label passwordField;
    @FXML private Label usernameField;
    @FXML private Label loginField;
    @FXML private Button cancelBtn;
    @FXML private Label locationText;
    Stage stage;
    ResourceBundle myBundle = ResourceBundle.getBundle("language/login", Locale.getDefault());

    /**
     *  Login button for main screen as well as containing the necessary checks to ensure the page functions properly.
     * @param event event
     * @throws SQLException Sql exception
     * @throws Exception Exception
     **/

    @FXML
    private void loginBtn(ActionEvent event) throws SQLException, Exception {
        try {
            ObservableList<Appointment> getAllAppointments = AppointmentAccess.getAllAppointments();
            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;

            String usernameInput = loginScreenUsername.getText();
            String passwordInput = loginPassword.getText();
            int userId = UserAccess.validUser(usernameInput, passwordInput);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);

            if (userId > 0) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/MainScreen.fxml"));
                Parent root = loader.load();
                stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                outputFile.print("user: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

                for (Appointment appointment: getAllAppointments)
                {
                    startTime = appointment.getStart();
                    if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                        getAppointmentID = appointment.getAppointmentID();
                        displayTime = startTime;
                        appointmentWithin15Min = true;
                    }
                }
                if (appointmentWithin15Min) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + getAppointmentID + " and appointment start time of: " + displayTime);
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("There is an appointment within 15 minutes");
                }

                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("no upcoming appointments");
                }
            } else if (userId < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect");
                alert.show();

                outputFile.print("user: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
            }
            outputFile.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Cancel button which closes the window.
     * @param event event
     */

    public void cancelBtn(ActionEvent event)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the page by setting it up to function as intended.
     * @param url, url
     */

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            ZoneId zone = ZoneId.systemDefault();

            loginLocation.setText(String.valueOf(zone));
            loginField.setText(myBundle.getString("login"));
            usernameField.setText(myBundle.getString("username"));
            passwordField.setText(myBundle.getString("password"));
            loginButton.setText(myBundle.getString("login"));
            cancelBtn.setText(myBundle.getString("Exit"));
            locationText.setText(myBundle.getString("Location"));

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
            e.printStackTrace();
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
