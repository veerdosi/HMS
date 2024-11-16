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

    public void scheduleAppointment(Patient patient, String doctorId, Date dateTime) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Appointment appointment = new Appointment(generateAppointmentID(), patient.getUserID(), doctorId, dateTime);
       //addappointment in doctor class is NOT being implemented because doctors can directly access their appointments through appointment outcome records
        // doctor.addAppointment(appointment);
        outcomeRecord.addOutcome(appointment);
        System.out.println("Appointment scheduled.");
    }

// !!!!!!!!!!!!! cancellation has to update doctors availability !!!!!!!!!!!!!!!!!!!
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
    public boolean rescheduleAppointment(String appointmentID, Date newDateTime) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentID);
        if (appointment != null) {
            Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());
            /*
            // Assuming we have a method in Doctor to check availability for a new date/time
            if (doctor.isAvailable(newDateTime)) {
                appointment.setDateTime(newDateTime);  // Update the date and time
                System.out.println("Appointment rescheduled to " + newDateTime);
                return true;
            } else {
                System.out.println("The doctor is not available at the requested time.");
                return false;
            } */
            return true; // returning for the sake of running program - delete after compiling
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
