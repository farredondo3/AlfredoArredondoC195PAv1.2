package Controllers;

import Utilities.*;
import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;

/**
 * Class which adds a new appointment to the appointment scheduler
 */
public class AddAppointmentController {
    @FXML private ComboBox<User> addAppointmentUserID;
    @FXML private ComboBox<Customer> addAppointmentCustomerID;
    @FXML private TextField addAppointmentInfo;
    @FXML private DatePicker addAppointmentEndDate;
    @FXML private ComboBox<LocalTime> addAppointmentEndTime;
    @FXML private TextField addAppointmentLocation;
    @FXML private DatePicker addAppointmentStartDate;
    @FXML private ComboBox<LocalTime> addAppointmentStartTime;
    @FXML private TextField addAppointmentTitle;
    @FXML private ComboBox<Contact> addAppointmentContact;
    @FXML private TextField addAppointmentType;

    /**
     * associated with the button save on addAppointment class. All necessary code to help the button function properly.
     * @throws IOException IO exception
     * @param event saves appointment
     */
    @FXML
    void addAppointmentSave(ActionEvent event) throws IOException {
        try {

            Contact contact = addAppointmentContact.getValue();
            User user = addAppointmentUserID.getValue();
            Customer customer = addAppointmentCustomerID.getValue();

            if (!addAppointmentTitle.getText().isEmpty() && !addAppointmentInfo.getText().isEmpty() && !addAppointmentLocation.getText().isEmpty() && !addAppointmentType.getText().isEmpty() && addAppointmentStartDate.getValue() != null && addAppointmentEndDate.getValue() != null && addAppointmentStartTime.getValue() != null && addAppointmentEndTime.getValue() != null && contact != null  && user != null && customer != null)
            {
                int contactID = addAppointmentContact.getValue().getContactID();
                int userID = addAppointmentUserID.getValue().getUserID();
                int customerID = addAppointmentCustomerID.getValue().getCustomerID();

                LocalDate localDateEnd = addAppointmentEndDate.getValue();
                LocalDate localDateStart = addAppointmentStartDate.getValue();
                LocalTime localTimeStart = addAppointmentStartTime.getValue();
                LocalTime localTimeEnd = addAppointmentEndTime.getValue();

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, localTimeEnd);

                if(!AddAppointmentController.checkForHours(dateTimeStart,dateTimeEnd))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment is outside business hours");
                    alert.showAndWait();
                    return;
                }

                if(AddAppointmentController.appointmentOverlapCheck( dateTimeStart, dateTimeEnd, customerID, -1))
                {
                    return;
                }

                if( localTimeStart.isAfter(localTimeEnd) || localTimeStart.equals(localTimeEnd))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Start time must be before end time");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                if( dateTimeStart.isAfter(dateTimeEnd))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Start date must be before end date");
                    Optional<ButtonType> error = alert.showAndWait();
                    return;
                }

                AppointmentAccess.addAppointment(addAppointmentTitle.getText(), addAppointmentInfo.getText(), addAppointmentLocation.getText(), addAppointmentType.getText(), dateTimeStart,  dateTimeEnd, customerID, userID, contactID);

            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Missing information. Make sure all required information is entered before saving!");
                Optional<ButtonType> error = alert.showAndWait();
                return;
            }

            Parent root = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
            Scene scene = new Scene(root);
            Stage returnToMainScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
            returnToMainScreen.setScene(scene);
            returnToMainScreen.show();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Cancel button that is associated with the addAppointment. Cancels the addAppointment and returns to the main Appointment page.
     *
     * @param event cancels button
     * @throws IOException IO exception
     */
    @FXML
    public void cancelButton(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Views/Appointments.fxml"));
        Scene scene = new Scene(root);
        Stage cnclBtn = (Stage)((Node)event.getSource()).getScene().getWindow();
        cnclBtn.setScene(scene);
        cnclBtn.show();
    }

    /**
     * Checks if appointment is overlapped with another appointment
     * @param newStart new appointment start time
     * @param newEnd new appointment end time
     * @param customerID customer ID
     * @param appointmentId appointment ID
     * @return boolean
     * @throws SQLException exception for sql
     */
    public static boolean appointmentOverlapCheck(LocalDateTime newStart, LocalDateTime newEnd, int customerID, int appointmentId) throws SQLException {
        ObservableList<Appointment> checksEveryApmt = AppointmentAccess.getAllAppointments();

        for (Appointment appointment: checksEveryApmt)
        {
            LocalDateTime oldStart = appointment.getStart();
            LocalDateTime oldEnd = appointment.getEnd();

            if(customerID != appointment.getCustomerID() || appointment.getAppointmentID() == appointmentId)
            {
                continue;
            }

            if ((newStart.isBefore(oldStart)) && (newEnd.isAfter(oldEnd)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 1");
                Optional<ButtonType> error = alert.showAndWait();
                System.out.println("Appointment overlaps with existing appointment.");
                return true;
            }

            if ((newStart.isAfter(oldStart)) && (newEnd.isBefore(oldEnd)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 2");
                Optional<ButtonType> error = alert.showAndWait();
                System.out.println("Appointment overlaps with existing appointment");
                return true;
            }

            if ((newStart.isEqual(oldStart)) || (newEnd.isEqual(oldEnd)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment equals");
                Optional<ButtonType> error = alert.showAndWait();
                System.out.println("Appointment overlaps with existing appointment");
                return true;
            }

            if ((newStart.isBefore(oldStart)) && (newEnd.isAfter(oldStart)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 4");
                Optional<ButtonType> error = alert.showAndWait();
                System.out.println("Appointment overlaps with existing appointment");
                return true;
            }

            if ((newStart.isBefore(oldEnd)) && (newEnd.isAfter(oldEnd)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 5");
                Optional<ButtonType> error = alert.showAndWait();
                System.out.println("Appointment overlaps with existing appointment");
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if appointment being added is within businesss hours
     * @param dateTimeStart start time
     * @param dateTimeEnd end time
     * @return boolean
     */
    public static boolean checkForHours(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd)
    {
        ZonedDateTime estBusinessStart = ZonedDateTime.of(dateTimeStart.toLocalDate(), LocalTime.of(8, 0, 0), ZoneId.of("America/New_York"));
        ZonedDateTime estBusinessEnd = ZonedDateTime.of(dateTimeStart.toLocalDate(), LocalTime.of(22, 0, 0), ZoneId.of("America/New_York"));

        ZonedDateTime localStart = estBusinessStart.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime localEnd = estBusinessEnd.withZoneSameInstant(ZoneId.systemDefault());

        if(dateTimeStart.isBefore(localStart.toLocalDateTime()) || dateTimeEnd.isAfter(localEnd.toLocalDateTime()))
        {
            return false;
        }
        return true;
    }

    /**
     * Initializes the fxml page and loads all the necessary information for user to enter details about the appointment
     * @throws SQLException exception
     */
    @FXML
    public void initialize() throws SQLException {

        ObservableList<Contact> contactsObservableList = ContactAccess.getAllContacts();
        ObservableList<Customer> customersObservableList = CustomerAccess.getAllCustomers();
        ObservableList<User> usersObservableList = UserAccess.getAllUsers();
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        addAppointmentCustomerID.setItems(customersObservableList);
        addAppointmentUserID.setItems(usersObservableList);
        addAppointmentContact.setItems(contactsObservableList);

        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

        if (!firstAppointment.equals(0) || !lastAppointment.equals(0))
        {
            while (firstAppointment.isBefore(lastAppointment))
            {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }
        addAppointmentStartTime.setItems(TimeHelp.getStartTimes());
        addAppointmentEndTime.setItems(TimeHelp.getEndTimes());
    }
}

