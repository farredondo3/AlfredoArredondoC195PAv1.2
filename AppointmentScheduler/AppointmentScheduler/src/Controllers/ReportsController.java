package Controllers;

import Models.*;
import Utilities.AppointmentAccess;
import Utilities.ContactAccess;
import Utilities.ReportAccess;
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
import java.sql.SQLException;
import java.time.Month;
import java.util.Collections;

/**
 * Reports page controller which documents the appointments and customers into several types of reports.
 */
public class ReportsController {

    @FXML private TableView<Appointment> allAppointmentsTable;
    @FXML private TableColumn<?, ?> appointmentContact;
    @FXML private TableColumn<?, ?> appointmentCustomerID;
    @FXML private TableColumn<?, ?> appointmentDescription;
    @FXML private TableColumn<?, ?> appointmentEnd;
    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private TableColumn<?, ?> appointmentLocation;
    @FXML private TableColumn<?, ?> appointmentStart;
    @FXML private TableColumn<?, ?> appointmentTitle;
    @FXML private TableColumn<?, ?> appointmentType;
    @FXML private Button backToMainMenu;
    @FXML private ComboBox<String> contactScheduleContactBox;
    @FXML private TableColumn<?, ?> tableContactID;
    @FXML private TableView<ReportType> appointmentTotalsAppointmentType;
    @FXML private Tab appointmentTotalsTab;
    @FXML private TableView<ReportMonth> appointmentTotalAppointmentByMonth;
    @FXML private TableView<Report> customerByCountry;
    @FXML private TableColumn<?, ?> countryName;
    @FXML private TableColumn<?, ?> countryCounter;
    @FXML private ComboBox<String> appointmentByMonth;
    @FXML private ComboBox<String> appointmentByType;
    public Label theCount;

    /**
     * Reports page that takes into account the contact
     */
    @FXML
    public void contactData() {
        try {
            int contactID = 0;
            ObservableList<Appointment> getData = AppointmentAccess.getAllAppointments();
            ObservableList<Appointment> info = FXCollections.observableArrayList();
            ObservableList<Contact> getAllContacts = ContactAccess.getAllContacts();
            Appointment appointmentInfo;
            String contactName = contactScheduleContactBox.getSelectionModel().getSelectedItem();

            for (Contact contact: getAllContacts) {
                if (contactName.equals(contact.getContactName())) {
                    contactID = contact.getContactID();
                }
            }

            for (Appointment appointment: getData) {
                if (appointment.getContactID() == contactID) {
                    appointmentInfo = appointment;
                    info.add(appointmentInfo);
                }
            }
            allAppointmentsTable.setItems(info);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Page that displays the countries and how many appointments each one has
     * @throws SQLException
     */
    public void customerByCountry() throws SQLException
    {
        try {
            ObservableList<Report> country = ReportAccess.getCountries();
            ObservableList<Report> countriesToAdd = FXCollections.observableArrayList();
            countriesToAdd.addAll(country);
            customerByCountry.setItems(countriesToAdd);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * back button that takes user back to the main menu page
     * @throws IOException
     */
    @FXML
    public void backBtn(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage backToMain = (Stage)((Node)event.getSource()).getScene().getWindow();
        backToMain.setScene(scene);
        backToMain.show();
    }

    /**
     * counts every appointment by month as well as type.
     * @param actionEvent
     * @throws SQLException
     */
    public void appointmentCount(ActionEvent actionEvent) throws SQLException
    {
        String month = appointmentByMonth.getValue();
        String type = appointmentByType.getValue();

        if( month == null || type == null)
        {
            return;
        }

        int count = AppointmentAccess.getMonthTypeCount(month, type);
        theCount.setText(String.valueOf(count));
    }

    /**
     * LAMBDA expression adds contact names in initialization without needing a whole new method
     * initializes the reports page with all necessary elements in order to function as intended
     * @throws SQLException
     */
    public void initialize() throws SQLException
    {
        countryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        countryCounter.setCellValueFactory(new PropertyValueFactory<>("countryCount"));
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        tableContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        ObservableList<Contact> contactsObservableList = ContactAccess.getAllContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        //LAMBDA expression checks to add contact names in initialization.
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
        contactScheduleContactBox.setItems(allContactsNames);
        appointmentByType.setItems(AppointmentAccess.getAllTypes());
        ObservableList<String> monthList = FXCollections.observableArrayList("January","February", "March","April","May","June","July","August","September","October","November", "December");
        appointmentByMonth.setItems(monthList);
    }
}