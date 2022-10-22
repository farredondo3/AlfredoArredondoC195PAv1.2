package Controllers;

import Utilities.*;
import Main.DBConnection;
import Main.Time;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Main appointment class containing the information of appoints and the necessary details to identify them
 */
public class AppointmentsMain {

    @FXML private TableColumn<?, ?> appointmentEnd;
    @FXML private TableColumn<?, ?> appointmentType;
    @FXML private TableColumn<?, ?> tableContactID;
    @FXML private TableColumn<?, ?> tableUserID;
    @FXML private TableColumn<?, ?> appointmentCustomer;
    @FXML private TableColumn<?, ?> appointmentDescription;
    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private TableColumn<?, ?> appointmentTitle;
    @FXML private TableColumn<?, ?> appointmentLocation;
    @FXML private TableColumn<?, ?> appointmentStart;
    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TextField updateTitle;
    @FXML private TextField updateDescription;
    @FXML private TextField updateType;
    @FXML private TextField updateLocation;
    @FXML private TextField updateAppointmentID;
    @FXML private DatePicker updateAppointmentStartDate;
    @FXML private DatePicker updateAppointmentEndDate;
    @FXML private ComboBox<LocalTime> updateStartTime;
    @FXML private ComboBox<LocalTime> updateEndTime;
    @FXML private ComboBox<Contact> updateContact;
    @FXML private ComboBox<Customer> updateCustomer;
    @FXML private ComboBox<User> updateUser;
    @FXML private Button saveAppointment;

    /**
     * Changes to addAppointment page
     * @param event switches to addAppointment
     * @throws IOException IO exception
     */
    @FXML
    void addAppointmentBtn(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../Views/AddAppointments.fxml"));
        Scene scene = new Scene(root);
        Stage goToAddAptmt = (Stage) ((Node) event.getSource()).getScene().getWindow();
        goToAddAptmt.setScene(scene);
        goToAddAptmt.show();
    }

