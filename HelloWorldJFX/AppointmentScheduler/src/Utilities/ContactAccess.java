package Utilities;

import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */

public class ContactAccess
{
    /**
     * Create observablelist from all contacts.
     * @throws SQLException Sql exception
     * @return contactsObservableList
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            contactsObservableList.add(contact);
        }

        return contactsObservableList;
    }

    /**
     * @throws SQLException Sql exception
     * @param contactID Contact ID
     * @return contactID
     */
    public static String findContactID(String contactID) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        ps.setString(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            contactID = rs.getString("Contact_ID");
        }
        return contactID;
    }
}
