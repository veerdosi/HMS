import java.util.List;

public class Doctor extends User {
    private int age;
    private String specialization;
    private List<TimeSlot> availability;
    private List<Appointment> schedule;
    private AppointmentServiceFacade appointmentServiceFacade;

    public Doctor(String userID, String password, String contactNumber, String email,
            int age, String gender, String specialization, AppointmentServiceFacade appointmentServiceFacade) {
        super(userID, password, contactNumber, email, UserRole.DOCTOR, gender);
        this.age = age;
        this.specialization = specialization;
        this.availability = new ArrayList<>();
        this.schedule = new ArrayList<>();
        this.appointmentServiceFacade = appointmentServiceFacade;
    }

    public int getAge() {
        return age;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public List<Appointment> getSchedule() {
        return schedule;
    }

    // Method to execute any DoctorCommand
    public void executeCommand(DoctorCommand command) {
        command.execute();
    }

    // Methods for specific interactions
    public void viewMedicalRecord(Patient patient) {
        System.out.println("Viewing medical record of patient: " + patient.getName());
    }

    public void viewSchedule() {
        System.out.println("Viewing schedule for Doctor: " + userID);
    }

    public void setAvailability(List<TimeSlot> availableSlots) {
        this.availability = availableSlots;
        System.out.println("Setting availability for Doctor: " + userID);
    }

    public void acceptAppointment(Appointment appointment) {
        appointmentServiceFacade.confirmAppointment(appointment);
        System.out.println("Appointment accepted for patient: " + appointment.getPatient().getName());
    }

    public void declineAppointment(Appointment appointment) {
        appointmentServiceFacade.cancelAppointment(appointment);
        System.out.println("Appointment declined for patient: " + appointment.getPatient().getName());
    }

    public void recordAppointmentOutcome(Appointment appointment, AppointmentOutcomeRecord outcome) {
        appointmentServiceFacade.recordAppointmentOutcome(appointment, outcome);
    }

    public void updateMedicalRecord(UpdateMedicalRecordRequest request) {
        appointmentServiceFacade.updateMedicalRecord(request);
    }
}