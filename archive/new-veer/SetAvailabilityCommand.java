import java.time.LocalDateTime;
import java.util.List;

/**
 * The `SetAvailabilityCommand` class implements the Command pattern for setting
 * doctor availability. It includes validation and proper datetime handling.
 */
public class SetAvailabilityCommand implements DoctorCommand {
    private Doctor doctor;
    private List<TimeSlot> availableSlots;
    private DoctorAvailabilityRepository repository;

    /**
     * Constructs a SetAvailabilityCommand with the specified doctor and available
     * slots.
     *
     * @param doctor         The Doctor object whose availability will be set
     * @param availableSlots The list of TimeSlot objects representing availability
     */
    public SetAvailabilityCommand(Doctor doctor, List<TimeSlot> availableSlots) {
        this.doctor = doctor;
        this.availableSlots = availableSlots;
        this.repository = DoctorAvailabilityRepository.getInstance();
    }

    /**
     * Executes the command to set the doctor's availability.
     * Validates the slots before setting them.
     *
     * @throws IllegalArgumentException if the slots are invalid
     */
    @Override
    public void execute() {
        // Validate that all slots are in the future
        LocalDateTime now = LocalDateTime.now();
        if (availableSlots.stream().anyMatch(slot -> slot.getStartDateTime().isBefore(now))) {
            throw new IllegalArgumentException("Cannot set availability for past time slots");
        }

        // Check for overlapping slots
        for (int i = 0; i < availableSlots.size(); i++) {
            for (int j = i + 1; j < availableSlots.size(); j++) {
                if (availableSlots.get(i).overlaps(availableSlots.get(j))) {
                    throw new IllegalArgumentException(
                            String.format("Overlapping slots found: %s and %s",
                                    availableSlots.get(i),
                                    availableSlots.get(j)));
                }
            }
        }

        // Set the availability in both the doctor object and the repository
        doctor.setAvailability(availableSlots);
        repository.setDoctorAvailability(doctor.getUserID(), availableSlots);
    }

    /**
     * Creates a new SetAvailabilityCommand for updating a single time slot
     *
     * @param doctor    The Doctor object whose availability will be updated
     * @param slot      The TimeSlot to update
     * @param available Whether the slot should be available or not
     * @return A new SetAvailabilityCommand
     */
    public static SetAvailabilityCommand createSingleSlotCommand(
            Doctor doctor, TimeSlot slot, boolean available) {
        List<TimeSlot> currentSlots = doctor.getAvailability();
        currentSlots.stream()
                .filter(s -> s.overlaps(slot))
                .forEach(s -> s.setAvailable(available));
        return new SetAvailabilityCommand(doctor, currentSlots);
    }
}