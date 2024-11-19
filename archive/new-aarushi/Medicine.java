
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

    public String getMedicineID() {
        return medicineID;
    }

    public String getName() {
        return name;
    }

    public void updateStock(int quantity) {
        this.currentStock += quantity;
        System.out.println("Stock updated. Current stock: " + currentStock);
    }

    public void setLowStockAlert(int alertLevel) {
        this.lowStockAlert = alertLevel;
    }

    public boolean isLowStock() {
        return currentStock < lowStockAlert;
    }

    public String toString() {
        return "Medicine: " + name + ", Stock: " + currentStock + ", Low Stock Alert: " + lowStockAlert;
    }
}
