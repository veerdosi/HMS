import java.util.HashMap;
import java.util.Map;

public class Pharmacist {
    private Map<String, AppointmentOutcomeRecord> appointmentRecords;
    private Map<String, Medicine> inventory;

    // Constructor
    public Pharmacist() {
        this.appointmentRecords = new HashMap<>();
        this.inventory = new HashMap<>();
    }

    // View Appointment Outcome Record
    public void viewAppointmentOutcome(String appointmentID) {
        if (appointmentRecords.containsKey(appointmentID)) {
            AppointmentOutcomeRecord record = appointmentRecords.get(appointmentID);
            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Date: " + record.getDate());
            System.out.println("Prescribed Medications: " + record.getPrescribedMedications());
            System.out.println("Notes: " + record.getNotes());
        } else {
            System.out.println("Appointment record not found.");
        }
    }

    // Update Prescription Status
    public void updatePrescriptionStatus(String prescriptionID, PrescriptionStatus status) {
        for (AppointmentOutcomeRecord record : appointmentRecords.values()) {
            if (record.updatePrescriptionStatus(prescriptionID, status)) {
                System.out.println("Prescription updated successfully.");
                return;
            }
        }
        System.out.println("Prescription ID not found.");
    }

    // View Medication Inventory
    public void viewMedicationInventory() {
        System.out.println("Medication Inventory:");
        for (Medicine medicine : inventory.values()) {
            System.out.println(medicine.getName() + ": " + medicine.getStock() + " units");
        }
    }

    // Submit Replenishment Request
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        if (inventory.containsKey(medicineName)) {
            Medicine medicine = inventory.get(medicineName);
            medicine.replenishStock(quantity);
            System.out.println("Replenishment request for " + quantity + " units of " + medicineName + " submitted.");
        } else {
            System.out.println("Medicine not found in inventory.");
        }
    }

    // Add sample data (for testing)
    public void addSampleData() {
        inventory.put("Paracetamol", new Medicine("Paracetamol", 100));
        inventory.put("Amoxicillin", new Medicine("Amoxicillin", 50));

        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord("A001", "2024-11-19");
        record.addPrescription("P001", "Paracetamol", PrescriptionStatus.PENDING);
        appointmentRecords.put("A001", record);
    }
}