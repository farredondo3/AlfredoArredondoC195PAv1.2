package Models;

/**
 * Report by type class containing info to set up the Report by type and all its functions
 **/

public class ReportType {
    public String appointmentType;
    public int appointmentTotal;

    /**
     * Report type
     * @param appointmentTotal Total
     * @param appointmentType Type
     */
    public ReportType(String appointmentType, int appointmentTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }
}