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
}
