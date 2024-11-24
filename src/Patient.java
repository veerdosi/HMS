<<<<<<< Updated upstream
/**
 * The `Patient` class extends `User` and implements interfaces for accessing
 * medical records, updating personal information, and updating passwords.
 *
 * This class represents a patient with associated personal details and medical records.
 * It provides functionality to manage contact information, access medical records, and update
 * account credentials.
 *
 * The `Patient` class is designed to integrate with systems requiring medical data access,
 * secure updates, and user information management.
 *
 */
public class Patient extends User implements IPatientMedicalRecordAccess, IPersonalInfoUpdate, IPasswordUpdate {
    private String dateOfBirth;
    private MedicalRecord medicalRecord;

    /**
     * Constructs a `Patient` object with the provided parameters, initializing
     * user details and creating a new `MedicalRecord` for the patient.
     *
     * @param userID        The unique identifier for the patient.
     * @param name          The name of the patient.
     * @param password      The patient's password.
     * @param gender        The gender of the patient.
     * @param contactEmail  The contact email address of the patient.
     * @param contactNumber The contact number of the patient.
     * @param dateOfBirth   The date of birth of the patient.
     * @param bloodType     The blood type of the patient.
     */
    public Patient(String userID, String name, String password, String gender, String contactEmail,
                   String contactNumber, String dateOfBirth, String bloodType) {
        super(userID, name, password, UserRole.Patient, gender, contactEmail, contactNumber);
        this.dateOfBirth = dateOfBirth;
        this.medicalRecord = new MedicalRecord(this, bloodType);
    }

    /**
     * Retrieves the date of birth of the patient.
     *
     * @return The date of birth as a `String`.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Allows authorized users to view the medical record of the patient.
     *
     * @param user The user requesting access to the medical record.
     * @return The `MedicalRecord` if access is granted, or `null` if access is denied.
     */
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        return medicalRecord.viewMedicalRecord(user);
    }

    /**
     * Updates the contact number of the patient.
     *
     * @param newContact The new contact number to set.
     */
    @Override
    public void updateContactNumber(String newContact) {
        this.contactNumber = newContact;
    }

    /**
     * Updates the email address of the patient.
     *
     * @param newEmail The new email address to set.
     */
    @Override
    public void updateEmailAddress(String newEmail) {
        this.contactEmail = newEmail;
    }

    /**
     * Updates both the contact email and contact number of the patient.
     *
     * @param newEmail   The new email address to set.
     * @param newContact The new contact number to set.
     */
    @Override
    public void updateContactInfo(String newEmail, String newContact) {
        this.contactEmail = newEmail;
        this.contactNumber = newContact;
    }

    /**
     * Retrieves the medical record associated with the patient.
     *
     * @return The `MedicalRecord` of the patient.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Updates the password for the patient and persists the change to an external data source.
     *
     * @param newPass The new password to set.
     */
    @Override
    public void changePassword(String newPass) {
        this.password = newPass;
        updatePasswordInExcel(); // Ensures the CSV file storing passwords is updated
    }
}
=======
/**
 * @author Revathi Selvasevaran
 *         The `Patient` class extends `User` and implements interfaces for
 *         accessing
 *         medical records and
 *         updating personal information.
 */
public class Patient extends User implements IPatientMedicalRecordAccess, IPersonalInfoUpdate, IPasswordUpdate {
    private String dateOfBirth;
    private MedicalRecord medicalRecord;

    // This constructor method is
    // initializing a new `Patient` object with the provided parameters. It calls
    // the constructor of
    // the superclass `User` using `super(userID, name, password, UserRole.PATIENT,
    // gender,
    // contactEmail, contactNumber)` to set up the user-related information.
    public Patient(String userID, String name, String password, String gender, String contactEmail,
            String contactNumber,
            String dateOfBirth, String bloodType) {
        super(userID, name, password, UserRole.Patient, gender, contactEmail, contactNumber);
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
    // This is implementing the `viewMedicalRecord` method from the
    // `IPatientMedicalRecordAccess` interface in the `Patient` class.
    // This methodallows a user to view the medical record of a patient.
    // It takes a `User` object as a parameter and returns the
    // medical record associated with the patient.
    // The actual implementation of how the medical record
    // is viewed is delegated to the `MedicalRecord` class, where the
    // `viewMedicalRecord` method is called with the user object passed as an
    // argument.
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

    /**
     * @param newPass
     */
    @Override
    // The `changePassword` method in the `Patient` class is responsible for
    // updating the password of a
    // patient object. It takes a new password as a parameter (`newPass`) and
    // assigns this new password
    // to the `password` attribute of the patient object. Additionally, it calls the
    // `updatePasswordInExcel` method to ensure that the changes are reflected in an
    // Excel CSV file,
    // presumably where user information is stored. This method helps in maintaining
    // the security and
    // integrity of patient data by allowing password updates and ensuring that the
    // changes are
    // persisted in an external data source.
    public void changePassword(String newPass) {
        this.password = newPass;
        updatePasswordInExcel(); // Ensures CSV file is updated
    }
}
>>>>>>> Stashed changes
