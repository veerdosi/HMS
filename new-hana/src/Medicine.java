public class Medicine {
    private String name;
    private int currentStock;
    private int lowStockAlert;

    // Constructor
    public Medicine(String name, int initialStock, int lowStockAlert) {
        this.name = name;
        this.currentStock = initialStock;
        this.lowStockAlert = lowStockAlert;
    }

    // Update the stock of the medicine by a given quantity (positive to add, negative to subtract)
    public void updateStock(int quantity) {
        this.currentStock += quantity;
        if (this.currentStock < 0) {
            this.currentStock = 0; // Ensuring stock doesn't go negative
        }
        System.out.println("Updated stock for " + name + ": " + this.currentStock);
    }

    // Check if the current stock is below the low stock alert level
    public boolean isLowStock() {
        return currentStock <= lowStockAlert;
    }

    // Getters for name and current stock, if needed in other parts of the system
    public String getName() {
        return name;
    }

    public int getCurrentStock() {
        return currentStock;
    }
}
