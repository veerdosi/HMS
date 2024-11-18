public class Patient extends User {
    private String contactInfo;
    private String dateOfBirth;
    private String bloodType;

    public Patient(String userID, String name, String password, String gender, String contactEmail, String contactNumber, String dateOfBirth, String bloodType) {
        super(userID, name, password, UserRole.PATIENT, gender, contactEmail, contactNumber);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        //AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBloodType() {
        return bloodType;
    }

}
