// File: Prescription.java

public class Prescription {
    private String prescriptionID;
    private String medicationID;
    private String patientID;
    private String prescriptionStatus; // e.g., "pending", "dispensed"
    private String date;

    // Constructor
    public Prescription(String prescriptionID, String medicationID, String patientID, String prescriptionStatus, String date) {
        this.prescriptionID = prescriptionID;
        this.medicationID = medicationID;
        this.patientID = patientID;
        this.prescriptionStatus = prescriptionStatus;
        this.date = date;
    }

    // Getters
    public String getPrescriptionID() {
        return prescriptionID;
    }

    public String getMedicationID() {
        return medicationID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public String getDate() {
        return date;
    }

    // Setter to update prescription status
    public void setPrescriptionStatus(String status) {
        this.prescriptionStatus = status;
    }

    // Display prescription details
    public void displayPrescriptionInfo() {
        System.out.println("Prescription ID: " + prescriptionID + ", Medication ID: " + medicationID 
                + ", Patient ID: " + patientID + ", Status: " + prescriptionStatus + ", Date: " + date);
    }
}
