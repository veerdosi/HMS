import java.time.LocalDateTime;
import java.util.List;

/**
 * The `AppointmentServiceFacade` class provides a simplified interface for managing
 * appointments, doctor availabilities, and patient data. It integrates multiple
 * services, including `DoctorService`, `PatientService`, `AppointmentService`,
 * and `PrescriptionService`, and follows the Singleton design pattern.
 */
public class AppointmentServiceFacade {
    private static AppointmentServiceFacade instance;
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentService appointmentService;
    private PrescriptionService prescriptionService;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the services with the file paths to their respective data sources.
     *
     * @param patientFilePath The file path to the patient data.
     * @param staffFilePath   The file path to the staff data.
     */
    private AppointmentServiceFacade(String patientFilePath, String staffFilePath) {
        this.doctorService = new DoctorService(staffFilePath);
        this.patientService = new PatientService(patientFilePath);
        this.appointmentService = new AppointmentService(doctorService, patientService);
        this.prescriptionService = new PrescriptionService(appointmentService);
    }

    /**
     * Retrieves the Singleton instance of the `AppointmentServiceFacade`, initializing it
     * with file paths if it has not been created yet.
     *
     * @param patientFilePath The file path to the patient data.
     * @param staffFilePath   The file path to the staff data.
     * @return The Singleton instance of `AppointmentServiceFacade`.
     */
    public static AppointmentServiceFacade getInstance(String patientFilePath, String staffFilePath) {
        if (instance == null) {
            instance = new AppointmentServiceFacade(patientFilePath, staffFilePath);
        }
        return instance;
    }

    /**
     * Retrieves the availability of a specific doctor by their ID.
     *
     * @param doctorId The ID of the doctor.
     * @return The availability of the specified doctor.
     */
    public DoctorAvailability getDoctorAvailability(String doctorId) {
        DoctorAvailabilityRepository availabilityRepository = DoctorAvailabilityRepository.getInstance();
        return availabilityRepository.getDoctorAvailability(doctorId);
    }

    /**
     * Retrieves the availability of all doctors.
     *
     * @return A list of all doctors' availabilities.
     */
    public List<DoctorAvailability> getAllDoctorAvailabilities() {
        DoctorAvailabilityRepository availabilityRepository = DoctorAvailabilityRepository.getInstance();
        return availabilityRepository.getAllDoctorAvailabilities();
    }

    /**
     * Prints the availability of all doctors to the console.
     */
    public void printAllDoctorAvailabilities() {
        DoctorAvailabilityRepository availabilityRepository = DoctorAvailabilityRepository.getInstance();
        availabilityRepository.printAllDoctorAvailabilities();
    }

    // Appointment management methods

    /**
     * Schedules an appointment for a specific patient and doctor.
     *
     * @param patient   The patient for whom the appointment is being scheduled.
     * @param doctorId  The ID of the doctor for the appointment.
     * @param dateTime  The date and time of the appointment.
     */
    public void scheduleAppointment(Patient patient, String doctorId, LocalDateTime dateTime) {
        appointmentService.scheduleAppointment(patient, doctorId, dateTime);
    }

    /**
     * Cancels an existing appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to cancel.
     */
    public void cancelAppointment(String appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    }

    /**
     * Processes an appointment by confirming or declining it.
     *
     * @param appointmentId The ID of the appointment to process.
     * @param accept         `true` to confirm the appointment, `false` to decline.
     */
    public void processAppointment(String appointmentId, boolean accept) {
        appointmentService.processAppointment(appointmentId, accept);
    }

    /**
     * Adds consultation notes to an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param notes         The consultation notes to add.
     */
    public void addConsultationNotes(String appointmentId, String notes) {
        appointmentService.addConsultationNotes(appointmentId, notes);
    }

    /**
     * Sets the type of service for an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param serviceType   The type of service to set.
     */
    public void setTypeOfService(String appointmentId, TypeOfService serviceType) {
        appointmentService.setTypeOfService(appointmentId, serviceType);
    }

    /**
     * Adds a prescription to an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param prescription  The prescription to add.
     */
    public void addPrescription(String appointmentId, Prescription prescription) {
        appointmentService.addPrescription(appointmentId, prescription);
    }

    /**
     * Updates the status of a prescription associated with an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param medicine      The medicine associated with the prescription.
     * @param status        The new status to set.
     * @return `true` if the status was successfully updated, `false` otherwise.
     */
    public boolean updatePrescriptionStatus(String appointmentId, Medicine medicine, PrescriptionStatus status) {
        return prescriptionService.updatePrescriptionStatus(appointmentId, medicine, status);
    }

    // Accessors for doctor and patient data

    /**
     * Retrieves a list of all available doctors.
     *
     * @return A list of available doctors.
     */
    public List<Doctor> getAvailableDoctors() {
        return doctorService.getAvailableDoctors();
    }

    /**
     * Retrieves a specific patient by their ID.
     *
     * @param patientId The ID of the patient.
     * @return The patient with the specified ID, or `null` if not found.
     */
    public Patient getPatientById(String patientId) {
        return patientService.getPatientById(patientId);
    }

    /**
     * Reschedules an existing appointment to a new date and time.
     *
     * @param appointmentID The ID of the appointment to reschedule.
     * @param newDateTime   The new date and time for the appointment.
     * @return `true` if the rescheduling was successful, `false` otherwise.
     */
    public boolean rescheduleAppointment(String appointmentID, LocalDateTime newDateTime) {
        return appointmentService.rescheduleAppointment(appointmentID, newDateTime);
    }
}
