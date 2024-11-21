import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private String id;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private String consultationNotes;
    private TypeOfService typeOfService;
    private List<Prescription> prescriptions;

    // Constructor
    public Appointment(String id, String patientID, String doctorID, LocalDateTime dateTime) {
        this.id = id;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.REQUESTED; // Default status
        this.prescriptions = new ArrayList<>();
    }

    /**
     * @return String
     */
    // Getters
    public String getId() {
        return id;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public TypeOfService getTypeOfService() {
        return typeOfService;
    }

    // Setter for status
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    // Setter for consultation notes
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    // Setter for type of service
    public void setTypeOfService(TypeOfService typeOfService) {
        this.typeOfService = typeOfService;
    }

    // Setter for Date & Time
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // Method to add a prescription
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    // toString method for displaying appointment details
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
