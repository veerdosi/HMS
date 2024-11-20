// File: InventoryManageable.java

public interface InventoryManageable {
    // Method to view the medication inventory
    void viewMedicationInventory();

    // Method to submit a replenishment request
    void submitReplenishmentRequest(String medicineName, int quantity);

    // Method to update stock for a given medicine
    void updateStock(String medicineID, int quantity);

    // Method to approve a replenishment request
    void approveReplenishmentRequest(String requestID);
}
