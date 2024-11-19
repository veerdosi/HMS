import java.util.List;

public class Doctor extends User {
    private int age;
    private String specialization;
    private List<TimeSlot> availability; // List of time slots representing availability
    private List<Appointment> schedule; // List of scheduled appointments
    private AppointmentServiceFacade appointmentServiceFacade;

    // Constructor
    public Doctor(String userID, String password, String contactNumber, String email,
            int age, String gender, String specialization,
            AppointmentServiceFacade appointmentServiceFacade) {
        super(userID, password, contactNumber, email, UserRole.DOCTOR, gender);
        this.age = age;
        this.specialization = specialization;
        this.availability = null; // Default to no availability
        this.schedule = null; // Default to no schedule
        this.appointmentServiceFacade = appointmentServiceFacade;
    }

    // Getter methods
    public int getAge() {
        return age;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<TimeSlot> availability) {
        this.availability = availability;
    }

    public List<Appointment> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Appointment> schedule) {
        this.schedule = schedule;
    }

    // Appointment-related methods

    // Accept an appointment
    public void acceptAppointment(Appointment appointment) {
        appointmentServiceFacade.confirmAppointment(appointment);
        System.out.println("Appointment accepted for patient: " + appointment.getPatient().getName());
    }

    // Decline an appointment
    public void declineAppointment(Appointment appointment) {
        appointmentServiceFacade.cancelAppointment(appointment);
        System.out.println("Appointment declined for patient: " + appointment.getPatient().getName());
    }

    // Record the outcome of an appointment
    public void recordAppointmentOutcome(Appointment appointment, AppointmentOutcomeRecord outcome) {
        appointmentServiceFacade.recordAppointmentOutcome(appointment, outcome);
        System.out.println("Outcome recorded for appointment with patient: " + appointment.getPatient().getName());
    }

    // View the doctor's schedule
    public void viewSchedule() {
        System.out.println("Viewing schedule for Doctor: " + this.getName());
        if (schedule != null) {
            for (Appointment appointment : schedule) {
                System.out.println(appointment);
            }
        } else {
            System.out.println("No scheduled appointments.");
        }
    }

    // View a patient's medical record
    public void viewMedicalRecord(Patient patient) {
        System.out.println("Viewing medical record of patient: " + patient.getName());
        // Additional logic to retrieve and display patient records can be added here.
    }

    // Update a patient's medical record
    public void updateMedicalRecord(UpdateMedicalRecordRequest request) {
        appointmentServiceFacade.updateMedicalRecord(request);
        System.out.println("Medical record updated for patient: " + request.getPatient().getName());
    }

    // Generate default availability slots using a service
    public void generateDefaultAvailability(DoctorAvailabilityService availabilityService) {
        availabilityService.generateDefaultAvailability(this);
    }

    // Set custom availability slots using a service
    public void setCustomAvailability(DoctorAvailabilityService availabilityService, List<TimeSlot> customSlots) {
        availabilityService.setAvailability(this, customSlots);
    }
}