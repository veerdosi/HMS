import java.util.List;

/**
 * The `SetAvailabilityCommand` class implements the `DoctorCommand` interface and is responsible for 
 * setting the availability of a doctor using a list of time slots.
 */
public class SetAvailabilityCommand implements DoctorCommand {
    private Doctor doctor;
    private List<TimeSlot> availableSlots;

    /**
     * Constructs a `SetAvailabilityCommand` with the specified doctor and available slots.
     *
     * @param doctor         the `Doctor` object whose availability will be set.
     * @param availableSlots a list of `TimeSlot` objects representing the doctor's availability.
     */
    public SetAvailabilityCommand(Doctor doctor, List<TimeSlot> availableSlots) {
        this.doctor = doctor;
        this.availableSlots = availableSlots;
    }

    /**
     * Executes the command to set the doctor's availability with the provided time slots.
     */
    @Override
    public void execute() {
        doctor.setAvailability(availableSlots);
    }
}
