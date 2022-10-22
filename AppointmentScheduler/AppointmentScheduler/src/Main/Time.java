package Main;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that involves properly converting time for appointment scheduler to function correctly
 */
public class Time {

    /**
     * Convert time to UTC and implements it into database.
     * @param dateTime date time
     * @return string
     */
    public static String convertTimeDateUTC(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localFin = utcDT.toLocalDateTime();
        String utcFin = localFin.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        return utcFin;
    }

}