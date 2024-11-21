import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The `Helper` class provides utility methods for parsing and formatting dates, times,
 * and user inputs. It also includes methods for reading and validating various types of input.
 */
public class Helper {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    /**
     * Parses a string representing a date into a `LocalDate` object.
     *
     * @param dateString The date string in the format "dd-MM-yyyy".
     * @return The `LocalDate` object or `null` if parsing fails.
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + dateString);
            return null;
        }
    }

    /**
     * Parses a string representing a time into a `LocalTime` object.
     *
     * @param timeString The time string in the format "HH:mm".
     * @return The `LocalTime` object or `null` if parsing fails.
     */
    public static LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing time: " + timeString);
            return null;
        }
    }

    /**
     * Parses a string representing a date and time into a `LocalDateTime` object.
     *
     * @param dateTimeString The date-time string in the format "dd-MM-yyyy HH:mm".
     * @return The `LocalDateTime` object or `null` if parsing fails.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date-time: " + dateTimeString);
            return null;
        }
    }

    /**
     * Formats a `LocalDate` object into a string.
     *
     * @param date The `LocalDate` to format.
     * @return The formatted date string in the format "dd-MM-yyyy".
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Formats a `LocalTime` object into a string.
     *
     * @param time The `LocalTime` to format.
     * @return The formatted time string in the format "HH:mm".
     */
    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /**
     * Formats a `LocalDateTime` object into a string.
     *
     * @param dateTime The `LocalDateTime` to format.
     * @return The formatted date-time string in the format "dd-MM-yyyy HH:mm".
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Parses a date string and an hour into a `LocalDateTime` object.
     *
     * @param dateString The date string in the format "dd-MM-yyyy".
     * @param hour       The hour of the day (0-23).
     * @return The `LocalDateTime` object or `null` if parsing fails.
     */
    public static LocalDateTime parseDateAndHour(String dateString, int hour) {
        LocalDate date = parseDate(dateString);
        if (date == null) {
            return null;
        }
        return date.atTime(hour, 0);
    }

}
