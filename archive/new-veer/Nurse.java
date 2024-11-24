public class Nurse extends User {
    public Nurse(String userID, String name, UserRole role, String gender, String contactEmail, String contactNumber) {
        super(userID, name, DEFAULT_PASSWORD, role, gender, contactEmail, contactNumber); // Pass default password
    }

    /**
     * @param patientId
     * @param vitals
     */
    public void recordPatientVitals(String patientId, String vitals) {
        System.out.println("Recording vitals for patient ID " + patientId + ": " + vitals);
        // Logic to store vitals in the patient record
    }

    public void prepareForAppointment(String appointmentId) {
        System.out.println("Preparing for appointment ID " + appointmentId);
        // Logic for preparation, e.g., checking room readiness or ensuring required
        // equipment
    }
}