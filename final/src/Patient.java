/**
 * The `Patient` class extends `User` and implements interfaces for accessing
 * medical records and
 * updating personal information.
 */
public class Patient extends User implements IPatientMedicalRecordAccess, IPersonalInfoUpdate {
    private String dateOfBirth;
    private MedicalRecord medicalRecord;

    // This constructor method `public Patient(String userID, String name, String
    // password, String
    // gender, String contactEmail, String contactNumber, String dateOfBirth, String
    // bloodType)` is
    // initializing a new `Patient` object with the provided parameters. It calls
    // the constructor of
    // the superclass `User` using `super(userID, name, password, UserRole.PATIENT,
    // gender,
    // contactEmail, contactNumber)` to set up the user-related information.
    public Patient(String userID, String name, String password, String gender, String contactEmail,
            String contactNumber,
            String dateOfBirth, String bloodType) {
        super(userID, name, password, UserRole.PATIENT, gender, contactEmail, contactNumber);
        this.dateOfBirth = dateOfBirth;
        this.medicalRecord = new MedicalRecord(this, bloodType);
    }

    /**
     * The function `getDateOfBirth` in Java returns the date of birth as a string.
     * 
     * @return The method `getDateOfBirth` is returning the date of birth as a
     *         String.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param user
     * @return MedicalRecord
     */
    // The code snippet you provided is implementing the `viewMedicalRecord` method
    // from the
    // `IPatientMedicalRecordAccess` interface in the `Patient` class. This method
    // allows a user to
    // view the medical record of a patient. It takes a `User` object as a parameter
    // and returns the
    // medical record associated with the patient. The actual implementation of how
    // the medical record
    // is viewed is delegated to the `MedicalRecord` class, where the
    // `viewMedicalRecord` method is
    // called with the user object passed as an argument.
    // Implementing PatientMedicalRecordAccess method for view-only access to
    // MedicalRecord
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        return medicalRecord.viewMedicalRecord(user);
    }

    /**
     * The updateContactNumber method updates the contact number of an object and
     * prints a success
     * message.
     * 
     * @param newContact The `newContact` parameter in the `updateContactNumber`
     *                   method represents the
     *                   new contact number that will be assigned to the
     *                   `contactNumber` attribute of the object. When
     *                   this method is called, the `contactNumber` attribute of the
     *                   object will be updated with the
     *                   value provided in the `new
     */
    @Override
    public void updateContactNumber(String newContact) {
        this.contactNumber = newContact;
    }

    /**
     * The updateEmailAddress method updates the contact's email address and prints
     * a success message.
     * 
     * @param newEmail The `newEmail` parameter in the `updateEmailAddress` method
     *                 represents the new
     *                 email address that will be assigned to the `contactEmail`
     *                 field of the object. When this method
     *                 is called with a new email address as an argument, it updates
     *                 the `contactEmail` field with the
     *                 new email address
     */
    @Override
    public void updateEmailAddress(String newEmail) {
        this.contactEmail = newEmail;
    }

    /**
     * The updateContactInfo method updates the contact email and contact number
     * fields of an object
     * and prints a success message.
     * 
     * @param newEmail   The `newEmail` parameter in the `updateContactInfo` method
     *                   represents the new
     *                   email address that will be assigned to the `contactEmail`
     *                   field of the object.
     * @param newContact The `newContact` parameter in the `updateContactInfo`
     *                   method is a `String`
     *                   type that represents the new contact number that will be
     *                   updated in the contact information.
     */
  
    @Override
    public void updateContactInfo(String newEmail, String newContact) {
        this.contactEmail = newEmail;
        this.contactNumber = newContact;
    }

    /**
     * This Java function returns the medical record associated with the Patient.
     * 
     * @return An instance of the `MedicalRecord` class is being returned.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

}
