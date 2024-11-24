import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `MedicineInventory` class manages the inventory of medicines in the
 * system. It provides
 * functionalities for adding, updating, and tracking medicines, as well as
 * monitoring low-stock
 * levels and maintaining a persistent record of inventory in a CSV file.
 * <p>
 * This class follows the Singleton design pattern to ensure a single,
 * centralized instance of
 * the inventory is used throughout the application.
 */
public class MedicineInventory {
    private static MedicineInventory instance; // Singleton instance
    private List<Medicine> medicines; // List of medicines in the inventory
    private String filePath; // Path to the CSV file for persistent storage

    /**
     * Private constructor for the `MedicineInventory` class.
     * Initializes the inventory by loading medicine data from the specified CSV
     * file.
     *
     * @param filePath the path to the CSV file containing medicine data.
     */
    private MedicineInventory(String filePath) {
        this.filePath = filePath;
        this.medicines = new ArrayList<>();
        loadMedicinesFromCsv(filePath);
    }

    /**
     * Retrieves the singleton instance of `MedicineInventory`.
     * If no instance exists, a new one is created using the specified file path.
     *
     * @param filePath the file path to initialize the instance if it is not already
     *                 created.
     * @return the singleton instance of `MedicineInventory`.
     */
    public static MedicineInventory getInstance(String filePath) {
        if (instance == null) {
            instance = new MedicineInventory(filePath);
        }
        return instance;
    }

    /**
     * @return List<String>
     */
    public List<String> getMedicineNames() {
        List<String> medicineNames = new ArrayList<>();
        for (Medicine medicine : medicines) { // Assuming `medicines` is a list of Medicine objects
            medicineNames.add(medicine.getName()); // Assuming `getName()` retrieves the medicine's name
        }
        return medicineNames;
    }

    /**
     * Loads medicines from the specified CSV file into the inventory.
     *
     * @param filePath the path to the CSV file containing medicine data.
     */
    private void loadMedicinesFromCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 3) { // Ensure the record has enough fields
                    String name = fields[0].trim();
                    int initialStock = Integer.parseInt(fields[1].trim());
                    int lowStockAlert = Integer.parseInt(fields[2].trim());

                    // Create and add a new Medicine object to the inventory
                    Medicine medicine = new Medicine(name, initialStock, lowStockAlert);
                    medicines.add(medicine);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading medicines from CSV: " + e.getMessage());
        }
    }

    /**
     * Adds a new medicine to the inventory and appends it to the CSV file.
     *
     * @param name          the name of the medicine.
     * @param initialStock  the initial stock level of the medicine.
     * @param lowStockAlert the low-stock alert threshold for the medicine.
     */
    public void addMedicine(String name, int initialStock, int lowStockAlert) {
        if (getMedicineByName(name) != null) {
            System.out.println("Medicine " + name + " already exists in the inventory.");
            return;
        }

        Medicine newMedicine = new Medicine(name, initialStock, lowStockAlert);
        medicines.add(newMedicine);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(name + "," + initialStock + "," + lowStockAlert);
            bw.newLine();
            System.out.println("Medicine " + name + " added to inventory and file.");
        } catch (IOException e) {
            System.err.println("Error writing new medicine to CSV file: " + e.getMessage());
        }
    }

    /**
     * Retrieves a medicine from the inventory by its name.
     *
     * @param name the name of the medicine to retrieve.
     * @return the `Medicine` object if found, or `null` if the medicine does not
     *         exist.
     */
    public Medicine getMedicineByName(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        System.out.println("Medicine not found: " + name);
        return null;
    }

    /**
     * Lists all medicines in the inventory along with their stock levels.
     */
    public void listAllMedicines() {
        for (Medicine medicine : medicines) {
            System.out.println("Medicine: " + medicine.getName() + ", Stock: " + medicine.getCurrentStock());
        }
    }

    /**
     * Checks for medicines that are low on stock and displays warnings for each.
     */
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

    /**
     * Decreases the stock of a specific medicine by 1 and updates the CSV file.
     *
     * @param name the name of the medicine whose stock will be decreased.
     */
    public void decreaseStock(String name) {
        Medicine medicine = getMedicineByName(name);
        if (medicine != null) {
            if (medicine.getCurrentStock() > 0) {
                medicine.updateStock(-1);
                System.out.println(
                        "Decreased stock for " + medicine.getName() + ". New stock: " + medicine.getCurrentStock());
                updateCsvFile();
            } else {
                System.out.println("Stock for " + medicine.getName() + " is already zero. Cannot decrease further.");
            }
        }
    }

    /**
     * Updates the stock level of a specific medicine and reflects the change in the
     * CSV file.
     *
     * @param name     the name of the medicine.
     * @param quantity the quantity to add (positive) or subtract (negative).
     */
    public boolean updateStock(String name, int quantity) {
        Medicine medicine = getMedicineByName(name);
        if (medicine != null) {
            medicine.updateStock(quantity);
            System.out
                    .println("Stock updated for " + medicine.getName() + ". New stock: " + medicine.getCurrentStock());
            updateCsvFile();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the CSV file with the current inventory data.
     */
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
