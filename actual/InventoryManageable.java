package actual;

public interface InventoryManageable {
    void updateStock(String medicineID, int quantity);

    void submitReplenishmentRequest(Medicine medicine, int quantity);

    void approveReplenishmentRequest(ReplenishmentRequest request);
}
