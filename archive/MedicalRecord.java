package archive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicalRecord {
    private String recordID;
    private Date recordDate;
    private String diagnosis;
    private String treatment;
    private List<Prescription> prescriptions;

    public MedicalRecord(String diagnosis, String treatment) {
        this.recordID = generateRecordID();
        this.recordDate = new Date();
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescriptions = new ArrayList<>();
    }

    private String generateRecordID() {
        return "REC-" + System.currentTimeMillis();
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public void updateDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
