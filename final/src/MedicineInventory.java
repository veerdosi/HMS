import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineInventory {
    private static MedicineInventory instance;
    private List<Medicine> medicines;
    private String filePath;

    // Private constructor for Singleton pattern
    private MedicineInventory(String filePath) {
        this.filePath = filePath;
        this.medicines = new ArrayList<>();
        loadMedicinesFromCsv(filePath);
    }

    /**
     * @param filePath
     * @return MedicineInventory
     */
    // Singleton access method
    public static MedicineInventory getInstance(String filePath) {
        if (instance == null) {
            instance = new MedicineInventory(filePath);
        }
        return instance;
    }

    // Load medicines from the specified CSV file path
    private void loadMedicinesFromCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header line (skip it)
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 3) { // Ensure there are enough fields for a valid record
                    String name = fields[0].trim();
                    int initialStock = Integer.parseInt(fields[1].trim());
                    int lowStockAlert = Integer.parseInt(fields[2].trim());

                    // Create and add Medicine object
                    Medicine medicine = new Medicine(name, initialStock, lowStockAlert);
                    medicines.add(medicine);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading medicines from CSV: " + e.getMessage());
        }
    }

    // Add a new medicine to the list and write it to the CSV file
    public void addMedicine(String name, int initialStock, int lowStockAlert) {
        // Check if medicine already exists to avoid duplicates
        if (getMedicineByName(name) != null) {
            System.out.println("Medicine " + name + " already exists in the inventory.");
            return;
        }

        // Create a new Medicine object
        Medicine newMedicine = new Medicine(name, initialStock, lowStockAlert);

        // Add the medicine to the list
        medicines.add(newMedicine);

        // Append the new medicine to the CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(name + "," + initialStock + "," + lowStockAlert);
            bw.newLine();
            System.out.println("Medicine " + name + " added to inventory and file.");
        } catch (IOException e) {
            System.err.println("Error writing new medicine to CSV file: " + e.getMessage());
        }
    }

    // Get a medicine by its name
    public Medicine getMedicineByName(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        System.out.println("Medicine not found: " + name);
        return null;
    }

    // List all medicines with their stock levels
    public void listAllMedicines() {
        for (Medicine medicine : medicines) {
            System.out.println("Medicine: " + medicine.getName() + ", Stock: " + medicine.getCurrentStock());
        }
    }

    // Check if any medicine is low on stock and display a warning
    public void checkLowStock() {
        int lowStockCount = 0;
        for (Medicine medicine : medicines) {
            if (medicine.isLowStock()) {
                lowStockCount++;
                System.out.println("Warning: Low stock for " + medicine.getName() + " (Current stock: "
                        + medicine.getCurrentStock() + ")");
            }
        }
        if (lowStockCount == 0) {
            System.out.println("All medicines are well-stocked.");
        }
    }

    // Decrease stock for a specific medicine by 1 and update the CSV file
    public void decreaseStock(String name) {
        Medicine medicine = getMedicineByName(name);
        if (medicine != null) {
            if (medicine.getCurrentStock() > 0) {
                medicine.updateStock(-1);
                System.out.println(
                        "Decreased stock for " + medicine.getName() + ". New stock: " + medicine.getCurrentStock());
                updateCsvFile(); // Reflect the change in the CSV file
            } else {
                System.out.println("Stock for " + medicine.getName() + " is already zero. Cannot decrease further.");
            }
        }
    }

    // Update stock for a specific medicine by name and reflect it in the CSV
    public void updateStock(String name, int quantity) {
        Medicine medicine = getMedicineByName(name);
        if (medicine != null) {
            medicine.updateStock(quantity);
            System.out
                    .println("Stock updated for " + medicine.getName() + ". New stock: " + medicine.getCurrentStock());
            updateCsvFile(); // Reflect the change in the CSV file
        }
    }

    // Update the entire CSV file with the current list of medicines
    private void updateCsvFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Medicine Name,Initial Stock,Low Stock Alert");
            bw.newLine();
            for (Medicine medicine : medicines) {
                bw.write(medicine.getName() + "," + medicine.getCurrentStock() + "," + medicine.getLowStockAlert());
                bw.newLine();
            }
            System.out.println("CSV file updated successfully.");
        } catch (IOException e) {
            System.err.println("Error updating CSV file: " + e.getMessage());
        }
    }
}