    /**
     * Back button that goes back to the main menu of options
     * @param event goes back to main screen
     * @throws IOException IO exception
     */
    @FXML
    void backbtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage toMainMenu = (Stage)((Node)event.getSource()).getScene().getWindow();
        toMainMenu.setScene(scene);
        toMainMenu.show();
    }

    /**
     * Delete button that deletes selected appointments
     * @param event deletes appointment
     * @throws Exception throws exception
     */
    @FXML
    void deleteBtn(ActionEvent event) throws Exception {
        try {
            Connection connection = DBConnection.startConnection();

            if (appointmentsTable.getSelectionModel().getSelectedItem() != null)
            {
                int delAptmtID = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID();
                String delType = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentType();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment with appointment id: " + delAptmtID + " and appointment type " + delType);
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    AppointmentAccess.deleteAppointment(delAptmtID, connection);

                    ObservableList<Appointment> allAppointmentsList = AppointmentAccess.getAllAppointments();
                    appointmentsTable.setItems(allAppointmentsList);
                }
            }else{Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select an appointment ");
                Optional<ButtonType> error = alert.showAndWait();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loads appropriate amount of appointments that have been made.
     */
    @FXML
    void loadAppointment()
    {
        try {
            DBConnection.startConnection();
            Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
            if (selectedAppointment != null) {
                for (Contact contact: updateContact.getItems()) {
                    if (selectedAppointment.getContactID() == contact.getContactID()) {
                        updateContact.setValue(contact);
                        break;
                    }
                }
                updateAppointmentID.setText(String.valueOf(selectedAppointment.getAppointmentID()));
                updateTitle.setText(selectedAppointment.getAppointmentTitle());
                updateDescription.setText(selectedAppointment.getAppointmentDescription());
                updateLocation.setText(selectedAppointment.getAppointmentLocation());
                updateType.setText(selectedAppointment.getAppointmentType());
                updateAppointmentStartDate.setValue(selectedAppointment.getStart().toLocalDate());
                updateAppointmentEndDate.setValue(selectedAppointment.getEnd().toLocalDate());
                updateStartTime.setValue(selectedAppointment.getStart().toLocalTime());
                updateEndTime.setValue(selectedAppointment.getEnd().toLocalTime());

                for(User u: updateUser.getItems()){
                    if (u.getUserID() == selectedAppointment.getUserID()){
                        updateUser.setValue(u);
                        break;
                    }
                }

                for(Customer c: updateCustomer.getItems()){
                    if (c.getCustomerID() == selectedAppointment.getCustomerID()){
                        updateCustomer.setValue(c);
                        break;
                    }
                }

                ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();
                LocalTime firstAppointment = LocalTime.MIN.plusHours(5);
                LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

                if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
                    while (firstAppointment.isBefore(lastAppointment)) {
                        appointmentTimes.add(firstAppointment);
                        firstAppointment = firstAppointment.plusMinutes(15);
                    }
                }
                updateStartTime.setItems(appointmentTimes);
                updateEndTime.setItems(appointmentTimes);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Save button that takes all of the changes made by updating the appointment with the most recent information.
     * @param event saves appointment
     */
    @FXML
    void saveAppointment(ActionEvent event) {
        try {

            //Connection connection = DBConnection.startConnection();

            if (!updateTitle.getText().isEmpty() && !updateDescription.getText().isEmpty() && !updateLocation.getText().isEmpty() && !updateType.getText().isEmpty() && updateAppointmentStartDate.getValue() != null && updateAppointmentEndDate.getValue() != null && !(updateStartTime.getValue() == null) && !(updateEndTime.getValue() == null) && updateCustomer.getValue() != null)//&& !addAppointmentCustomerID.getText().isEmpty() @end
            {
                ObservableList<Customer> getAllCustomers = CustomerAccess.getAllCustomers();
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<User> getAllUsers = UserAccess.getAllUsers();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointment> getAllAppointments = AppointmentAccess.getAllAppointments();

                getAllCustomers.stream().map(Customer::getCustomerID).forEach(storeCustomerIDs::add);
                getAllUsers.stream().map(User::getUserID).forEach(storeUserIDs::add);

                LocalDate localDateEnd = updateAppointmentEndDate.getValue();
                LocalDate localDateStart = updateAppointmentStartDate.getValue();
                LocalTime localTimeStart = updateStartTime.getValue();
                LocalTime localTimeEnd = updateEndTime.getValue();

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, localTimeEnd);

                if(!AppointmentsMain.checkForHours(dateTimeStart,dateTimeEnd))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment is outside business hours");
                    alert.showAndWait();
                    return;
                }

                int newCustomerID = updateCustomer.getValue().getCustomerID();
                int newContactID = updateContact.getValue().getContactID();
                int newUserID = updateUser.getValue().getUserID();

                int appointmentID = Integer.parseInt(updateAppointmentID.getText());

                if( localTimeStart.isAfter(localTimeEnd)|| localTimeStart.equals(localTimeEnd))
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

                if(AppointmentsMain.appointmentOverlapCheck( dateTimeStart, dateTimeEnd, localDateEnd,localDateStart,newCustomerID, appointmentID))
                {
                    return;
                }

                String startDate = updateAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime startTime = updateStartTime.getValue();

                String endDate = updateAppointmentEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime endTime = updateEndTime.getValue();

                String start = Time.convertTimeDateUTC(startDate + " " + startTime + ":00");
                String end = Time.convertTimeDateUTC(endDate + " " + endTime + ":00");

                AppointmentAccess.updateAppointment(updateTitle.getText(), updateDescription.getText(), updateLocation.getText(), updateType.getText(), start,  end, newCustomerID, newUserID, newContactID, appointmentID);

                ObservableList<Appointment> allAppointmentsList = AppointmentAccess.getAllAppointments();
                appointmentsTable.setItems(allAppointmentsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Radio button that gives all appointments that have ever been made except for updated and deleted ones
     * @param event goes to event
     * @throws SQLException Sql exception
     */
    @FXML
    void appointmentAllSelected(ActionEvent event) throws SQLException {
        try {
            ObservableList<Appointment> allAppointmentsList = AppointmentAccess.getAllAppointments();

            for (Appointment appointment : allAppointmentsList) {
                appointmentsTable.setItems(allAppointmentsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LAMBDA expression checks each appointment to see if it is within the current month
     * Button that filters the appointments to ones that are specific to the current month.
     * @param event filters appointment only in current month
     * @throws SQLException sql exception
     */
    @FXML
    private void appointmentMonthSelected(ActionEvent event) throws SQLException {
        try {
            ObservableList<Appointment> allAppointmentsList = AppointmentAccess.getAllAppointments();
            ObservableList<Appointment> appointmentsMonth = FXCollections.observableArrayList();

            LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);

            //LAMBDA expression checks each appointment to see if it is within the current month
            allAppointmentsList.forEach(appointment -> {
                if (appointment.getEnd().isAfter(currentMonthStart) && appointment.getEnd().isBefore(currentMonthEnd)) {
                    appointmentsMonth.add(appointment);
                }
                appointmentsTable.setItems(appointmentsMonth);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LAMBDA expression checks each appointment to see if it is within the current week
     * Button that filters the appointments to ones that are specific to the current week.
     * @param event Filters appointment by ones in current week
     * @throws SQLException Sql exception
     */
    @FXML
    void appointmentWeekSelected(ActionEvent event) throws SQLException {
        try {

            ObservableList<Appointment> allAppointmentsList = AppointmentAccess.getAllAppointments();
            ObservableList<Appointment> appointmentsWeek = FXCollections.observableArrayList();

            LocalDateTime weekStart = LocalDateTime.now().minusWeeks(1);
            LocalDateTime weekEnd = LocalDateTime.now().plusWeeks(1);

            //LAMBDA expression checks each appointment to see if it is within the current week
            allAppointmentsList.forEach(appointment -> {
                if (appointment.getEnd().isAfter(weekStart) && appointment.getEnd().isBefore(weekEnd)) {
                    appointmentsWeek.add(appointment);
                }
                appointmentsTable.setItems(appointmentsWeek);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if appointment is overlapped with another appointment
     * @param newStart new start time
     * @param newEnd new end time
     * @param customerID customer id
     * @param appointmentId appointment id
     * @return boolean
     * @throws SQLException Sql exception
     */
    public static boolean appointmentOverlapCheck(LocalDateTime newStart, LocalDateTime newEnd,LocalDate newDateEnd,LocalDate newDateStart, int customerID, int appointmentId) throws SQLException {
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
                return true;
            }

            if ((newStart.isAfter(oldStart)) && (newEnd.isBefore(oldEnd)))
            {

                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 2");
                Optional<ButtonType> error = alert.showAndWait();
                return true;
            }

            if ((newStart.isEqual(oldStart)) || (newEnd.isEqual(oldEnd)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment equals");
                Optional<ButtonType> error = alert.showAndWait();
                return true;
            }

            if ((newStart.isBefore(oldStart)) && (newEnd.isAfter(oldStart)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 4");
                Optional<ButtonType> error = alert.showAndWait();
                return true;
            }
            if ((newStart.isBefore(oldEnd)) && (newEnd.isAfter(oldEnd)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment overlaps with existing appointment 5");
                Optional<ButtonType> error = alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    /**
     * Method which checks to make sure business hours are taken into consideration when making an appoint as well as displaying an error message if appointment is made outside the available time.
     * @param dateTimeStart start date
     * @param dateTimeEnd end date
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
     * Sets up the appointment page with the information to work properly on initialization
     * @throws SQLException Sql exception
     */
    public void initialize() throws SQLException {

        ObservableList<Appointment> listEveryAptmt = AppointmentAccess.getAllAppointments();
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tableContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        tableUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentsTable.setItems(listEveryAptmt);
        updateUser.setItems(UserAccess.getAllUsers());
        updateCustomer.setItems(CustomerAccess.getAllCustomers());
        ObservableList<Contact> allContacts = ContactAccess.getAllContacts();
        updateContact.setItems(allContacts);
        updateStartTime.setItems(TimeHelp.getStartTimes());
        updateEndTime.setItems(TimeHelp.getEndTimes());
    }
}
