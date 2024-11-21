import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeHelper {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    /**
     * @param dateString
     * @return LocalDate
     */
    // Method to parse a date from a string
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + dateString);
            return null;
        }
    }

    // Method to parse time from a string (date will default to the current date)
    public static LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing time: " + timeString);
            return null;
        }
    }

    // Method to parse both date and time from a string
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date-time: " + dateTimeString);
            return null;
        }
    }

    // Method to format a LocalDate object as a date string
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    // Method to format a LocalTime object as a time string
    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    // Method to format a LocalDateTime object as a date-time string
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    // Method to parse a date string and an hour into a LocalDateTime object
    public static LocalDateTime parseDateAndHour(String dateString, int hour) {
        LocalDate date = parseDate(dateString);
        if (date == null) {
            return null; // Return null if the date is invalid
        }
        return date.atTime(hour, 0); // Combine date with the given hour, setting minutes/seconds to 0
    }
}
