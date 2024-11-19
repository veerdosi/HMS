import java.util.Scanner;

public class AdminMenu {
    private Admin admin;
    private Scanner scanner;

    // Constructor
    public AdminMenu(Admin admin) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    // Display Admin Menu
    public void displayMenu() {
        int choice = -1;
        while (choice != 5) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");

            choice = readMenuChoice();
            processChoice(choice);
        }
    }

    // Process menu choices
    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                manageHospitalStaff();
                break;
            case 2:
                viewAppointmentDetails();
                break;
            case 3:
                manageMedicationInventory();
                break;
            case 4:
                approveReplenishmentRequests();
                break;
            case 5:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Manage Hospital Staff
    private void manageHospitalStaff() {
        System.out.println("1. Add Staff");
        System.out.println("2. Remove Staff");
        System.out.println("3. Update Staff Details");
        System.out.print("Choose an action: ");
        int action = readMenuChoice();

        switch (action) {
            case 1:
                String staffDetails = readStringInput("Enter staff details (format: Name,Role,Age,Gender): ");
                admin.addStaff(staffDetails);
                break;
            case 2:
                String staffID = readStringInput("Enter Staff ID to remove: ");
                admin.removeStaff(staffID);
                break;
            case 3:
                String updateDetails = readStringInput("Enter updated staff details (format: ID,NewDetails): ");
                admin.updateStaff(updateDetails);
                break;
            default:
                System.out.println("Invalid action. Returning to main menu.");
        }
    }

    // View Appointment Details
    private void viewAppointmentDetails() {
        admin.viewAppointmentDetails();
    }

    // Manage Medication Inventory
    private void manageMedicationInventory() {
        System.out.println("1. Add Medication");
        System.out.println("2. Update Stock Levels");
        System.out.print("Choose an action: ");
        int action = readMenuChoice();

        switch (action) {
            case 1:
                String medicineDetails = readStringInput("Enter medication details (format: Name,Stock,AlertLevel): ");
                admin.addMedication(medicineDetails);
                break;
            case 2:
                String updateStock = readStringInput("Enter stock update (format: Name,NewStock): ");
                admin.updateStockLevels(updateStock);
                break;
            default:
                System.out.println("Invalid action. Returning to main menu.");
        }
    }

    // Approve Replenishment Requests
    private void approveReplenishmentRequests() {
        admin.approveReplenishmentRequests();
    }

    // Input utilities
    private int readMenuChoice() {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return choice;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}