import java.time.LocalDateTime;

public class AppointmentService {
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentOutcomeRecord outcomeRecord; // Only instantiated here
    private int appointmentCounter = 1; // For unique appointment IDs

    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance(); // Singleton instantiation
    }

    /**
     * @return String
     */
    private String generateAppointmentID() {
        return "A" + (appointmentCounter++);
    }

    public void scheduleAppointment(Patient patient, String doctorId, LocalDateTime dateTime) {
        Appointment appointment = new Appointment(generateAppointmentID(), patient.getUserID(), doctorId, dateTime);
        outcomeRecord.addOutcome(appointment);
        System.out.println("Appointment scheduled.");
    }

    // !!!!!!!!!!!!! cancellation has to update doctors availability
    // !!!!!!!!!!!!!!!!!!!
    public void cancelAppointment(String appointmentId) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            // Update the status of the appointment to "Canceled" instead of removing it
            appointment.setStatus(AppointmentStatus.CANCELLED);
            System.out.println("Appointment with ID " + appointmentId + " has been canceled.");
        } else {
            // Handle incorrect appointment ID
            System.out.println("Error: Appointment with ID " + appointmentId + " not found.");
        }
    }

    public void processAppointment(String appointmentId, boolean accept) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setStatus(accept ? AppointmentStatus.CONFIRMED : AppointmentStatus.DECLINED);
            System.out.println("Appointment " + (accept ? "confirmed" : "declined") + ".");
        }
    }

    // Methods for modifying appointment details

    public void addConsultationNotes(String appointmentId, String notes) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setConsultationNotes(notes);
            System.out.println("Consultation notes added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    public void setTypeOfService(String appointmentId, TypeOfService serviceType) {
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

    // Method to reschedule an existing appointment
    public boolean rescheduleAppointment(String appointmentID, LocalDateTime newDateTime) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentID);

        if (appointment != null) {
            // Fetch the doctor for this appointment
            Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());

            // Check if the doctor is available at the new time
            if (doctor != null && doctor.isAvailable(newDateTime)) {
                // Free the old time slot
                LocalDateTime oldDateTime = appointment.getDateTime();
                if (!doctor.freeSlot(oldDateTime.toLocalTime())) {
                    System.out.println("Failed to free old time slot.");
                    return false;
                }

                // Book the new time slot
                if (!doctor.bookSlot(newDateTime.toLocalTime())) {
                    System.out.println("Failed to book the new time slot.");
                    return false;
                }

                // Update the appointment date and time
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

    // New method to retrieve an appointment by its ID
    public Appointment getAppointmentById(String appointmentId) {
        return outcomeRecord.getAppointmentById(appointmentId);
    }
}
