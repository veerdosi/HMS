/**
 * The `IPatientMedicalRecordAccess` interface provides a contract for accessing
 * medical records of patients.
 */
public interface IPatientMedicalRecordAccess {
    /**
     * Retrieves the medical record for a specified user.
     *
     * @param user The user requesting the medical record.
     * @return The `MedicalRecord` object if access is granted, or `null` otherwise.
     */
    MedicalRecord viewMedicalRecord(User user);
}
