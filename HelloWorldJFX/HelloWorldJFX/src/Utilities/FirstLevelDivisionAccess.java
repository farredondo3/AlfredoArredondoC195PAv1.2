package Utilities;

import Main.DBConnection;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */

public class FirstLevelDivisionAccess extends Division
{
    public FirstLevelDivisionAccess(int divisionID, String divisionName, int country_ID) {
        super(divisionID, divisionName, country_ID);
    }

    /**
     * Gets first level division
     * @return firstLevelDivisionsObservableList
     */
    public static ObservableList<FirstLevelDivisionAccess> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivisionAccess> firstLevelDivisionsObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int country_ID = rs.getInt("COUNTRY_ID");
                FirstLevelDivisionAccess firstLevelDivision = new FirstLevelDivisionAccess(divisionID, divisionName, country_ID);
                firstLevelDivisionsObservableList.add(firstLevelDivision);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return firstLevelDivisionsObservableList;
    }

    /**
     * Gets division name
     * @param divisionName division name
     * @return division
     */
    public static Division getDivisionFromName(String divisionName)
    {
        ObservableList<FirstLevelDivisionAccess> list = getAllFirstLevelDivisions();
        for (Division division: FirstLevelDivisionAccess.getAllFirstLevelDivisions()) {
            if (division.getDivisionName().equals(divisionName)) {
                return division;
            }
        }
        return null;
    }
}
