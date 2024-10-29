package actual;

import java.time.LocalDateTime;
import java.util.ArrayList; // Ensure ArrayList is imported
import java.util.List;

public class AppointmentOutcomeRecord {
    private String appointmentID;
    private LocalDateTime date; // Using LocalDateTime for date and time
    private String serviceType;
    private String consultationNotes;
    private List<Prescription> prescriptions;
    private String doctorID; // Added doctorID attribute

    // Constructor
    public AppointmentOutcomeRecord(String appointmentID, LocalDateTime date, String serviceType, String doctorID) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.serviceType = serviceType;
        this.consultationNotes = "";
        this.prescriptions = new ArrayList<>(); // Initialize the prescriptions list
        this.doctorID = doctorID; // Initialize doctorID
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public String getDoctorID() {
        return doctorID; // Getter for doctorID
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID; // Setter for doctorID
    }

    // Method to add a prescription
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    // Method to update consultation notes
    public void updateNotes(String notes) {
        this.consultationNotes = notes;
    }
}
