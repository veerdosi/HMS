import java.time.LocalDateTime;

/**
 * The `AppointmentService` class handles operations related to managing appointments.
 * It provides functionalities such as scheduling, canceling, rescheduling, and modifying
 * appointment details. The service integrates with the `DoctorService`, `PatientService`,
 * and the `AppointmentOutcomeRecord`.
 */
public class AppointmentService {
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentOutcomeRecord outcomeRecord; // Singleton instance for managing appointment records
    private int appointmentCounter = 1; // Counter to generate unique appointment IDs

    /**
     * Constructs an `AppointmentService` with the specified doctor and patient services.
     *
     * @param doctorService  The service for managing doctor-related operations.
     * @param patientService The service for managing patient-related operations.
     */
    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance(); // Singleton instantiation
    }

    /**
     * Generates a unique appointment ID using a counter.
     *
     * @return A unique appointment ID.
     */
    private String generateAppointmentID() {
        return "A" + (appointmentCounter++);
    }

    /**
     * Schedules a new appointment for the specified patient and doctor.
     *
     * @param patient   The patient for whom the appointment is being scheduled.
     * @param doctorId  The ID of the doctor for the appointment.
     * @param dateTime  The date and time of the appointment.
     */
    public void scheduleAppointment(Patient patient, String doctorId, LocalDateTime dateTime) {
        Appointment appointment = new Appointment(generateAppointmentID(), patient.getUserID(), doctorId, dateTime);
        outcomeRecord.addOutcome(appointment);
        System.out.println("Appointment scheduled.");
    }

    /**
     * Cancels an existing appointment by its ID. Updates the appointment's status
     * to "CANCELLED".
     *
     * @param appointmentId The ID of the appointment to cancel.
     */
    public void cancelAppointment(String appointmentId) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            System.out.println("Appointment with ID " + appointmentId + " has been canceled.");
        } else {
            System.out.println("Error: Appointment with ID " + appointmentId + " not found.");
        }
    }

    /**
     * Processes an appointment by confirming or declining it.
     *
     * @param appointmentId The ID of the appointment to process.
     * @param accept         `true` to confirm the appointment, `false` to decline.
     */
    public void processAppointment(String appointmentId, boolean accept) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setStatus(accept ? AppointmentStatus.CONFIRMED : AppointmentStatus.DECLINED);
            System.out.println("Appointment " + (accept ? "confirmed" : "declined") + ".");
        }
    }

    /**
     * Adds consultation notes to an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param notes         The consultation notes to add.
     */
    public void addConsultationNotes(String appointmentId, String notes) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setConsultationNotes(notes);
            System.out.println("Consultation notes added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    /**
     * Sets the type of service for an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param serviceType   The type of service to set.
     */
    public void setTypeOfService(String appointmentId, TypeOfService serviceType) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setTypeOfService(serviceType);
            System.out.println("Type of service set to " + serviceType + " for appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    /**
     * Adds a prescription to an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param prescription  The prescription to add.
     */
    public void addPrescription(String appointmentId, Prescription prescription) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.addPrescription(prescription);
            System.out.println("Prescription added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    /**
     * Reschedules an existing appointment to a new date and time.
     *
     * @param appointmentID The ID of the appointment to reschedule.
     * @param newDateTime   The new date and time for the appointment.
     * @return `true` if the rescheduling was successful, `false` otherwise.
     */
    public boolean rescheduleAppointment(String appointmentID, LocalDateTime newDateTime) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentID);

        if (appointment != null) {
            Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());

            if (doctor != null && doctor.isAvailable(newDateTime)) {
                LocalDateTime oldDateTime = appointment.getDateTime();
                if (!doctor.freeSlot(oldDateTime.toLocalTime())) {
                    System.out.println("Failed to free old time slot.");
                    return false;
                }

                if (!doctor.bookSlot(newDateTime.toLocalTime())) {
                    System.out.println("Failed to book the new time slot.");
                    return false;
                }

                appointment.setDateTime(newDateTime);
                System.out.println("Appointment rescheduled to " + newDateTime);
                return true;
            } else {
                System.out.println("The doctor is not available at the requested time.");
                return false;
            }
        } else {
            System.out.println("Appointment not found for ID: " + appointmentID);
            return false;
        }
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to retrieve.
     * @return The appointment with the specified ID, or `null` if not found.
     */
    public Appointment getAppointmentById(String appointmentId) {
        return outcomeRecord.getAppointmentById(appointmentId);
    }
}
