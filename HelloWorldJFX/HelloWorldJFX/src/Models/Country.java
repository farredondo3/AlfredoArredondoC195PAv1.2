package Models;

/**
 * Country class containing info to set up the Country and all its main functions
 **/
public class Country {
    private int countryID;
    private String countryName;

    /**
     * Country constructor
     * @param countryID Country ID
     * @param countryName Country name
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {

        return countryID;
    }

    /**
     *
     * @return countryName
     */
    public String getCountryName() {

        return countryName;
    }
}