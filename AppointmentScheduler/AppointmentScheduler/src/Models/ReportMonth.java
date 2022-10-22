package Models;

/**
 * Report by month class containing info to set up the Report by month and all its functions
 **/

public class ReportMonth {
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     * Report by month
     * @param appointmentMonth month
     * @param appointmentTotal total
     */
    public ReportMonth(String appointmentMonth, int appointmentTotal) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

}