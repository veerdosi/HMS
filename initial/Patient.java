public class Patient extends User {
    private String dateOfBirth;
    private String bloodType;

    public Patient(String userID, String password, String contactNumber, String email,
                   String dateOfBirth, String gender, String bloodType) {
        super(userID, password, contactNumber, email, UserRole.PATIENT, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBloodType() {
        return bloodType;
    }
}
