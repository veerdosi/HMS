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

    // Update the stock of the medicine by a given quantity (positive to add,
    // negative to subtract)
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

    // Setters for stock and alert level
    public void setStock(int stock) {
        if (stock >= 0) {
            this.currentStock = stock;
        } else {
            System.out.println("Stock value cannot be negative. Stock not updated.");
        }
    }

    public void setLowStockAlert(int alertLevel) {
        if (alertLevel >= 0) {
            this.lowStockAlert = alertLevel;
        } else {
            System.out.println("Alert level cannot be negative. Alert level not updated.");
        }
    }

    // Replenish stock
    public void replenishStock(int quantity) {
        if (quantity > 0) {
            this.currentStock += quantity;
            System.out.println("Replenished " + quantity + " units of " + name + ". Current stock: " + currentStock);
        } else {
            System.out.println("Invalid replenishment quantity. Must be greater than zero.");
        }
    }

    // Getters for all fields
    public String getName() {
        return name;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    // toString override for better debugging and logs
    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", currentStock=" + currentStock +
                ", lowStockAlert=" + lowStockAlert +
                '}';
    }
}