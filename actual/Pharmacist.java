package actual;

import java.util.HashMap;
import java.util.Map;

public class Pharmacist extends User implements InventoryManageable {
    private String pharmacistID;
    private Map<String, Integer> medicationStock; // A map to hold medication stock levels

    public Pharmacist(String userID, String password, String name, String contactNumber, String email,
            String pharmacistID) {
        super(userID, password, name, contactNumber, email, UserRole.PHARMACIST);
        this.pharmacistID = pharmacistID;
        this.medicationStock = new HashMap<>(); // Initialize the medication stock
    }

    @Override
    public void updateStock(String medicineID, int quantity) {
        // Logic to update stock of medications
        if (medicationStock.containsKey(medicineID)) {
            int currentStock = medicationStock.get(medicineID);
            medicationStock.put(medicineID, currentStock + quantity);
            System.out
                    .println("Updated stock for medicine " + medicineID + ". New stock: " + (currentStock + quantity));
        } else {
            medicationStock.put(medicineID, quantity);
            System.out.println("Added new medicine " + medicineID + " with stock: " + quantity);
        }
    }

    @Override
    public void submitReplenishmentRequest(Medicine medicine, int quantity) {
        // Logic to submit a replenishment request for medication
        // Assuming we have a system in place to handle these requests
        ReplenishmentRequest request = new ReplenishmentRequest(medicine.getMedicineID(), quantity);
        System.out.println(
                "Submitted replenishment request for medicine " + medicine.getName() + " with quantity: " + quantity);
        // Here you would typically send this request to a system or service for
        // processing
    }

    @Override
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        // Pharmacists can only submit; approval is handled by Administrator
        throw new UnsupportedOperationException("Pharmacist cannot approve replenishment requests.");
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Performing pharmacist-specific actions.");
    }

    // Method to get current stock for a specific medicine
    public int getStock(String medicineID) {
        return medicationStock.getOrDefault(medicineID, 0);
    }
}
