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

       private String generateAppointmentID() {
        return "A" + (appointmentCounter++);
    }

    public void scheduleAppointment(Patient patient, String doctorId, LocalDateTime dateTime) {
        // Create the appointment
        Appointment appointment = new Appointment(generateAppointmentID(), patient.getUserID(), doctorId, dateTime);
        
        // Get the doctor
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Error: Doctor not found.");
            return;
        }

        // Check doctor's availability
        if (!doctor.isAvailable(dateTime)) {
            System.out.println("Error: Doctor is not available at the selected time.");
            return;
        }

        // Book the time slot
        if (!doctor.bookSlot(dateTime.toLocalTime())) {
            System.out.println("Error: Failed to book the time slot.");
            return;
        }

        // Add to doctor's schedule
        doctor.getSchedule().add(appointment);

        // Add to outcome record
        outcomeRecord.addOutcome(appointment);

        System.out.println("Appointment scheduled successfully.");
        System.out.println("Appointment ID: " + appointment.getId());
        System.out.println("Doctor: " + doctor.getName());
        System.out.println("Date/Time: " + dateTime);
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            // Get the doctor
            Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());
            if (doctor != null) {
                // Free the time slot
                doctor.freeSlot(appointment.getDateTime().toLocalTime());
                // Remove from doctor's schedule
                doctor.getSchedule().removeIf(app -> app.getId().equals(appointmentId));
            }
            
            appointment.setStatus(AppointmentStatus.CANCELLED);
            System.out.println("Appointment with ID " + appointmentId + " has been canceled.");
        } else {
            System.out.println("Error: Appointment with ID " + appointmentId + " not found.");
        }
    }

    public void processAppointment(String appointmentId, boolean accept) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setStatus(accept ? AppointmentStatus.CONFIRMED : AppointmentStatus.DECLINED);
            
            // If declined, free up the doctor's schedule
            if (!accept) {
                Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());
                if (doctor != null) {
                    doctor.freeSlot(appointment.getDateTime().toLocalTime());
                    doctor.getSchedule().removeIf(app -> app.getId().equals(appointmentId));
                }
            }
            
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
