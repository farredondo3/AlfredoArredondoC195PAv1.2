package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Appointment class containing info to set up the appointment and all its main functions
 */
public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;

    /**
     * Template of appointment
     * @param appointmentID appointment id
     * @param appointmentTitle appointment title
     * @param appointmentDescription description
     * @param appointmentLocation location
     * @param appointmentType type
     * @param start start
     * @param end end
     * @param customerID customer id
     * @param userID user id
     * @param contactID contact id
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID,
                        int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }

    /**
     * Get appointment ID
     * @return appointmentID
     */
    public int getAppointmentID() {

        return appointmentID;
    }

    /**
     * Get appointment title
     * @return appointmentTitle
     */
    public String getAppointmentTitle() {

        return appointmentTitle;
    }

    /**
     * Get appointment description
     * @return appointmentDescription
     */
    public String getAppointmentDescription() {

        return appointmentDescription;
    }

    /**
     * Get appointment location
     * @return appointmentLocation
     */
    public String getAppointmentLocation() {

        return appointmentLocation;
    }

    /**
     * Get appointment type
     * @return appointmentType
     */
    public String getAppointmentType() {

        return appointmentType;
    }


    /**
     * Get start time
     * @return start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Gets end time
     * @return end
     */
    public LocalDateTime getEnd() {

        return end;
    }

    /**
     * Get customer ID
     * @return customerID
     */
    public int getCustomerID () {

        return customerID;
    }

    /**
     * Get user Id
     * @return userID
     */
    public int getUserID() {

        return userID;
    }

    /**
     * Get contact ID
     * @return contactID
     */
    public int getContactID() {

        return contactID;
    }

}
