public class Patient extends User {
    private String contactInfo;
    private String dateOfBirth;
    private String bloodType;

    public void requestAppointment(String doctorId, Date dateTime) {
        AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(null, null);
        facade.scheduleAppointment(this, doctorId, dateTime);
        System.out.println("Appointment requested with Doctor ID: " + doctorId + " at " + dateTime);
    }

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
