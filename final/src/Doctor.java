import java.util.List;

public class Doctor extends User {
    private int age;
    private List<TimeSlot> availability; // List of time slots representing availability
    private List<Appointment> schedule; // List of scheduled appointments
    private AppointmentServiceFacade appointmentServiceFacade;

    public Doctor(String userID, String name, String password, String gender,
            String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.DOCTOR, gender, contactEmail, contactNumber);
        this.age = age;
        this.availability = null; // Default to no availability
        this.schedule = null; // Default to no schedule
        this.appointmentServiceFacade = AppointmentServiceFacade.getInstance(); // Singleton instance
    }

    // Getter methods
    public int getAge() {
        return age;
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
        appointmentServiceFacade.processAppointment(appointment.getAppointmentID(), true);
        System.out.println("Appointment accepted for patient: " + appointment.getPatient().getName());
    }

    // Decline an appointment
    public void declineAppointment(Appointment appointment) {
        appointmentServiceFacade.processAppointment(appointment.getAppointmentID(), false);
        System.out.println("Appointment declined for patient: " + appointment.getPatient().getName());
    }

    // Record the outcome of an appointment
    public void recordAppointmentOutcome(Appointment appointment, AppointmentOutcomeRecord outcome) {
        appointmentServiceFacade.addConsultationNotes(appointment.getAppointmentID(), outcome.getNotes());
        appointmentServiceFacade.setTypeOfService(appointment.getAppointmentID(), outcome.getTypeOfService());
        appointmentServiceFacade.addPrescription(appointment.getAppointmentID(), outcome.getPrescriptions());
        System.out.println("Outcome recorded for appointment with patient: " + appointment.getPatient().getName());
    }

    // View the doctor's schedule
    public void viewSchedule() {
        System.out.println("Viewing schedule for Doctor: " + this.getName());
        if (schedule != null && !schedule.isEmpty()) {
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
        List<MedicalRecord> records = appointmentServiceFacade.getPatientMedicalRecords(patient.getUserID());
        for (MedicalRecord record : records) {
            System.out.println(record);
        }
    }

    // Update a patient's medical record
    public void updateMedicalRecord(UpdateMedicalRecordRequest request) {
        appointmentServiceFacade.updateMedicalRecord(request);
        System.out.println("Medical record updated for patient: " + request.getPatient().getName());
    }

    // Generate default availability slots using a service
    public void generateDefaultAvailability(DoctorAvailabilityService availabilityService) {
        availability = availabilityService.generateDefaultAvailability(this);
        System.out.println("Default availability generated for Doctor: " + this.getName());
    }

    // Set custom availability slots using a service
    public void setCustomAvailability(DoctorAvailabilityService availabilityService, List<TimeSlot> customSlots) {
        availabilityService.setAvailability(this, customSlots);
        this.availability = customSlots;
        System.out.println("Custom availability set for Doctor: " + this.getName());
    }

    // Override toString for debugging or logging
    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + getName() + '\'' +
                ", age=" + age +
                ", contactEmail='" + getEmail() + '\'' +
                ", contactNumber='" + getContactNumber() + '\'' +
                '}';
    }
}