import java.util.Date;

public class Prescription {
    private Medicine medicine;       // Medicine object associated with the prescription
    private PrescriptionStatus status;           // Status of the prescription (e.g., "Pending", "Dispensed")
    private Date prescribedDate;     // Date the prescription was added

    public Prescription(Medicine medicine) {
        this.medicine = medicine;
        this.status = PrescriptionStatus.PENDING;  // Default status
        this.prescribedDate = new Date();
    }

    public Medicine getMedicine() { return medicine; }
    public PrescriptionStatus getStatus() { return status; }
    public void setStatus(PrescriptionStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Prescription[Medicine=" + medicine.getName() + ", Status=" + status + ", Date=" + prescribedDate + "]";
    }
}
