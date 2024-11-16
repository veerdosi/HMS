import java.util.List;
import java.util.stream.Collectors;

public class DoctorAvailabilityService {

    // Method to set availability for a doctor
    public void setAvailability(Doctor doctor, List<TimeSlot> availableSlots) {
        doctor.setAvailability(availableSlots);
        System.out.println("Availability updated for Doctor: " + doctor.getName());
    }

    // Method to get available slots for a doctor
    public List<TimeSlot> getAvailableSlots(Doctor doctor) {
        return doctor.getAvailability()
                .stream()
                .filter(TimeSlot::isAvailable) // Only return available slots
                .collect(Collectors.toList());
    }

    // Method to generate default daily slots for a doctor
    public void generateDefaultAvailability(Doctor doctor) {
        List<TimeSlot> defaultSlots = AppointmentSlotUtil.generateDailySlots();
        doctor.setAvailability(defaultSlots);
        System.out.println("Default availability generated for Doctor: " + doctor.getName());
    }
}