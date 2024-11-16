// File: Pharmacist.java

import java.util.List;

public class Pharmacist extends User implements InventoryManageable {
    private String pharmacistID;

    // Constructor
    public Pharmacist(String loginID, String password, String pharmacistID) {
        super(loginID, password);
        this.pharmacistID = pharmacistID;
    }

    // Method to update stock of a specific medicine
    @Override
    public void updateStock(String medicineID, int quantity) {
        System.out.println("Updating stock for Medicine ID: " + medicineID + " by " + quantity);
        // Logic to update stock, possibly interacting with an Inventory class
    }

    // Submit a replenishment request for a medicine
    @Override
    public void submitReplenishmentRequest(Medicine medicine, int quantity) {
        System.out.println("Submitting replenishment request for Medicine: " + medicine.getName() + " with quantity: " + quantity);
        // Logic to create and submit a ReplenishmentRequest
    }


    }

    // Method to view appointment outcome records (Placeholder for now)
    public List<AppointmentOutcomeRecord> viewAppointmentOutcomeRecords() {
        System.out.println("Viewing appointment outcome records...");
        // Return mock list or fetched list of records
        return null;
    }

    // Method to update prescription status
    public void updatePrescriptionStatus(Prescription prescription) {
        System.out.println("Updating prescription status for Prescription ID: " + prescription.getPrescriptionID());
        prescription.setPrescriptionStatus("dispensed");
    }

    // Display pharmacist information
    public void displayPharmacistInfo() {
        System.out.println("Pharmacist ID: " + pharmacistID + ", Login ID: " + getLoginID());
    }
}
