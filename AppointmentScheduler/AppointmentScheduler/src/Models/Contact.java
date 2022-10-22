package Models;

/**
 * Contact class containing info to set up the Contact and all its main functions
**/
public class Contact {
    public int contactID;
    public String contactName;
    public String contactEmail;

    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * get contact ID
     * @return contactID
     */
    public int getContactID() {

        return contactID;
    }

    /**
     * Get contact name
     * @return contactName
     */
    public String getContactName() {

        return contactName;
    }

    /**
     * To string
     * @return to String
     */
    @Override
    public String toString()
    {
        return contactName;
    }
}