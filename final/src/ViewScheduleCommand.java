public class ViewScheduleCommand implements DoctorCommand {
    private Doctor doctor;

    public ViewScheduleCommand(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public void execute() {
        doctor.viewSchedule();
    }
}