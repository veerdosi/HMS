/**
 * The `PrescriptionService` class provides functionality for managing prescriptions associated with appointments.
 * This includes adding new prescriptions and updating the status of existing prescriptions.
 */
public class PrescriptionService {
    private AppointmentService appointmentService;

    /**
     * Constructs a `PrescriptionService` with the specified `AppointmentService`.
     *
     * @param appointmentService the `AppointmentService` to manage prescriptions within appointments.
     */
    public PrescriptionService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Adds a prescription to a specific appointment.
     *
     * @param appointmentId the ID of the appointment to which the prescription will be added.
     * @param prescription  the `Prescription` object to be added.
     */
    public void addPrescription(String appointmentId, Prescription prescription) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.addPrescription(prescription);
            System.out.println("Prescription added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    /**
     * Updates the status of a prescription for a specific medicine in a given appointment.
     *
     * @param appointmentId the ID of the appointment containing the prescription.
     * @param medicine      the `Medicine` object for which the prescription status will be updated.
     * @param status        the new `PrescriptionStatus` to set.
     * @return `true` if the prescription status was successfully updated, `false` otherwise.
     */
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
