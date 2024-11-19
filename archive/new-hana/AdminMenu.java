import java.util.Scanner;

public class AdminMenu {
    private Admin admin;
    private Scanner scanner = new Scanner(System.in);

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public boolean display() {
        while (true) {
            System.out.println("---- Admin Menu ----");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manageHospitalStaff();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    manageMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return false; // Return false to signal logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageHospitalStaff() {
        // Implement manage hospital staff
    }

    private void viewAppointments() {
        // Implement view appointments
    }

    private void manageMedicationInventory() {
        // Implement manage medication inventory
    }

    private void approveReplenishmentRequests() {
        // Implement approve replenishment requests
    }
}
