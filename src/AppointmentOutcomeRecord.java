<<<<<<< Updated upstream
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `AppointmentOutcomeRecord` class manages the records of past
 * appointments.
 * It is implemented as a Singleton to ensure only one instance exists
 * throughout the application.
 * The class provides methods to add, retrieve, and display appointment
 * outcomes.
 */
public class AppointmentOutcomeRecord {
    private static AppointmentOutcomeRecord instance;
    private List<Appointment> pastAppointments;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the list to store past appointments.
     */
    private AppointmentOutcomeRecord() {
        pastAppointments = new ArrayList<>();
    }

    /**
     * Gets the Singleton instance of `AppointmentOutcomeRecord`.
     *
     * @return The single instance of `AppointmentOutcomeRecord`.
     */
    public static AppointmentOutcomeRecord getInstance() {
        if (instance == null) {
            instance = new AppointmentOutcomeRecord();
        }
        return instance;
    }

    /**
     * Adds a new appointment to the record of past appointments.
     *
     * @param appointment The appointment to add.
     */
    public void addOutcome(Appointment appointment) {
        // Remove any existing appointment with the same ID
        pastAppointments.removeIf(apt -> apt.getId().equals(appointment.getId()));
        // Add the updated appointment
        pastAppointments.add(appointment);
    }

    /**
     * Retrieves all past appointments.
     *
     * @return A list of all past appointments.
     */
    public List<Appointment> getAllOutcomes() {
        return pastAppointments;
    }

    /**
     * Retrieves an appointment by its unique ID.
     *
     * @param appointmentId The unique identifier of the appointment.
     * @return The appointment with the specified ID, or `null` if not found.
     */
    public Appointment getAppointmentById(String appointmentId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all appointments associated with a specific patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return A list of appointments for the specified patient.
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getPatientID().equals(patientId))
                .collect(Collectors.toList());
    }

    /**
     * @param patientId
     * @return List<Appointment>
     */
    public List<Appointment> getCompletedAppointmentsByPatient(String patientId) {
        return getAppointmentsByPatient(patientId).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public void displayCompletedAppointmentsByPatient(String patientId) {
        // Get completed appointments for the patient
        List<Appointment> completedAppointments = getCompletedAppointmentsByPatient(patientId);

        // Check if the list is null or empty
        if (completedAppointments == null || completedAppointments.isEmpty()) {
            System.out.println("No completed appointments found for Patient ID: " + patientId);
            return;
        }

        // Define a date-time formatter for consistent display
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Print the title
        System.out.println("Completed Appointments for Patient ID: " + patientId);

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type",
                "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : completedAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();

            String dateTimeString = appointment.getDateTime() != null
                    ? appointment.getDateTime().format(dateTimeFormatter)
                    : "N/A";

            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateTimeString,
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }

    /**
     * Retrieves all appointments associated with a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of appointments for the specified doctor.
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getDoctorID().equals(doctorId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getRequestedAppointmentsByDoctor(String doctorId) {
        return getAppointmentsByDoctor(doctorId).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.REQUESTED)
                .collect(Collectors.toList());
    }

    public List<Appointment> getConfirmedAppointmentsByDoctor(String doctorId) {
        return getAppointmentsByDoctor(doctorId).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    public void displayConfirmedAppointmentsByDoctor(String doctorId) {
        // Get confirmed appointments for the doctor
        List<Appointment> confirmedAppointments = getConfirmedAppointmentsByDoctor(doctorId);

        // Check if the list is null or empty
        if (confirmedAppointments == null || confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found for Doctor ID: " + doctorId);
            return;
        }

        // Define a date-time formatter for consistent display
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Print the title
        System.out.println("Confirmed Appointments for Doctor ID: " + doctorId);

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type",
                "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : confirmedAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();

            String dateTimeString = appointment.getDateTime() != null
                    ? appointment.getDateTime().format(dateTimeFormatter)
                    : "N/A";

            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateTimeString,
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }

    /**
     * Displays all past appointments in a formatted table. If no appointments
     * are available, a message indicating this is shown.
     */
    public void displayAllAppointments() {
        // Check if pastAppointments is null or empty
        if (pastAppointments == null || pastAppointments.isEmpty()) {
            System.out.println("No appointments found to display.");
            return;
        }

        // Define a date-time formatter for consistent display
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type",
                "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : pastAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();

            String dateTimeString = appointment.getDateTime() != null
                    ? appointment.getDateTime().format(dateTimeFormatter)
                    : "N/A";

            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateTimeString,
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }
}
=======
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `AppointmentOutcomeRecord` class manages the records of past
 * appointments.
 * It is implemented as a Singleton to ensure only one instance exists
 * throughout the application.
 * The class provides methods to add, retrieve, and display appointment
 * outcomes.
 */
public class AppointmentOutcomeRecord {
    private static AppointmentOutcomeRecord instance;
    private List<Appointment> pastAppointments;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the list to store past appointments.
     */
    private AppointmentOutcomeRecord() {
        pastAppointments = new ArrayList<>();
    }

