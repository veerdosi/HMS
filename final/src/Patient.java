//COMPLETE

public class Patient extends User implements IPatientMedicalRecordAccess,IPersonalInfoUpdate{
    private String dateOfBirth;
    private MedicalRecord medicalRecord;
    
    public Patient(String userID, String name, String password, String gender, String contactEmail, String contactNumber, 
                   String dateOfBirth, String bloodType) {
        super(userID, name, password, UserRole.PATIENT, gender, contactEmail, contactNumber);
        this.dateOfBirth = dateOfBirth;
        this.medicalRecord = new MedicalRecord(this, bloodType);
        //AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    // Implementing PatientMedicalRecordAccess method for view-only access to MedicalRecord
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        return medicalRecord.viewMedicalRecord(user);
    }
    
    @Override
    public void updateContactNumber(String newContact) {
        this.contactNumber = newContact;
        System.out.println("Contact number updated successfully.");
    }

    @Override
    public void updateEmailAddress(String newEmail) {
        this.contactEmail = newEmail;
        System.out.println("Email address updated successfully.");
    }

    @Override
    public void updateContactInfo(String newEmail, String newContact) {
        this.contactEmail = newEmail;
        this.contactNumber = newContact;
        System.out.println("Contact information updated successfully.");
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

}
