// File: Medicine.java

public class Medicine {
    private String medicineID;
    private String name;
    private int currentStock;
    private int lowStockAlert;

    // Constructor
    public Medicine(String medicineID, String name, int currentStock, int lowStockAlert) {
        this.medicineID = medicineID;
        this.name = name;
        this.currentStock = currentStock;
        this.lowStockAlert = lowStockAlert;
    }

    // Getters
    public String getMedicineID() {
        return medicineID;
    }

    public String getName() {
        return name;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    // Method to update stock by adding or reducing quantity
    public void updateStock(int quantity) {
        this.currentStock += quantity;
    }

    // Check if stock is below the low stock alert level
    public boolean isLowStock() {
        return currentStock <= lowStockAlert;
    }

    // Display medicine details
    public void displayMedicineInfo() {
        System.out.println("Medicine ID: " + medicineID + ", Name: " + name 
                + ", Current Stock: " + currentStock + ", Low Stock Alert Level: " + lowStockAlert);
    }
}
