import java.util.ArrayList;
import java.util.List;

/**@author Revathi Selvasevaran
 * The `MedicalRecord` class represents a patient's medical history, including past diagnoses,
 * treatments, and blood type. It provides methods for viewing and updating medical records.
 */
public class MedicalRecord implements IPatientMedicalRecordAccess {
    private Patient patient;
    private String bloodType;
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;

    /**
     * Constructs a `MedicalRecord` for a specific patient.
     *
     * @param patient   the patient to whom this medical record belongs.
     * @param bloodType the blood type of the patient.
     */
    public MedicalRecord(Patient patient, String bloodType) {
        this.patient = patient;
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.pastTreatments = new ArrayList<>();
    }

    /**
     * Retrieves the patient's blood type.
     *
     * @return the blood type of the patient.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Retrieves the patient associated with this medical record.
     *
     * @return the `Patient` object.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Retrieves a list of the patient's past diagnoses.
     *
     * @return a copy of the list of past diagnoses.
     */
    public List<String> getPastDiagnoses() {
        return new ArrayList<>(pastDiagnoses);
    }

    /**
     * Retrieves a list of the patient's past treatments.
     *
     * @return a copy of the list of past treatments.
     */
    public List<String> getPastTreatments() {
        return new ArrayList<>(pastTreatments);
    }

    /**
     * Adds a new diagnosis to the patient's medical record.
     *
     * @param diagnosis the diagnosis to add.
     */
    public void addDiagnosis(String diagnosis) {
        pastDiagnoses.add(diagnosis);
    }

    /**
     * Adds a new treatment to the patient's medical record.
     *
     * @param treatment the treatment to add.
     */
    public void addTreatment(String treatment) {
        pastTreatments.add(treatment);
    }

    /**
     * Displays the patient's medical record in a formatted manner.
     */
    public void displayRecord() {
        System.out.println("------ Medical Record ------");
        System.out.println("Name: " + patient.getName());
        System.out.println("Patient ID: " + patient.getUserID());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Contact Info: " + patient.getContactNumber() + " | " + patient.getContactEmail());
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Past Diagnoses: " + pastDiagnoses);
        System.out.println("Past Treatments: " + pastTreatments);
        System.out.println();
    }

    /**
     * Allows authorized users (e.g., doctors or the patient) to view the medical record.
     *
     * @param user the user attempting to view the record.
     * @return the `MedicalRecord` if access is granted, or `null` if access is denied.
     */
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        if (user.getUserID().equals(patient.getUserID()) || user.getRole() == UserRole.Doctor) {
            displayRecord();
            return this;
        } else {
            System.out.println("Access denied: You cannot view this record.");
            return null;
        }
    }
}
