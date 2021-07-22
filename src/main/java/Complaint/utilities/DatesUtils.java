package Complaint.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatesUtils {

    private static DateTimeFormatter dtf;

    public static String getInstantDateTime(){
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getInstantDateTimeInDifferentFormat(){
        DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
