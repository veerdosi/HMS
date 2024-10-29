public interface InventoryManageable {
    void updateStock(int quantity);

    void submitReplenishmentRequest(Medicine medicine, int quantity);

    void approveReplenishmentRequest(ReplenishmentRequest request);
}
