
import java.util.*;

public class AdminMenu {
    private Admin admin;
    private Scanner scanner = new Scanner(System.in);

    private List<Staff> staffList = new ArrayList<>();
    private List<Medicine> inventory = new ArrayList<>();
    private List<ReplenishmentRequest> requests = new ArrayList<>();

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public boolean display() {
        while (true) {
            System.out.println("---- Admin Menu ----");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View and Manage Medication Inventory");
            System.out.println("3. Approve Replenishment Requests");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manageHospitalStaff();
                    break;
                case 2:
                    manageMedicationInventory();
                    break;
                case 3:
                    approveReplenishmentRequests();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return false;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageHospitalStaff() {
        System.out.println("1. Add Staff");
        System.out.println("2. Remove Staff");
        System.out.println("3. Update Staff");
        System.out.println("4. View All Staff");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> admin.addStaff(staffList, new Staff("S001", "John Doe", "Doctor", "12345678"));
            case 2 -> admin.removeStaff(staffList, "S001");
            case 3 -> admin.updateStaff(staffList, "S001", "Jane Doe", "Pharmacist", "87654321");
            case 4 -> admin.viewStaff(staffList);
        }
    }

    private void manageMedicationInventory() {
        System.out.println("1. View Inventory");
        System.out.println("2. Update Stock");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> admin.viewMedicineInventory(inventory);
            case 2 -> admin.updateMedicineStock(inventory, "M001", 50);
        }
    }

    private void approveReplenishmentRequests() {
        admin.viewReplenishmentRequests(requests);
        admin.approveReplenishmentRequest(requests, "R001");
    }
}
