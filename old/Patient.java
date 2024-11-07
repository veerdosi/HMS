package old;

import java.util.List;

public class Patient extends User {
    private String patientID;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private List<MedicalRecord> medicalHistory;
    private List<Appointment> appointments;

    public Patient(String userID, String password, String name, String contactNumber, String email, String patientID,
            String dateOfBirth, String gender, String bloodType) {
        super(userID, password, name, contactNumber, email, UserRole.PATIENT);
        this.patientID = patientID;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
    }

    public String getPatientID() {
        return patientID;
    }

    // View medical record
    public List<MedicalRecord> viewMedicalRecord() {
        return medicalHistory;
    }

    // Update non-medical personal information
    @Override
    public void updateContactInfo(String email, String contactNumber) {
        super.updateContactInfo(email, contactNumber);
    }

    // Schedule an appointment
    public Appointment requestAppointment(Doctor doctor, TimeSlot timeSlot) {
        Appointment appointment = new Appointment(this, doctor, timeSlot);
        appointments.add(appointment);
        return appointment;
    }

    @Override
    public void performRoleSpecificActions() {
        // Specific actions for a patient, e.g., viewing and managing appointments.
        System.out.println("Performing patient-specific actions.");
    }
}
