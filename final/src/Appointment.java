import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The `Appointment` class represents a medical appointment in the system. It includes
 * information about the patient, doctor, date and time, appointment status, consultation
 * notes, type of service, and associated prescriptions.
 */
public class Appointment {
    private String id;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private String consultationNotes;
    private TypeOfService typeOfService;
    private List<Prescription> prescriptions;

    /**
     * Constructs an `Appointment` object with the specified details.
     *
     * @param id        The unique identifier of the appointment.
     * @param patientID The unique identifier of the patient.
     * @param doctorID  The unique identifier of the doctor.
     * @param dateTime  The date and time of the appointment.
     */
    public Appointment(String id, String patientID, String doctorID, LocalDateTime dateTime) {
        this.id = id;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.REQUESTED; // Default status
        this.prescriptions = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the appointment.
     *
     * @return The appointment ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the unique identifier of the patient.
     *
     * @return The patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the unique identifier of the doctor.
     *
     * @return The doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the status of the appointment.
     *
     * @return The appointment status.
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Gets the date and time of the appointment.
     *
     * @return The appointment date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the list of prescriptions associated with the appointment.
     *
     * @return A list of prescriptions.
     */
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Gets the consultation notes for the appointment.
     *
     * @return The consultation notes, or `null` if none exist.
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Gets the type of service for the appointment.
     *
     * @return The type of service, or `null` if not specified.
     */
    public TypeOfService getTypeOfService() {
        return typeOfService;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status The new status to set.
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Sets the consultation notes for the appointment.
     *
     * @param consultationNotes The consultation notes to set.
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Sets the type of service for the appointment.
     *
     * @param typeOfService The type of service to set.
     */
    public void setTypeOfService(TypeOfService typeOfService) {
        this.typeOfService = typeOfService;
    }

    /**
     * Sets the date and time of the appointment.
     *
     * @param dateTime The new date and time to set.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Adds a prescription to the appointment.
     *
     * @param prescription The prescription to add.
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    /**
     * Returns a string representation of the appointment, including its details.
     *
     * @return A string containing the appointment details.
     */
    @Override
    public String toString() {
        return "Appointment ID: " + id +
                "\nPatient ID: " + patientID +
                "\nDoctor ID: " + doctorID +
                "\nDate and Time: " + dateTime +
                "\nStatus: " + status +
                "\nType of Service: " + (typeOfService != null ? typeOfService : "Not specified") +
                "\nConsultation Notes: " + (consultationNotes != null ? consultationNotes : "None") +
                "\nPrescriptions: " + prescriptions;
    }
}
