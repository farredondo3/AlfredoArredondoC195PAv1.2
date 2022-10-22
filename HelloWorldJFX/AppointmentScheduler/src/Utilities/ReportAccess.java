package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.DBConnection;
import Models.Appointment;
import Models.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */

public class ReportAccess extends Appointment
{
    public ReportAccess(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, LocalDate startDate,LocalDate endDate, int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentTitle, start, end, customerID, userID, customerID);
    }

    /**
     * Gets countries
     * @throws SQLException Sql exception
     * @return countriesObservableList
     */
    public static ObservableList<Report> getCountries() throws SQLException {
        ObservableList<Report> countriesObservableList = FXCollections.observableArrayList();
        String sql = "select countries.Country, count(*) as countryCount from customers inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID inner join countries on countries.Country_ID = first_level_divisions.Country_ID where  customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Country_ID order by count(*) desc";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            String countryName = rs.getString("Country");
            int countryCount = rs.getInt("countryCount");
            Report report = new Report(countryName, countryCount);
            countriesObservableList.add(report);

        }
        return countriesObservableList;
    }
}
