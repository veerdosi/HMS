
public interface InventoryManageable {
    void updateMedicineStock(Medicine medicine, int quantity);
    void submitReplenishmentRequest(Medicine medicine, int quantity);
    void approveReplenishmentRequest(ReplenishmentRequest request);
}
