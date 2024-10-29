public class Medicine {
    private String medicineID;
    private String name;
    private int currentStock;
    private int lowStockAlert;

    public Medicine(String medicineID, String name, int initialStock, int lowStockAlert) {
        this.medicineID = medicineID;
        this.name = name;
        this.currentStock = initialStock;
        this.lowStockAlert = lowStockAlert;
    }

    public void updateStock(int quantity) {
        this.currentStock += quantity;
    }

    public boolean isLowStock() {
        return currentStock <= lowStockAlert;
    }
}
