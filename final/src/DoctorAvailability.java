import java.util.List;

/**
 * The `DoctorAvailability` class represents the availability of a doctor,
 * including their ID and a list of available time slots.
 */
public class DoctorAvailability {
    private String doctorId;
    private List<TimeSlot> slots;

    /**
     * Constructs a `DoctorAvailability` object with the specified doctor ID and availability slots.
     *
     * @param doctorId The unique ID of the doctor.
     * @param slots    A list of available time slots for the doctor.
     */
    public DoctorAvailability(String doctorId, List<TimeSlot> slots) {
        this.doctorId = doctorId;
        this.slots = slots;
    }

    /**
     * Gets the doctor ID.
     *
     * @return The doctor's unique ID.
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Gets the list of available time slots for the doctor.
     *
     * @return A list of time slots.
     */
    public List<TimeSlot> getSlots() {
        return slots;
    }

    /**
     * Returns a string representation of the doctor's availability.
     *
     * @return A string containing the doctor's availability details.
     */
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
