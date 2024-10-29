public class Prescription {
    private String prescriptionID;
    private String medicineID;
    private int quantity;
    private PrescriptionStatus status;

    public Prescription(String medicineID, int quantity) {
        this.prescriptionID = generatePrescriptionID();
        this.medicineID = medicineID;
        this.quantity = quantity;
        this.status = PrescriptionStatus.PENDING;
    }

    private String generatePrescriptionID() {
        return "RX-" + System.currentTimeMillis();
    }

    public void updateStatus(PrescriptionStatus status) {
        this.status = status;
    }
}
