import java.util.Date;

/**
 * The `Prescription` class represents a medical prescription, including details about the prescribed medicine, 
 * its current status, and the date it was prescribed.
 */
public class Prescription {
    private Medicine medicine; // Medicine object associated with the prescription
    private PrescriptionStatus status; // Status of the prescription (e.g., "Pending", "Dispensed")
    private Date prescribedDate; // Date the prescription was added

    /**
     * Constructs a `Prescription` with the specified `Medicine` object.
     * The initial status of the prescription is set to `PENDING`, and the prescribed date is set to the current date.
     *
     * @param medicine the `Medicine` object associated with this prescription.
     */
    public Prescription(Medicine medicine) {
        this.medicine = medicine;
        this.status = PrescriptionStatus.PENDING; // Default status
        this.prescribedDate = new Date();
    }

    /**
     * Retrieves the medicine associated with this prescription.
     *
     * @return the `Medicine` object.
     */
    public Medicine getMedicine() {
        return medicine;
    }

    /**
     * Retrieves the current status of this prescription.
     *
     * @return the `PrescriptionStatus` of the prescription.
     */
    public PrescriptionStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of this prescription.
     *
     * @param status the new `PrescriptionStatus` to set.
     */
    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the prescription, including the medicine name, status, and prescribed date.
     *
     * @return a string representation of the prescription.
     */
    @Override
    public String toString() {
        return "Prescription[Medicine=" + medicine.getName() + 
               ", Status=" + status + 
               ", Date=" + prescribedDate + "]";
    }
}
