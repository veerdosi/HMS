//COMPLETE

public class Patient extends User implements IPatientMedicalRecordAccess,IPersonalInfoUpdate{
    private String dateOfBirth;
    private MedicalRecord medicalRecord;
    
    public Patient(String userID, String name, String password, UserRole role, String gender, String contactEmail, String contactNumber, 
                   String dateOfBirth, String bloodType) {
        super(userID, name, password, UserRole.PATIENT, gender, contactEmail, contactNumber);
        this.dateOfBirth = dateOfBirth;
        this.medicalRecord = new MedicalRecord(userID, bloodType);
        //AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);
    }
/*
    public void requestAppointment(String doctorId, Date dateTime) {
        AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(null, null);
        facade.scheduleAppointment(this, doctorId, dateTime);
        System.out.println("Appointment requested with Doctor ID: " + doctorId + " at " + dateTime);
    }
*/

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    // Implementing PatientMedicalRecordAccess method for view-only access to MedicalRecord
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        if (this.userID.equals(user.userID)) {
            medicalRecord.displayRecord();
            return medicalRecord;
        } else {
            System.out.println("Access denied: Cannot view records of other patients.");
            return null;
        }
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

    @Override
    public boolean changePassword(String oldPass, String newPass) {
        if (this.password.equals(oldPass)) {
            this.password = newPass;
            System.out.println("Password changed successfully.");
            return true;
        } else {
            System.out.println("Incorrect password entered. Please try again.");
            return false;
        }
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }
}
