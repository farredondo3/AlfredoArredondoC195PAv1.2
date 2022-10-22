package Utilities;

import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */
public class AppointmentAccess
{
    /**
     * Observablelist for all appointments in database.
     * @throws SQLException sql exception
     * @return appointmentsObservableList
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

    /**
     * Method that deletes appointment based on appointment ID.
     * @param customer customer
     * @param connection connection
     * @return result
     * @throws SQLException sql exception
     */
    public static int deleteAppointment(int customer, Connection connection) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, customer);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    /**
     * add appointment method
     * @param appointmentTitle title
     * @param appointmentDescription description
     * @param appointmentLocation location
     * @param appointmentType type
     * @param start start
     * @param end end
     * @param customerID customer id
     * @param userID user id
     * @param contactID contact id
     */
    public static void addAppointment(String appointmentTitle, String appointmentDescription,
                                      String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID,
                                      int userID, int contactID)
    {
        try {
            Connection connection = DBConnection.startConnection();

            String insertStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,now(),?,now(),?,?,?,?)";

            PreparedStatement ps = DBConnection.getPreparedStatement(insertStatement);
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, "admin");
            ps.setString(8, "admin");
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);

            System.out.println("ps " + ps);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates appointment
     * @param updateAppointmentTitle Title
     * @param updateAppointmentDescription Description
     * @param updateAppointmentLocation Location
     * @param updateAppointmentType Type
     * @param start Start
     * @param end End
     * @param customerID Customer ID
     * @param userID User ID
     * @param contactID Contact ID
     * @param appointmentID Appointment ID
     */
    public static void updateAppointment(String updateAppointmentTitle, String updateAppointmentDescription,
                                      String updateAppointmentLocation, String updateAppointmentType, String start, String end, int customerID,
                                      int userID, int contactID, int appointmentID)
    {
        try {
            //Connection connection = DBConnection.startConnection();



            String insertStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";//(1/12) Appointment_ID = ?,
            PreparedStatement ps = DBConnection.getPreparedStatement(insertStatement);
            //ps.setInt(1, Integer.parseInt(updateAppointmentID.getText()));
            ps.setString(1, updateAppointmentTitle);
            ps.setString(2, updateAppointmentDescription);
            ps.setString(3, updateAppointmentLocation);
            ps.setString(4, updateAppointmentType);
            ps.setString(5, start);
            ps.setString(6, end);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "admin");
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            ps.setInt(12, appointmentID);

            System.out.println("ps " + ps);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Gets all types of appointments
     * @return list of types
     * @throws SQLException
     */
    public static ObservableList<String> getAllTypes() throws SQLException {
        String sql = "SELECT DISTINCT TYPE FROM appointments";

        ObservableList<String> appointmentsObservableList = FXCollections.observableArrayList();
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String appointmentType = rs.getString("Type");
            appointmentsObservableList.add(appointmentType);
        }

        return appointmentsObservableList;
    }

    public static int getMonthTypeCount(String month, String type) throws SQLException {
        String sql = "SELECT count(*) FROM client_schedule.appointments WHERE TYPE = ? AND monthname(START) = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, type);
        ps.setString(2,month);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
