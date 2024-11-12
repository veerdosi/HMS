
//NOTE: MOST OF THE METHODS WILL LOOK VERY DIFFERENT IN FINAL - THIS IS JUST A DRAFT

public class ProcessAppointmentCommand implements Command {
    private Doctor doctor;
    private Appointment appointment;
    private boolean accept;

    public ProcessAppointmentCommand(Doctor doctor, Appointment appointment, boolean accept) {
        this.doctor = doctor;
        this.appointment = appointment;
        this.accept = accept;
    }

    @Override
    public void execute() {
        if (accept) {
            appointment.setStatus("Confirmed");
            // Record additional details as needed
        } else {
            appointment.setStatus("Declined");
        }
    }
}