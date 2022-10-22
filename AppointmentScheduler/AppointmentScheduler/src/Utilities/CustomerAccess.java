package Utilities;

import Main.DBConnection;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */

public class CustomerAccess {
    /**
     * List of customers
     * @return customersObservableList
     * @throws SQLException Sql exception
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, countries.Country_ID, countries.Country, customers.Division_ID, first_level_divisions.Division " +
                "FROM customers " +
                "INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "INNER JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();

        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, countryID, divisionID, countryName, divisionName);
            customersObservableList.add(customer);
        }
        return customersObservableList;
    }

    /**
     * Add customer
     * @param customerName Name
     * @param customerAddress Address
     * @param customerPostalCode Zip code
     * @param customerPhoneNumber Phone number
     * @param divisionID Division ID
     */
    public static void addCustomer(String customerName, String customerAddress, String customerPostalCode,
                                   String customerPhoneNumber, int countryID,int divisionID) {
        try {
            Connection connection = DBConnection.startConnection();


                String insertStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Division_ID) VALUES (?,?,?,?,NOW(),?,NOW(),?)"; //Customer_ID, Customer_Name, 1&2
                PreparedStatement ps = DBConnection.getPreparedStatement(insertStatement);
                ps.setString(1, customerName);
                ps.setString(2, customerAddress);
                ps.setString(3, customerPostalCode);
                ps.setString(4, customerPhoneNumber);
                ps.setString(5, "admin");
                ps.setInt(6, divisionID);
                ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(String customerNameEdit, String customerAddressEdit, String customerEditPostal,
                                      String customerEditPhone, int divisionID, int customerIDEdit) throws SQLException
    {
        try {
            Connection connection = DBConnection.startConnection();
            String insertStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";//Customer_ID = ?,
            PreparedStatement ps = DBConnection.getPreparedStatement(insertStatement);
            ps.setString(1, customerNameEdit);
            ps.setString(2, customerAddressEdit);
            ps.setString(3, customerEditPostal);
            ps.setString(4, customerEditPhone);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, "admin");
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "admin");
            ps.setInt(9, divisionID);
            ps.setInt(10, customerIDEdit);
            ps.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
