
public class PrescriptionService {
    private AppointmentService appointmentService;

    public PrescriptionService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * @param appointmentId
     * @param prescription
     */
    // Add a prescription to an appointment
    public void addPrescription(String appointmentId, Prescription prescription) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.addPrescription(prescription);
            System.out.println("Prescription added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    // Update prescription status for a specific appointment
    public boolean updatePrescriptionStatus(String appointmentId, Medicine medicine, PrescriptionStatus status) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment != null) {
            for (Prescription prescription : appointment.getPrescriptions()) {
                if (prescription.getMedicine().getName().equals(medicine.getName())) {
                    prescription.setStatus(status);
                    System.out.println("Prescription status updated to " + status + " for " + medicine.getName());
                    return true;
                }
            }
            System.out.println("Prescription not found for medicine: " + medicine.getName());
            return false;
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
            return false;
        }
    }
}
