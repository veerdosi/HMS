/**
 * The `IMedicalRecordManagement` interface defines operations for managing patient medical records,
 * allowing authorized users (like doctors) to view and update records.
 */
public interface IDoctorMedicalRecordAccess {

    /**
     * Allows authorized users to view the medical record of a patient associated with a specific appointment.
     *
     * @param appointment The appointment whose patient's medical record needs to be viewed.
     * @return The `MedicalRecord` of the patient, or `null` if access is denied.
     */
    MedicalRecord viewMedicalRecord(Appointment appointment);

    /**
     * Allows authorized users to update the medical record of a patient associated with a specific appointment.
     *
     * @param appointment The appointment whose patient's medical record needs to be updated.
     * @param diagnosis   The new diagnosis to add to the medical record.
     * @param treatment   The new treatment to add to the medical record.
     * @return `true` if the medical record was updated successfully, `false` otherwise.
     */
    boolean updateMedicalRecord(Appointment appointment, String diagnosis, String treatment);
}
