
import java.util.Scanner;

public class AdminMenu {
    private Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments");
            System.out.println("3. Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    // Code to view and manage hospital staff
                    break;
                case 2:
                    // Code to view appointments
                    break;
                case 3:
                    // Code to manage medication inventory
                    break;
                case 4:
                    // Code to approve replenishment requests
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