    /**
     * Gets the Singleton instance of `AppointmentOutcomeRecord`.
     *
     * @return The single instance of `AppointmentOutcomeRecord`.
     */
    public static AppointmentOutcomeRecord getInstance() {
        if (instance == null) {
            instance = new AppointmentOutcomeRecord();
        }
        return instance;
    }

    /**
     * Adds a new appointment to the record of past appointments.
     *
     * @param appointment The appointment to add.
     */
    public void addOutcome(Appointment appointment) {
        // Remove any existing appointment with the same ID
        pastAppointments.removeIf(apt -> apt.getId().equals(appointment.getId()));
        // Add the updated appointment
        pastAppointments.add(appointment);
    }

    /**
     * Retrieves all past appointments.
     *
     * @return A list of all past appointments.
     */
    public List<Appointment> getAllOutcomes() {
        return pastAppointments;
    }

    /**
     * Retrieves an appointment by its unique ID.
     *
     * @param appointmentId The unique identifier of the appointment.
     * @return The appointment with the specified ID, or `null` if not found.
     */
    public Appointment getAppointmentById(String appointmentId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all appointments associated with a specific patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return A list of appointments for the specified patient.
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getPatientID().equals(patientId))
                .collect(Collectors.toList());
    }

    /**
     * @param patientId
     * @return List<Appointment>
     */
    public List<Appointment> getCompletedAppointmentsByPatient(String patientId) {
        return getAppointmentsByPatient(patientId).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public void displayCompletedAppointmentsByPatient(String patientId) {
        // Get completed appointments for the patient
        List<Appointment> completedAppointments = getCompletedAppointmentsByPatient(patientId);

        // Check if the list is null or empty
        if (completedAppointments == null || completedAppointments.isEmpty()) {
            System.out.println("No completed appointments found for Patient ID: " + patientId);
            return;
        }

        // Define a date-time formatter for consistent display
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Print the title
        System.out.println("Completed Appointments for Patient ID: " + patientId);

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type",
                "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : completedAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();

            String dateTimeString = appointment.getDateTime() != null
                    ? appointment.getDateTime().format(dateTimeFormatter)
                    : "N/A";

            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateTimeString,
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }

    /**
     * Retrieves all appointments associated with a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of appointments for the specified doctor.
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return pastAppointments.stream()
                .filter(appointment -> appointment.getDoctorID().equals(doctorId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getRequestedAppointmentsByDoctor(String doctorId) {
        return getAppointmentsByDoctor(doctorId).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.REQUESTED)
                .collect(Collectors.toList());
    }

    public List<Appointment> getConfirmedAppointmentsByDoctor(String doctorId) {
        return getAppointmentsByDoctor(doctorId).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    public void displayConfirmedAppointmentsByDoctor(String doctorId) {
        // Get confirmed appointments for the doctor
        List<Appointment> confirmedAppointments = getConfirmedAppointmentsByDoctor(doctorId);

        // Check if the list is null or empty
        if (confirmedAppointments == null || confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found for Doctor ID: " + doctorId);
            return;
        }

        // Define a date-time formatter for consistent display
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Print the title
        System.out.println("Confirmed Appointments for Doctor ID: " + doctorId);

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type",
                "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : confirmedAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();

            String dateTimeString = appointment.getDateTime() != null
                    ? appointment.getDateTime().format(dateTimeFormatter)
                    : "N/A";

            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateTimeString,
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }

    /**
     * Displays all past appointments in a formatted table. If no appointments
     * are available, a message indicating this is shown.
     */
    public void displayAllAppointments() {
        // Check if pastAppointments is null or empty
        if (pastAppointments == null || pastAppointments.isEmpty()) {
            System.out.println("No appointments found to display.");
            return;
        }

        // Define a date-time formatter for consistent display
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Print the table header
        System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                "Appointment ID", "Patient ID", "Doctor ID", "Date & Time", "Status", "Service Type",
                "Consultation Notes", "Prescriptions"));

        // Print each appointment as a row
        for (Appointment appointment : pastAppointments) {
            String prescriptions = appointment.getPrescriptions().isEmpty()
                    ? "None"
                    : appointment.getPrescriptions().toString();

            String dateTimeString = appointment.getDateTime() != null
                    ? appointment.getDateTime().format(dateTimeFormatter)
                    : "N/A";

            System.out.println(String.format("%-15s %-15s %-15s %-20s %-10s %-15s %-20s %-30s",
                    appointment.getId(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    dateTimeString,
                    appointment.getStatus(),
                    (appointment.getTypeOfService() != null ? appointment.getTypeOfService() : "N/A"),
                    (appointment.getConsultationNotes() != null ? appointment.getConsultationNotes() : "N/A"),
                    prescriptions));
        }
    }
}
>>>>>>> Stashed changes
