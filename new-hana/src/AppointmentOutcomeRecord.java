import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentOutcomeRecord {
    private static AppointmentOutcomeRecord instance;
    private List<Appointment> pastAppointments;

    // Private constructor for Singleton pattern
    private AppointmentOutcomeRecord() {
        pastAppointments = new ArrayList<>();
    }

    // Singleton instance getter
    public static AppointmentOutcomeRecord getInstance() {
        if (instance == null) {
            instance = new AppointmentOutcomeRecord();
        }
        return instance;
    }

    // Adds a new appointment outcome
    public void addOutcome(Appointment appointment) {
        pastAppointments.add(appointment);
    }

    // Retrieves all appointment outcomes
    public List<Appointment> getAllOutcomes() {
        return pastAppointments;
    }

    // Retrieves an appointment by its ID
    public Appointment getAppointmentById(String appointmentId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    // Retrieves all appointments for a specific patient by their ID
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getId().equals(patientId))
                .collect(Collectors.toList());
    }

    // Retrieves all appointments for a specific doctor by their ID
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getDoctorID().equals(doctorId))
                .collect(Collectors.toList());
    }

    // Displays all appointment details in a tabular format
    public void displayAllAppointments() {
        // Define a date format for consistent display
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type", "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : pastAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();
            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateFormat.format(appointment.getDateTime()),
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }
}
