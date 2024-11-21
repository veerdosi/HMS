import java.util.ArrayList;
import java.util.List;

//COMPLETE
public class MedicalRecord implements IPatientMedicalRecordAccess {
    private Patient patient;
    private String bloodType;
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;

    public MedicalRecord(Patient patient, String bloodType) {
        this.patient = patient;
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.pastTreatments = new ArrayList<>();
    }

    /**
     * @return String
     */
    // Getters for read-only access
    public String getBloodType() {
        return bloodType;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<String> getPastDiagnoses() {
        return new ArrayList<>(pastDiagnoses); // Returns a copy for immutability
    }

    public List<String> getPastTreatments() {
        return new ArrayList<>(pastTreatments);
    }

    // Methods for doctors to modify diagnoses and treatments
    public void addDiagnosis(String diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    public void addTreatment(String treatment) {
        pastTreatments.add(treatment);
    }

    // Method to display the record (for viewing by patients)
    public void displayRecord() {
        System.out.println("Patient ID: " + patient.getUserID());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Info: " + patient.getContactNumber() + " | " + patient.getContactEmail());
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Past Diagnoses: " + pastDiagnoses);
        System.out.println("Past Treatments: " + pastTreatments);
    }

    // Implementing the viewMedicalRecord method for authorized users (patients or
    // doctors)
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        // Assuming a user role check or similar mechanism is implemented elsewhere
        if (user.getUserID().equals(patient.getUserID()) || user.getRole() == UserRole.DOCTOR) {
            displayRecord(); // Show the record for patients or doctors
            return this;
        } else {
            System.out.println("Access denied: You cannot view this record.");
            return null;
        }
    }

}
