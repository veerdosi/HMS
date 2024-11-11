import java.util.List;

public class SetAvailabilityCommand implements DoctorCommand {
    private Doctor doctor;
    private List<TimeSlot> availableSlots;

    public SetAvailabilityCommand(Doctor doctor, List<TimeSlot> availableSlots) {
        this.doctor = doctor;
        this.availableSlots = availableSlots;
    }

    @Override
    public void execute() {
        doctor.setAvailability(availableSlots);
    }
}