package HMSpkg;
import java.util.Date;



public class Patient extends User implements PatientMedicalRecordAccess, PersonalInfoUpdate/*, AppointmentSchedling*/ {
    private Date dateOfBirth;
    private MedicalRecord medicalRecord;

    public Patient(String userID, String password, String name, String contactNumber, String email,
                   Date dateOfBirth, String gender, String bloodType) {
        super(userID, password, name,contactNumber, email, UserRole.PATIENT, gender);
        this.dateOfBirth = dateOfBirth;
        this.medicalRecord = new MedicalRecord(userID, bloodType);
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    // Utility method to get date of birth as formatted String
    public String getFormattedDateOfBirth() {
        // Assuming a date format (e.g., "dd-MM-yyyy")
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(dateOfBirth);
    }

    // Implementing PatientMedicalRecordAccess method for view-only access to MedicalRecord
    @Override
    public MedicalRecord viewMedicalRecord(User user) {
        if (this.userID.equals(user.userID)) {
            medicalRecord.displayRecord(); // View-only access to the medical record
            return medicalRecord;
        } else {
            System.out.println("Access denied: Cannot view records of other patients.");
            return null;
        }
    }

    // Implementing PersonalInfoUpdate methods to allow patients to update contact info
    @Override
    public void updateContactNumber(String newContact) {
        this.contactNumber = newContact;
        System.out.println("Contact number updated successfully.");
    }

    @Override
    public void updateEmailAddress(String newEmail) {
        this.email = newEmail;
        System.out.println("Email address updated successfully.");
    }

    @Override
    public void updateContactInfo(String newEmail, String newContact) {
        this.email = newEmail;
        this.contactNumber = newContact;
        System.out.println("Email address updated successfully.");
    }

    @Override
    public boolean changePassword(String oldPass, String newPass){
        if(this.password.equals(oldPass)){
            this.password = newPass;
            return true;
        }
        else{
            System.out.println("Incorrect Password Entered, Please Try Again.");
            return false;
        }
    }
}
