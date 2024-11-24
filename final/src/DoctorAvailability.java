import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `DoctorAvailability` class represents the availability of a doctor,
 * including their ID and a list of available time slots.
 */
public class DoctorAvailability {
    private String doctorId;
    private List<TimeSlot> slots;

    public DoctorAvailability(String doctorId, List<TimeSlot> slots) {
        this.doctorId = doctorId;
        this.slots = new ArrayList<>(slots);
    }

    public String getDoctorId() {
        return doctorId;
    }

    public List<TimeSlot> getSlots() {
        return new ArrayList<>(slots);
    }

    /**
     * Gets all available slots after the specified datetime
     *
     * @param after The datetime after which to find available slots
     * @return List of available time slots
     */
    public List<TimeSlot> getAvailableSlots(LocalDateTime after) {
        return slots.stream()
                .filter(slot -> slot.isAvailable() &&
                        !slot.getStartDateTime().isBefore(after))
                .collect(Collectors.toList());
    }

    /**
     * Books a time slot for the specified datetime
     *
     * @param dateTime The datetime to book
     * @return true if booking was successful, false otherwise
     */
    public boolean bookSlot(LocalDateTime dateTime) {
        for (TimeSlot slot : slots) {
            if (slot.isAvailable() && slot.contains(dateTime)) {
                slot.setAvailable(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Frees a time slot for the specified datetime
     *
     * @param dateTime The datetime to free
     * @return true if the slot was freed, false if no matching slot was found
     */
    public boolean freeSlot(LocalDateTime dateTime) {
        for (TimeSlot slot : slots) {
            if (slot.contains(dateTime)) {
                slot.setAvailable(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a doctor is available at a specific datetime
     *
     * @param dateTime The datetime to check
     * @return true if the doctor is available, false otherwise
     */
    public boolean isAvailable(LocalDateTime dateTime) {
        // Must be at least 24 hours in advance
        LocalDateTime minBookingTime = LocalDateTime.now().plusHours(24);
        if (dateTime.isBefore(minBookingTime)) {
            return false;
        }

        return slots.stream()
                .anyMatch(slot -> slot.isAvailable() && slot.contains(dateTime));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Doctor ID: ").append(doctorId).append("\nAvailable Slots:\n");
        if (slots != null && !slots.isEmpty()) {
            for (TimeSlot slot : slots) {
                sb.append(slot).append("\n");
            }
        } else {
            sb.append("No available slots.\n");
        }
        return sb.toString();
    }
}