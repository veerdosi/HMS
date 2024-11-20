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
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve or Reject Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

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
                    handleReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return false; // Return false to signal logout
                default:
                    System.out.println("Invalid choice. Please try again.");
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
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    admin.displayAllStaff();
                    break;
                case 2:
                    addNewStaff();
                    break;
                case 3:
                    updateStaff();
                    break;
                case 4:
                    removeStaff();
                    break;
                case 5:
                    filterStaff();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Contact Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        User newStaff;
        switch (role.toLowerCase()) {
            case "doctor":
                newStaff = new Doctor(staffID, name, "password", gender, email, contactNumber, age);
                break;
            case "pharmacist":
                newStaff = new Pharmacist(staffID, name, "password", gender, email, contactNumber, age);
                break;
            case "administrator":
                newStaff = new Admin(staffID, name, "password", gender, email, contactNumber, age);
                break;
            default:
                System.out.println("Invalid role. Staff not added.");
                return;
        }
        admin.addStaff(newStaff);
    }

    private void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String staffID = scanner.nextLine();
        System.out.print("Enter field to update (Name, ContactNumber, Email, Age): ");
        String field = scanner.nextLine();
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        admin.updateStaff(staffID, field, newValue);
    }

    private void removeStaff() {
        System.out.print("Enter Staff ID to remove: ");
        String staffID = scanner.nextLine();
        admin.removeStaff(staffID);
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
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    MedicineInventory.getInstance(null).listAllMedicines();
                    break;
                case 2:
                    admin.viewLowStockMedicines();
                    break;
                case 3:
                    updateMedicineStock();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateMedicineStock() {
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine();
        System.out.print("Enter new stock quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        MedicineInventory.getInstance(null).updateStock(medicineName, quantity);
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
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    admin.viewAllRequests();
                    break;
                case 2:
                    System.out.print("Enter Status (PENDING, APPROVED, REJECTED): ");
                    String status = scanner.nextLine().toUpperCase();
                    admin.viewRequestsByStatus(RequestStatus.valueOf(status));
                    break;
                case 3:
                    System.out.print("Enter Request ID to approve: ");
                    int approveId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    admin.approveRequest(approveId);
                    break;
                case 4:
                    System.out.print("Enter Request ID to reject: ");
                    int rejectId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    admin.rejectRequest(rejectId);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
