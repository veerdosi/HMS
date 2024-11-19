import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    // Method to parse a date from a string
    public static Date parseDate(String dateString) {
        return parse(dateString, DATE_FORMAT);
    }

    // Method to parse time from a string (date will default to the current date)
    public static Date parseTime(String timeString) {
        return parse(timeString, TIME_FORMAT);
    }

    // Method to parse both date and time from a string
    public static Date parseDateTime(String dateTimeString) {
        return parse(dateTimeString, DATE_TIME_FORMAT);
    }

    // Method to format a Date object as a date string
    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    // Method to format a Date object as a time string
    public static String formatTime(Date date) {
        return format(date, TIME_FORMAT);
    }

    // Method to format a Date object as a date-time string
    public static String formatDateTime(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    // Generic method to parse a date based on a given format
    private static Date parse(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); // Ensures strict date format validation
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + dateString + " with format: " + format);
            return null;
        }
    }

    // Generic method to format a Date object based on a given format
    private static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
