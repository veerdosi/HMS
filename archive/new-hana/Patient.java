public class Patient extends User {
    private String contactInfo;
    private String dateOfBirth;
    private String bloodType;

    public Patient(String userID, String name, String password, String contactInfo ,
                   String dateOfBirth, String gender, String bloodType) {
        super(userID, name, password, UserRole.PATIENT, gender);
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
