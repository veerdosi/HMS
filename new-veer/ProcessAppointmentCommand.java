public class ProcessAppointmentCommand implements DoctorCommand {
    private Doctor doctor;
    private Appointment appointment;
    private boolean accept; // true for accept, false for decline

    public ProcessAppointmentCommand(Doctor doctor, Appointment appointment, boolean accept) {
        this.doctor = doctor;
        this.appointment = appointment;
        this.accept = accept;
    }

    @Override
    public void execute() {
        if (accept) {
            doctor.acceptAppointment(appointment);
        } else {
            doctor.declineAppointment(appointment);
        }
    }
}