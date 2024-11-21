/**
 * The `ViewScheduleCommand` class implements the `DoctorCommand` interface
 * and represents a command to view a doctor's schedule.
 */
public class ViewScheduleCommand implements DoctorCommand {
    private Doctor doctor;

    /**
     * Constructs a `ViewScheduleCommand` with the specified doctor.
     *
     * @param doctor The doctor whose schedule is to be viewed.
     */
    public ViewScheduleCommand(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Executes the command to view the schedule of the doctor.
     */
    @Override
    public void execute() {
        doctor.viewSchedule();
    }
}
