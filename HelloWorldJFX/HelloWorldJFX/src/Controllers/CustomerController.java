package Controllers;

import Utilities.*;
import Models.Appointment;
import Models.Country;
import Models.Customer;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Main.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Customer page that contains all information about the customer which is then added within the appointment
 */
public class CustomerController implements Initializable {
    @FXML private TableColumn<?, ?> customerName;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<?, ?> customerAddress;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TableColumn<?, ?> customerPhone;
    @FXML private TableColumn<?, ?> customerZip;
    @FXML private TableColumn<?, ?> customerCountry;
    @FXML private TableColumn<?, ?> customerState;
    @FXML private TextField updateCustomerID;
    @FXML private TextField textBoxName;
    @FXML private TextField textBoxPhone;
    @FXML private TextField textBoxZip;
    @FXML private ComboBox<String> state;
    @FXML private ComboBox<String> country;
    @FXML private TextField textBoxAddress;

    /**
     * Returns to main menu
     * @param event Cancels by returning to main menu
     * @throws IOException IO exception
     */
    @FXML
    public void cancelBtn(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();
    }

    /**
     * Deletes selected customer and checks to make sure the appointments made associated with selected customer are deleted
     * @throws Exception Exception
     * @param event Deletes customer
     */
    @FXML
    void deleteCustomer(ActionEvent event) throws Exception {
        try {
            Connection connection = DBConnection.startConnection();

            if (!textBoxName.getText().isEmpty() && !textBoxAddress.getText().isEmpty() && !textBoxZip.getText().isEmpty() && !textBoxPhone.getText().isEmpty() && state.getValue() != null) {
                ObservableList<Appointment> getAllAppointmentsList = AppointmentAccess.getAllAppointments();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected customer and all appointments? ");
                Optional<ButtonType> confirmation = alert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    int deleteID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
                    AppointmentAccess.deleteAppointment(deleteID, connection);

                    String sqlDelete = "DELETE FROM customers WHERE Customer_ID = ?";

                    PreparedStatement psDel = DBConnection.getPreparedStatement(sqlDelete);
                    int selectedCustomer = customerTable.getSelectionModel().getSelectedItem().getCustomerID();

                    psDel.setInt(1, selectedCustomer);
                    psDel.execute();
                    ObservableList<Customer> refreshList = CustomerAccess.getAllCustomers();
                    customerTable.setItems(refreshList);
                }
            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a customer!");
                Optional<ButtonType> error = alert.showAndWait();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     *Updates selected customer information to be up to date
     * @throws SQLException Sql Exception
     * @param event Updates customer
     */

    @FXML
    void updateCustomerBtn(ActionEvent event)  throws SQLException {

        try {
            if (!textBoxName.getText().isEmpty() || !textBoxAddress.getText().isEmpty() || !textBoxAddress.getText().isEmpty() || !textBoxZip.getText().isEmpty() || !textBoxPhone.getText().isEmpty() || (country.getValue() != null) || (state.getValue() != null))
            {
                int customer = Integer.parseInt(updateCustomerID.getText());

                int firstLevelDivisionId = 0;
                for (FirstLevelDivisionAccess firstLevelDivision : FirstLevelDivisionAccess.getAllFirstLevelDivisions()) {
                    if (state.getSelectionModel().getSelectedItem().equals(firstLevelDivision.getDivisionName())) {
                        firstLevelDivisionId = firstLevelDivision.getDivisionID();
                    }
                }

                CustomerAccess.updateCustomer(textBoxName.getText(), textBoxAddress.getText(), textBoxZip.getText(), textBoxPhone.getText(),firstLevelDivisionId,customer);

                updateCustomerID.clear();
                textBoxName.clear();
                textBoxAddress.clear();
                textBoxZip.clear();
                textBoxPhone.clear();

                ObservableList<Customer> updateList = CustomerAccess.getAllCustomers();
                customerTable.setItems(updateList);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a customer!");
                Optional<ButtonType> error = alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

    /**
     * Button that adds customer to the page and checks to make sure all necessary data is filled
     * @param event Add customer
     */
    @FXML
    void addCustomerBtn(ActionEvent event)
    {
        try {
            if (!textBoxName.getText().isEmpty() && !textBoxAddress.getText().isEmpty() && !textBoxZip.getText().isEmpty() && !textBoxPhone.getText().isEmpty() && country.getValue() != null && state.getValue() != null)
            {
                int countryId = CountryAccess.getCountryFromName(country.getValue()).getCountryID();
                int firstLevelDivisionId = FirstLevelDivisionAccess.getDivisionFromName(state.getValue()).getDivisionID();
                CustomerAccess.addCustomer(textBoxName.getText(),
                        textBoxAddress.getText(), textBoxZip.getText(), textBoxPhone.getText(), countryId,
                        firstLevelDivisionId);

                updateCustomerID.clear();
                textBoxName.clear();
                textBoxAddress.clear();
                textBoxZip.clear();
                textBoxPhone.clear();

                ObservableList<Customer> updateAppointments = CustomerAccess.getAllCustomers();
                customerTable.setItems(updateAppointments);
            }
            else
            {Alert alert = new Alert(Alert.AlertType.ERROR, "You need to add more data ");
                Optional<ButtonType> error = alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the incomplete data filled in by user in order to start over
     * @param event Event
     */
    @FXML
    void clearBtn(ActionEvent event)  {

        updateCustomerID.clear();
        textBoxName.clear();
        textBoxAddress.clear();
        textBoxZip.clear();
        textBoxPhone.clear();
        customerTable.getSelectionModel().clearSelection();
    }

    /**
     * 2 LAMBDA expressions
     * The first lambda expression gets all the first level division names depending on the choice made by the user.
     * The second lambda expression is used 3 times the same way and sets the names of the first level divisions depending on which country is chosen.
     * Customer uses combo box to determine what country and thus appropriately which states will be available when selecting the country.
     * @param event Event
     * @throws SQLException Sql exception
     */
    public void countryBox(ActionEvent event) throws SQLException {
        try {
            DBConnection.startConnection();

            String countryChosen = country.getSelectionModel().getSelectedItem();
            ObservableList<FirstLevelDivisionAccess> getAllFirstLevelDivisions = FirstLevelDivisionAccess.getAllFirstLevelDivisions();
            ObservableList<String> divisionUS = FXCollections.observableArrayList();
            ObservableList<String> divisionUK = FXCollections.observableArrayList();
            ObservableList<String> divisionCanada = FXCollections.observableArrayList();

            //LAMBDA expression gets the names for the first level divisions.
            getAllFirstLevelDivisions.forEach(firstLevelDivision -> {
                if (firstLevelDivision.getCountryID() == 1) {
                    divisionUS.add(firstLevelDivision.getDivisionName());
                } else if (firstLevelDivision.getCountryID() == 2) {
                    divisionUK.add(firstLevelDivision.getDivisionName());
                } else if (firstLevelDivision.getCountryID() == 3) {
                    divisionCanada.add(firstLevelDivision.getDivisionName());
                }
            });
            // LAMBDA expression switches depending on which country is chosen
            switch (countryChosen) {
                case "U.S" -> state.setItems(divisionUS);
                case "UK" -> state.setItems(divisionUK);
                case "Canada" -> state.setItems(divisionCanada);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LAMBDA expression sets selection listener for the controller table
     * Initializes the page of customers with most up to date information when entering it from another page
     * @param url Url
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<CountryAccess> allCountries = CountryAccess.getCountries();
            ObservableList<String> countryNames = FXCollections.observableArrayList();
            ObservableList<FirstLevelDivisionAccess> flDivisionsList = FirstLevelDivisionAccess.getAllFirstLevelDivisions();
            ObservableList<String> flDivisionNames = FXCollections.observableArrayList();
            ObservableList<Customer> allCustomersList = CustomerAccess.getAllCustomers();

            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerZip.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerCountry.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            customerState.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

            allCountries.stream().map(Country::getCountryName).forEach(countryNames::add);
            country.setItems(countryNames);

            state.setItems(flDivisionNames);
            customerTable.setItems(allCustomersList);
            //LAMBDA sets selection listener for the controller table
            customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldCustomer, selectedCustomer) -> {
                if (selectedCustomer != null) {
                    ObservableList<CountryAccess> getAllCountries = CountryAccess.getCountries();
                    ObservableList<FirstLevelDivisionAccess> getDivisionNames = FirstLevelDivisionAccess.getAllFirstLevelDivisions();
                    ObservableList<String> everyDivision = FXCollections.observableArrayList();

                    state.setItems(everyDivision);

                    updateCustomerID.setText(String.valueOf((selectedCustomer.getCustomerID())));
                    textBoxName.setText(selectedCustomer.getCustomerName());
                    textBoxAddress.setText(selectedCustomer.getCustomerAddress());
                    textBoxZip.setText(selectedCustomer.getCustomerPostalCode());
                    textBoxPhone.setText(selectedCustomer.getCustomerPhone());

                    String divisionName = "";
                    String countryName = "";

                    // find division to get country ID
                    Division divisionToUse = null;
                    for (Division flDivision: getDivisionNames) {

                        if (flDivision.getDivisionID() == selectedCustomer.getCustomerDivisionID()) {
                            divisionToUse = flDivision;
                            break;
                        }
                    }
                    // set country based on country ID
                    for (Country countryx: getAllCountries) {
                        if (countryx.getCountryID() == divisionToUse.getCountryID()) {
                            countryName = countryx.getCountryName();
                            country.setValue(countryName);
                            break;
                        }
                    }
                    // fill every division list with matching country ID
                    for (Division flDivision: getDivisionNames) {

                        if (flDivision.getCountryID() == divisionToUse.getCountryID()) {
                            everyDivision.add(flDivision.getDivisionName());
                            //break;
                        }
                    }
                    // set the division based on division ID
                    state.setValue(divisionToUse.getDivisionName());
                }
            });
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}