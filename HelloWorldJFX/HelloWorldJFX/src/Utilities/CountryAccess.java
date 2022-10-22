package Utilities;

import Models.Country;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Main.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Communicates with sql to send and receive the new information being saved, updated, or deleted
 */

public class CountryAccess extends Country
{
    /**
     * Constructor
     * @param countryID Country ID
     * @param countryName Country Name
     */
    public CountryAccess(int countryID, String countryName) {
        super(countryID, countryName);
    }

    /**
     *
     * @return countriesObservableList
     */
    public static ObservableList<CountryAccess> getCountries() {
        ObservableList<CountryAccess> countriesObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Country_ID, Country from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                CountryAccess country = new CountryAccess(countryID, countryName);
                countriesObservableList.add(country);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return countriesObservableList;
    }
    public static Country getCountryFromName(String countryName)
    {
        ObservableList<CountryAccess> list = getCountries();
        for (Country country: CountryAccess.getCountries()) {
            if (country.getCountryName().equals(countryName)) {
                return country;
            }
        }
        return null;
    }
}
