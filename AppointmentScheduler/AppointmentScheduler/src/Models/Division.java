package Models;

/**
 * Divisions class containing info to set up the Divisions and all its functions
 **/
public class Division {
    private int divisionID;
    private String divisionName;
    public int countryID;

    /**
     * Constructor
     * @param divisionID ID for division
     * @param countryID ID for country
     * @param divisionName Name for division
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Gets ID
     * @return divisionID
     */
    public int getDivisionID() {

        return divisionID;
    }

    /**
     * Gets name
     * @return divisionName
     */
    public String getDivisionName() {

        return divisionName;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {

        return countryID;
    }

}