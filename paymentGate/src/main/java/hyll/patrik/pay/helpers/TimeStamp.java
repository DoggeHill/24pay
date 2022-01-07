package hyll.patrik.pay.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simply returns Timestamp in needed format
 *
 * @author Patrik
 */
public class TimeStamp {

    /**
     * Return date time formatted string
     *
     * @return String
     */
    public static String getTimeStamp() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}
