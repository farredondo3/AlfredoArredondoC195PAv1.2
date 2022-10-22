package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Sets  and gets time for when business hours are available
 */
public class TimeHelp
{
    public static final ZonedDateTime START_EST_HOURS = ZonedDateTime.of(LocalDate.now(),LocalTime.of(8,0), ZoneId.of("America/New_York"));
    public static final ZonedDateTime END_EST_HOURS = ZonedDateTime.of(LocalDate.now(),LocalTime.of(22,0), ZoneId.of("America/New_York"));

    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    /**
     * gets start times
     * @return
     */
    public static ObservableList<LocalTime> getStartTimes()
    {

        if (startTimes.size() < 1)
        {
            ZonedDateTime start = START_EST_HOURS.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime end = END_EST_HOURS.withZoneSameInstant(ZoneId.systemDefault()).minusMinutes(15);

            while(start.isBefore(end))
            {
                startTimes.add(start.toLocalTime());
                start = start.plusMinutes(15);
            }

        }
        return startTimes;
    }

    /**
     * gets end times
     * @return
     */
    public static ObservableList<LocalTime> getEndTimes()
    {

        if (endTimes.size() < 1)
        {
            ZonedDateTime start = START_EST_HOURS.withZoneSameInstant(ZoneId.systemDefault()).plusMinutes(15);
            ZonedDateTime end = END_EST_HOURS.withZoneSameInstant(ZoneId.systemDefault());

            while(start.isBefore(end))
            {
                endTimes.add(start.toLocalTime());
                start = start.plusMinutes(15);
            }

        }
        return endTimes;
    }
}
