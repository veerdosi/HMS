import java.util.ArrayList;
import java.util.List;

/**
 * The `AppointmentSlotUtil` class provides utility methods for generating and managing
 * daily appointment time slots.
 */
public class AppointmentSlotUtil {

    /**
     * Generates a list of daily appointment slots with predefined start times.
     * Each slot is one hour long.
     *
     * @return A list of `TimeSlot` objects representing daily appointment slots.
     */
    public static List<TimeSlot> generateDailySlots() {
        List<TimeSlot> slots = new ArrayList<>();
        String[] startTimes = { "09:00", "10:00", "11:00", "14:00", "15:00", "16:00" };
        for (String startTime : startTimes) {
            String endTime = calculateEndTime(startTime);
            slots.add(new TimeSlot(startTime, endTime));
        }
        return slots;
    }

    /**
     * Calculates the end time for a time slot based on the provided start time.
     * Assumes each slot is one hour long.
     *
     * @param startTime The start time of the slot in "HH:mm" format.
     * @return The end time of the slot in "HH:mm" format.
     */
    private static String calculateEndTime(String startTime) {
        String[] parts = startTime.split(":");
        int hour = Integer.parseInt(parts[0]) + 1; // Add 1 hour
        return String.format("%02d:%02d", hour, 0);
    }
}
