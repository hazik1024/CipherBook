package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    private static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date) {
        return dateToString(date, FORMATTER);
    }
    public static String dateToString(Date date, String formatter) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatter);
        return format.format(date);
    }

    public static Date stringToDate(String dateString) {
        return stringToDate(dateString, FORMATTER);
    }

    public static Date stringToDate(String dateString, String formatter) {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatter);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
