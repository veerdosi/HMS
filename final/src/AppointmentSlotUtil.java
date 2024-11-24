import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating and managing appointment time slots.
 * Provides methods to create default scheduling slots and validate time
 * periods.
 */
public class AppointmentSlotUtil {

    // Define standard clinic hours
    private static final LocalTime MORNING_START = LocalTime.of(9, 0);
    private static final LocalTime MORNING_END = LocalTime.of(12, 0);
    private static final LocalTime AFTERNOON_START = LocalTime.of(14, 0);
    private static final LocalTime AFTERNOON_END = LocalTime.of(17, 0);

    /**
     * Generates a list of default time slots for the next 7 days.
     * Creates hourly slots during standard clinic hours:
     * Morning: 9 AM - 12 PM
     * Afternoon: 2 PM - 5 PM
     *
     * @return List of TimeSlot objects
     */
    public static List<TimeSlot> generateDailySlots() {
        List<TimeSlot> slots = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        // Generate slots for the next 7 days
        for (int day = 1; day <= 7; day++) {
            LocalDateTime currentDate = now.plusDays(day);

            // Morning slots
            LocalDateTime timePointer = currentDate.with(MORNING_START);
            while (!timePointer.toLocalTime().isAfter(MORNING_END)) {
                if (timePointer.toLocalTime().isBefore(MORNING_END)) {
                    slots.add(new TimeSlot(
                            timePointer,
                            timePointer.plusHours(1)));
                }
                timePointer = timePointer.plusHours(1);
            }

            // Afternoon slots
            timePointer = currentDate.with(AFTERNOON_START);
            while (!timePointer.toLocalTime().isAfter(AFTERNOON_END)) {
                if (timePointer.toLocalTime().isBefore(AFTERNOON_END)) {
                    slots.add(new TimeSlot(
                            timePointer,
                            timePointer.plusHours(1)));
                }
                timePointer = timePointer.plusHours(1);
            }
        }

        return slots;
    }

    /**
     * Generates slots for a specific date
     *
     * @param date The date to generate slots for
     * @return List of TimeSlot objects for the specified date
     */
    public static List<TimeSlot> generateSlotsForDate(LocalDateTime date) {
        List<TimeSlot> slots = new ArrayList<>();

        // Morning slots
        LocalDateTime timePointer = date.with(MORNING_START);
        while (!timePointer.toLocalTime().isAfter(MORNING_END)) {
            if (timePointer.toLocalTime().isBefore(MORNING_END)) {
                slots.add(new TimeSlot(
                        timePointer,
                        timePointer.plusHours(1)));
            }
            timePointer = timePointer.plusHours(1);
        }

        // Afternoon slots
        timePointer = date.with(AFTERNOON_START);
        while (!timePointer.toLocalTime().isAfter(AFTERNOON_END)) {
            if (timePointer.toLocalTime().isBefore(AFTERNOON_END)) {
                slots.add(new TimeSlot(
                        timePointer,
                        timePointer.plusHours(1)));
            }
            timePointer = timePointer.plusHours(1);
        }

        return slots;
    }

    /**
     * Validates if a given time falls within clinic hours
     *
     * @param dateTime The datetime to validate
     * @return true if the time is within clinic hours, false otherwise
     */
    public static boolean isWithinClinicHours(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return (time.isAfter(MORNING_START.minusMinutes(1)) && time.isBefore(MORNING_END)) ||
                (time.isAfter(AFTERNOON_START.minusMinutes(1)) && time.isBefore(AFTERNOON_END));
    }

    /**
     * Gets the next available slot time after a given datetime
     *
     * @param after The datetime to start looking from
     * @return The next available slot datetime, or null if none found today
     */
    public static LocalDateTime getNextAvailableSlotTime(LocalDateTime after) {
        LocalTime time = after.toLocalTime();
        LocalDateTime nextSlot = after;

        // If before morning hours, set to morning start
        if (time.isBefore(MORNING_START)) {
            return after.with(MORNING_START);
        }

        // If in morning hours
        if (time.isBefore(MORNING_END)) {
            nextSlot = after.plusHours(1);
            if (nextSlot.toLocalTime().isBefore(MORNING_END)) {
                return nextSlot;
            }
        }

        // If between morning and afternoon, set to afternoon start
        if (time.isBefore(AFTERNOON_START)) {
            return after.with(AFTERNOON_START);
        }

        // If in afternoon hours
        if (time.isBefore(AFTERNOON_END)) {
            nextSlot = after.plusHours(1);
            if (nextSlot.toLocalTime().isBefore(AFTERNOON_END)) {
                return nextSlot;
            }
        }

        // If after clinic hours, return start time for next day
        return after.plusDays(1).with(MORNING_START);
    }

    /**
     * Formats a datetime for display in the UI
     *
     * @param dateTime The datetime to format
     * @return Formatted string representation of the datetime
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    /**
     * Checks if a given slot duration is valid (currently fixed at 1 hour)
     *
     * @param startTime The start time of the slot
     * @param endTime   The end time of the slot
     * @return true if the duration is valid, false otherwise
     */
    public static boolean isValidSlotDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return java.time.Duration.between(startTime, endTime).toHours() == 1;
    }
}