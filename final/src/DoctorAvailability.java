import java.util.List;

public class DoctorAvailability {
    private String doctorId; // Doctor ID
    private List<TimeSlot> slots; // List of available time slots

    // Constructor
    public DoctorAvailability(String doctorId, List<TimeSlot> slots) {
        this.doctorId = doctorId;
        this.slots = slots;
    }

    /**
     * @return String
     */
    // Getter for doctorId
    public String getDoctorId() {
        return doctorId;
    }

    // Getter for slots
    public List<TimeSlot> getSlots() {
        return slots;
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