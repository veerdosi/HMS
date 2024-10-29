package actual;

import java.util.List;

public class HospitalManagementSystem {
    private List<User> staff;
    private List<Patient> patients;
    private List<Medicine> inventory;

    public HospitalManagementSystem() {
        // Load data from files
        this.staff = FileLoader.loadStaff("staff.csv");
        this.patients = FileLoader.loadPatients("patients.csv");
        this.inventory = FileLoader.loadInventory("inventory.csv");

        System.out.println("System initialized with data from files.");
    }

    public static void main(String[] args) {
        // Initialize the system
        HospitalManagementSystem hms = new HospitalManagementSystem();
    }
}
