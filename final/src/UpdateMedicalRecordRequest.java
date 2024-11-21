/**
 * The `UpdateMedicalRecordRequest` class represents a request to update a patient's
 * medical record. It includes details such as the patient, diagnosis, treatment plan,
 * and prescription.
 */
public class UpdateMedicalRecordRequest {
    private Patient patient;
    private String diagnosis;
    private String treatmentPlan;
    private Prescription prescription;

    /**
     * Constructs an `UpdateMedicalRecordRequest` with the specified details.
     *
     * @param patient       The patient whose medical record is to be updated.
     * @param diagnosis     The diagnosis to add to the medical record.
     * @param treatmentPlan The treatment plan to add to the medical record.
     * @param prescription  The prescription to add to the medical record.
     */
    public UpdateMedicalRecordRequest(Patient patient, String diagnosis, String treatmentPlan, Prescription prescription) {
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.prescription = prescription;
    }

    /**
     * @return The patient associated with this request.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * @return The diagnosis associated with this request.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * @return The treatment plan associated with this request.
     */
    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    /**
     * @return The prescription associated with this request.
     */
    public Prescription getPrescription() {
        return prescription;
    }
}
