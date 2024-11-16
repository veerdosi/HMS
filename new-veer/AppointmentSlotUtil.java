import java.util.ArrayList;
import java.util.List;

public class AppointmentSlotUtil {
    public static List<TimeSlot> generateDailySlots() {
        List<TimeSlot> slots = new ArrayList<>();
        String[] startTimes = { "09:00", "10:00", "11:00", "14:00", "15:00", "16:00" };
        for (String startTime : startTimes) {
            String endTime = calculateEndTime(startTime);
            slots.add(new TimeSlot(startTime, endTime));
        }
        return slots;
    }

    private static String calculateEndTime(String startTime) {
        String[] parts = startTime.split(":");
        int hour = Integer.parseInt(parts[0]) + 1; // Add 1 hour
        return String.format("%02d:%02d", hour, 0);
    }
}