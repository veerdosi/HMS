import java.util.Date;
import java.util.List;

public class AppointmentService {
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentOutcomeRecord outcomeRecord;  // Only instantiated here
    private int appointmentCounter = 1; // For unique appointment IDs

    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();  // Singleton instantiation
    }

    private String generateAppointmentID() {
        return "A" + (appointmentCounter++);
    }

    public void scheduleAppointment(Patient patient, String doctorId, String dateTime) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Date appointmentDate = /* Convert dateTime string to Date */;
        Appointment appointment = new Appointment(generateAppointmentID(), patient.getId(), doctorId, appointmentDate);
        doctor.addAppointment(appointment);
        outcomeRecord.addOutcome(appointment);
        System.out.println("Appointment scheduled.");
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            outcomeRecord.removeOutcome(appointment);
            System.out.println("Appointment canceled.");
        }
    }

    public void processAppointment(String appointmentId, boolean accept) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setStatus(accept ? "Confirmed" : "Declined");
            System.out.println("Appointment " + (accept ? "confirmed" : "declined") + ".");
        }
    }

    // Methods for modifying appointment details

    public void addConsultationNotes(String appointmentId, String notes) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.addConsultationNotes(notes);
            System.out.println("Consultation notes added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    public void setTypeOfService(String appointmentId, String serviceType) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setTypeOfService(serviceType);
            System.out.println("Type of service set to " + serviceType + " for appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    public void addPrescription(String appointmentId, Prescription prescription) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.addPrescription(prescription);
            System.out.println("Prescription added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    // New method to retrieve an appointment by its ID
    public Appointment getAppointmentById(String appointmentId) {
        return outcomeRecord.getAppointmentById(appointmentId);
    }
}
