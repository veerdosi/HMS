public class ViewMedicalRecordCommand implements DoctorCommand {
    private Doctor doctor;
    private Patient patient;

    public ViewMedicalRecordCommand(Doctor doctor, Patient patient) {
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public void execute() {
        doctor.viewMedicalRecord(patient);
    }
}