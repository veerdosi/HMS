
//NOTE: MOST OF THE METHODS WILL LOOK VERY DIFFERENT IN FINAL - THIS IS JUST A DRAFT

public class CreateAppointmentCommand implements Command {
    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;
 //   private AppointmentServiceFacade facade;

    public CreateAppointmentCommand(Patient patient, Doctor doctor, String dateTime, AppointmentServiceFacade facade) {
        this.patient = patient;
        this.doctor = doctor;
     //   this.facade = facade;
        this.appointment = new Appointment(/* Generate appointment ID */, patient.getUserID(), doctor.getUserID(), dateTime);
    }

    @Override
    public void execute() {
        // Logic to create an appointment and notify the doctor
        doctor.addAppointment(appointment);
        patient.confirmAppointment(appointment);
    }
}