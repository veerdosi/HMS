import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Pharmacist extends User {
    private String pharmacistID;
    private MedicineInventory inventory;
    private AppointmentOutcomeRecord outcomeRecord;

    // Constructor
    public Pharmacist(String userID, String name, String password, UserRole role, String gender, String pharmacistID, MedicineInventory inventory, AppointmentOutcomeRecord outcomeRecord) {
        super(userID, name, password, role, gender);
        this.pharmacistID = pharmacistID;
        this.inventory = inventory;
        this.outcomeRecord = outcomeRecord;
    }

    // View Appointment Outcome
    public void viewAppointmentOutcome(String appointmentID) {
        System.out.println("Viewing outcome for Appointment ID: " + appointmentID);
        // Retrieve appointment outcome
        Appointment appointment = outcomeRecord.getAppointmentByID(appointmentID);
        if (appointment != null) {
            System.out.println("Appointment Details: ");
            System.out.println("Patient ID: " + appointment.getPatientID());
            System.out.println("Doctor ID: " + appointment.getDoctorID());
            System.out.println("Prescriptions: ");
            for (Prescription prescription : appointment.getPrescriptions()) {
                System.out.println(prescription);
            }
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // Update Prescription Status
    public void updatePrescriptionStatus(String prescriptionID, PrescriptionStatus status) {
        System.out.println("Updating Prescription ID: " + prescriptionID + " to status: " + status);
        Prescription prescription = outcomeRecord.getPrescriptionByID(prescriptionID);
        if (prescription != null) {
            prescription.setStatus(status);
            System.out.println("Prescription status updated successfully to " + status + ".");
        } else {
            System.out.println("Prescription not found.");
        }
    }

    // View Medication Inventory
    public void viewMedicationInventory() {
        System.out.println("Viewing medication inventory:");
        inventory.listAllMedicines();
    }

    // Submit replenishment request for medicine
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        Medicine medicine = inventory.getMedicineByName(medicineName);
        if (medicine != null) {
            if (medicine.isLowStock()) {
                System.out.println("Submitting replenishment request for Medicine: " + medicine.getName() + " with quantity: " + quantity);
                // Create and log a replenishment request
                ReplenishmentRequest request = new ReplenishmentRequest("REQ001", medicine.getMedicineID(), quantity);
                request.displayDetails();
            } else {
                System.out.println("Stock level is sufficient, no need for replenishment request.");
            }
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }
}



