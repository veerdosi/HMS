/**
 * The `Medicine` class represents a pharmaceutical product in the inventory,
 * including its
 * stock levels and low-stock alert threshold.
 */
public class Medicine {
    private String name;
    private int currentStock;
    private int lowStockAlert;

    /**
     * Constructs a `Medicine` with the specified name, initial stock, and low-stock
     * alert level.
     *
     * @param name          the name of the medicine.
     * @param initialStock  the initial stock level.
     * @param lowStockAlert the low-stock alert threshold.
     */
    public Medicine(String name, int initialStock, int lowStockAlert) {
        this.name = name;
        this.currentStock = initialStock;
        this.lowStockAlert = lowStockAlert;
    }

    public Medicine(String name) {
        this.name = name;
    }

    /**
     * Updates the stock level of the medicine.
     *
     * @param quantity the quantity to add (positive) or subtract (negative).
     */
    public void updateStock(int quantity) {
        this.currentStock += quantity;
        if (this.currentStock < 0) {
            this.currentStock = 0;
        }
        System.out.println("Updated stock for " + name + ": " + this.currentStock);
    }

    /**
     * Checks if the current stock level is below the low-stock alert threshold.
     *
     * @return `true` if stock is low, otherwise `false`.
     */
    public boolean isLowStock() {
        return currentStock <= lowStockAlert;
    }

    /**
     * Replenishes the stock by the specified quantity.
     *
     * @param quantity the quantity to add to the stock.
     */
    public void replenishStock(int quantity) {
        if (quantity > 0) {
            this.currentStock += quantity;
            System.out.println("Replenished " + quantity + " units of " + name + ". Current stock: " + currentStock);
        } else {
            System.out.println("Invalid replenishment quantity. Must be greater than zero.");
        }
    }

    /**
     * Retrieves the name of the medicine.
     *
     * @return the name of the medicine.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the current stock level of the medicine.
     *
     * @return the current stock level.
     */
    public int getCurrentStock() {
        return currentStock;
    }

    /**
     * Retrieves the low-stock alert threshold.
     *
     * @return the low-stock alert level.
     */
    public int getLowStockAlert() {
        return lowStockAlert;
    }

    /**
     * Updates the stock level directly.
     *
     * @param stock the new stock level.
     */
    public void setStock(int stock) {
        if (stock >= 0) {
            this.currentStock = stock;
        } else {
            System.out.println("Stock value cannot be negative. Stock not updated.");
        }
    }

    /**
     * Updates the low-stock alert level.
     *
     * @param alertLevel the new low-stock alert level.
     */
    public void setLowStockAlert(int alertLevel) {
        if (alertLevel >= 0) {
            this.lowStockAlert = alertLevel;
        } else {
            System.out.println("Alert level cannot be negative. Alert level not updated.");
        }
    }

    /**
     * Returns a string representation of the medicine, including its name, stock
     * level, and alert threshold.
     *
     * @return a string representation of the medicine.
     */
    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", currentStock=" + currentStock +
                ", lowStockAlert=" + lowStockAlert +
                '}';
    }
}
