import java.util.Scanner;

public class AdminMenu {
    private Admin admin;
    private Scanner scanner;

    // Updated Constructor
    public AdminMenu(Admin admin, Scanner scanner) {
        this.admin = admin;
        this.scanner = scanner; // Use shared scanner instance
    }

    /**
     * Displays the Admin Menu.
     * 
     * @return boolean indicating whether to continue or exit.
     */
    public boolean display() {
        while (true) {
            try {
                System.out.println("---- Admin Menu ----");
                System.out.println("1. View and Manage Hospital Staff");
                System.out.println("2. View Appointment Details");
                System.out.println("3. View and Manage Medication Inventory");
                System.out.println("4. Approve or Reject Replenishment Requests");
                System.out.println("5. Logout");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    scanner.next(); // Consume invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1 -> manageHospitalStaff();
                    case 2 -> viewAppointmentDetails();
                    case 3 -> manageMedicationInventory();
                    case 4 -> handleReplenishmentRequests();
                    case 5 -> {
                        System.out.println("Logging out...");
                        return false; // Exit the menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Option 1: Manage Hospital Staff
    private void manageHospitalStaff() {
        while (true) {
            System.out.println("---- Manage Hospital Staff ----");
            System.out.println("1. Display All Staff");
            System.out.println("2. Add New Staff");
            System.out.println("3. Update Existing Staff");
            System.out.println("4. Remove Staff");
            System.out.println("5. Filter Staff by Criteria");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = readValidatedInt();

            switch (choice) {
                case 1 -> admin.displayAllStaff();
                case 2 -> addNewStaff();
                case 3 -> updateStaff();
                case 4 -> removeStaff();
                case 5 -> filterStaff();
                case 6 -> {
                    return; // Back to Main Menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewStaff() {
        System.out.print("Enter Staff ID: ");
        String staffID = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role (Doctor, Pharmacist, Administrator): ");
        String role = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = readValidatedInt();
        System.out.print("Enter Contact Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        User newStaff;
        switch (role.toLowerCase()) {
            case "doctor" -> newStaff = new Doctor(staffID, name, "password", gender, email, contactNumber, age);
            case "pharmacist" ->
                newStaff = new Pharmacist(staffID, name, "password", gender, email, contactNumber, age);
            case "administrator" -> newStaff = new Admin(staffID, name, "password", gender, email, contactNumber, age);
            default -> {
                System.out.println("Invalid role. Staff not added.");
                return;
            }
        }
        admin.addStaff(newStaff);
        System.out.println("Staff member added successfully.");
    }

    private void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String staffID = scanner.nextLine();
        System.out.print("Enter field to update (Name, ContactNumber, Email, Age): ");
        String field = scanner.nextLine();
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        admin.updateStaff(staffID, field, newValue);
        System.out.println("Staff details updated successfully.");
    }

    private void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String staffID = scanner.nextLine();
        admin.removeStaff(staffID);
        System.out.println("Staff member removed successfully.");
    }

    private void filterStaff() {
        System.out.print("Enter filter type (Role, Gender, Age): ");
        String filterType = scanner.nextLine();
        System.out.print("Enter filter value: ");
        String filterValue = scanner.nextLine();
        admin.displayFilteredStaff(filterType, filterValue);
    }

    // Option 2: View Appointment Details
    private void viewAppointmentDetails() {
        System.out.println("Fetching appointment details...");
        AppointmentOutcomeRecord.getInstance().displayAllAppointments();
    }

    // Option 3: Manage Medication Inventory
    private void manageMedicationInventory() {
        while (true) {
            System.out.println("---- Manage Medication Inventory ----");
            System.out.println("1. View All Medicines");
            System.out.println("2. View Low Stock Medicines");
            System.out.println("3. Update Medicine Stock Level");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = readValidatedInt();

            switch (choice) {
                case 1 -> MedicineInventory.getInstance(null).listAllMedicines();
                case 2 -> admin.viewLowStockMedicines();
                case 3 -> updateMedicineStock();
                case 4 -> {
                    return; // Back to Main Menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateMedicineStock() {
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine();
        System.out.print("Enter new stock quantity: ");
        int quantity = readValidatedInt();
        MedicineInventory.getInstance(null).updateStock(medicineName, quantity);
        System.out.println("Medicine stock updated successfully.");
    }

    // Option 4: Handle Replenishment Requests
    private void handleReplenishmentRequests() {
        while (true) {
            System.out.println("---- Replenishment Requests ----");
            System.out.println("1. View All Requests");
            System.out.println("2. View Requests by Status");
            System.out.println("3. Approve Request");
            System.out.println("4. Reject Request");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = readValidatedInt();

            switch (choice) {
                case 1 -> admin.viewAllRequests();
                case 2 -> {
                    System.out.print("Enter Status (PENDING, APPROVED, REJECTED): ");
                    String status = scanner.nextLine().toUpperCase();
                    admin.viewRequestsByStatus(RequestStatus.valueOf(status));
                }
                case 3 -> {
                    System.out.print("Enter Request ID to approve: ");
                    int approveId = readValidatedInt();
                    admin.approveRequest(approveId);
                }
                case 4 -> {
                    System.out.print("Enter Request ID to reject: ");
                    int rejectId = readValidatedInt();
                    admin.rejectRequest(rejectId);
                }
                case 5 -> {
                    return; // Back to Main Menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Utility method to validate integer inputs
    private int readValidatedInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume invalid input
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }
}