package actual;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    // Load staff from a CSV file
    public static List<User> loadStaff(String filename) {
        List<User> staffList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String role = data[0];
                String userID = data[1];
                String password = data[2];
                String name = data[3];
                String contactNumber = data[4];
                String email = data[5];

                switch (role.toLowerCase()) {
                    case "doctor":
                        staffList.add(new Doctor(userID, password, name, contactNumber, email, userID));
                        break;
                    case "pharmacist":
                        staffList.add(new Pharmacist(userID, password, name, contactNumber, email, userID));
                        break;
                    case "administrator":
                        staffList.add(new Administrator(userID, password, name, contactNumber, email, userID));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading staff from file: " + e.getMessage());
        }
        return staffList;
    }

    // Load patients from a CSV file
    public static List<Patient> loadPatients(String filename) {
        List<Patient> patientList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String patientID = data[0];
                String name = data[1];
                String dob = data[2];
                String gender = data[3];
                String contactNumber = data[4];
                String email = data[5];
                String bloodType = data[6];

                patientList.add(new Patient(patientID, "password", name, contactNumber, email, patientID));
            }
        } catch (IOException e) {
            System.err.println("Error loading patients from file: " + e.getMessage());
        }
        return patientList;
    }

    // Load inventory from a CSV file
    public static List<Medicine> loadInventory(String filename) {
        List<Medicine> inventory = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String medicineID = data[0];
                String name = data[1];
                int currentStock = Integer.parseInt(data[2]);
                int lowStockAlert = Integer.parseInt(data[3]);

                inventory.add(new Medicine(medicineID, name, currentStock, lowStockAlert));
            }
        } catch (IOException e) {
            System.err.println("Error loading inventory from file: " + e.getMessage());
        }
        return inventory;
    }
}
