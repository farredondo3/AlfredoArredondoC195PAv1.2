package Models;


/**
 * Customer class containing info to set up the Customer and all its functions
 **/

public class Customer {

    private  String countryName;
    private int countryID;
    private String divisionName;
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionID;

    /**
     * Constructor
     * @param customerID ID
     * @param customerName Name
     * @param customerAddress Address
     * @param customerPostalCode Zip Code
     * @param customerPhoneNumber Phone number
     * @param divisionID Division ID
     * @param divisionName Division name
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, int countryID,int divisionID, String countryName, String divisionName) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.countryID = countryID;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    /**
     *
     * @return customerID
     */
    public Integer getCustomerID() {

        return customerID;
    }

    /**
     * Sets customer
     * @param customerID Customer ID
     */
    public void setCustomerID(Integer customerID) {

        this.customerID = customerID;
    }

    /**
     *
     * @return customerName
     */
    public String getCustomerName() {

        return customerName;
    }

    /**
     *Sets customer name
     * @param customerName Customer name
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    /**
     *
     * @return customerAddress
     */
    public String getCustomerAddress() {

        return customerAddress;
    }

    /**
     * Sets address
     * @param address address
     */
    public void setCustomerAddress(String address) {

        this.customerAddress = address;
    }

    /**
     *
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {

        return customerPostalCode;
    }

    /**
     * Sets zip
     * @param postalCode zip
     */
    public void setCustomerPostalCode(String postalCode) {

        this.customerPostalCode = postalCode;
    }

    /**
     *
     * @return customerPhoneNumber
     */
    public String getCustomerPhone() {

        return customerPhoneNumber;
    }

    /**
     * Sets phone
     * @param phone phone
     */
    public void setCustomerPhone(String phone) {

        this.customerPhoneNumber = phone;
    }

    /**
     *
     * @return divisionID
     */
    public Integer getCustomerDivisionID() {

        return divisionID;
    }

    @Override
    public String toString()
    {
        return customerName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}

