import java.util.Date;
import java.util.List;

public class AppointmentServiceFacade {
    private static AppointmentServiceFacade instance;
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentService appointmentService;
    private PrescriptionService prescriptionService;

    // Private constructor for Singleton
    private AppointmentServiceFacade(String patientFilePath, String staffFilePath) {
        // Initialize services with paths to their respective Excel sheets
        this.doctorService = new DoctorService(staffFilePath);
        this.patientService = new PatientService(patientFilePath);
        this.appointmentService = new AppointmentService(doctorService, patientService);
        this.prescriptionService = new PrescriptionService(appointmentService);
    }

    // Static method to get the single instance, initializing with file paths if not yet created
    public static AppointmentServiceFacade getInstance(String patientFilePath, String staffFilePath) {
        if (instance == null) {
            instance = new AppointmentServiceFacade(patientFilePath, staffFilePath);
        }
        return instance;
    }

    // Public methods for managing appointments through the facade
    public void scheduleAppointment(Patient patient, String doctorId, Date dateTime) {
        appointmentService.scheduleAppointment(patient, doctorId, dateTime);
    }
    //might be useless considering if cancelled
/*
    public void cancelAppointment(String appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    } */

    public void processAppointment(String appointmentId, boolean accept) {
        appointmentService.processAppointment(appointmentId, accept);
    }

    public void addConsultationNotes(String appointmentId, String notes) {
        appointmentService.addConsultationNotes(appointmentId, notes);
    }

    public void setTypeOfService(String appointmentId, TypeOfService serviceType) {
        appointmentService.setTypeOfService(appointmentId, serviceType);
    }

    public void addPrescription(String appointmentId, Prescription prescription) {
        appointmentService.addPrescription(appointmentId, prescription);
    }

    public void updatePrescriptionStatus(String appointmentId, Medicine medicine, PrescriptionStatus status) {
        prescriptionService.updatePrescriptionStatus(appointmentId, medicine, status);
    }

    // Public methods to access doctors and patients from DoctorService and PatientService
    public List<Doctor> getAvailableDoctors() {
        return doctorService.getAvailableDoctors();
    }

    public Patient getPatientById(String patientId) {
        return patientService.getPatientById(patientId);
    }

    public boolean rescheduleAppointment(String appointmentID, Date newDateTime) {
        return appointmentService.rescheduleAppointment(appointmentID, newDateTime);
    }
}
