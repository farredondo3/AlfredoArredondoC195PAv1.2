package Models;

/**
 * Reports class containing info to set up the Reports and all its functions
 **/
public class Report {

    private int countryCount;
    private String countryName;

    /**
     * @param countryName
     * @param countryCount
     */
    public Report(String countryName, int countryCount) {
        this.countryCount = countryCount;
        this.countryName = countryName;

    }

    /**
     * Returns country name for custom report.
     * @return countryName
     */
    public String getCountryName() {

        return countryName;
    }

    /**
     * Total for each country.
     * @return countryCount
     */
    public int getCountryCount() {

        return countryCount;
    }

}