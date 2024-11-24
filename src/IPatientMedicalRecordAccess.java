/**
 * The `IPatientMedicalRecordAccess` interface provides a contract for accessing
 * medical records of patients.
 *
 * Implementing classes are expected to define the logic for retrieving a patient's
 * medical record based on the requesting user.
 *
 */
public interface IPatientMedicalRecordAccess {

    /**
     * Retrieves the medical record for a specified user.
     *
     * @param user The user requesting access to the medical record.
     * @return The `MedicalRecord` object if access is granted, or `null` if access is denied.
     */
    MedicalRecord viewMedicalRecord(User user);
}
