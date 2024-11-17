// File: InventoryManageable.java

public interface InventoryManageable {
    // Method to view the medication inventory
    void viewMedicationInventory();

    // Method to submit a replenishment request
    void submitReplenishmentRequest(String medicineName, int quantity);

    // Method to update stock for a given medicine
    void updateMedicineStock(String medicineID, int quantity);
}
