
public class Medicine {
    private String medicineID;
    private String name;
    private int currentStock;
    private int lowStockAlert;

    public Medicine(String medicineID, String name, int currentStock, int lowStockAlert) {
        this.medicineID = medicineID;
        this.name = name;
        this.currentStock = currentStock;
        this.lowStockAlert = lowStockAlert;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public boolean isLowStock() {
        return currentStock < lowStockAlert;
    }
}
