public class UpdateMedicalRecordRequest {
    private Patient patient;
    private String diagnosis;
    private String treatmentPlan;
    private Prescription prescription;

    public UpdateMedicalRecordRequest(Patient patient, String diagnosis, String treatmentPlan,
            Prescription prescription) {
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.prescription = prescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public Prescription getPrescription() {
        return prescription;
    }
}