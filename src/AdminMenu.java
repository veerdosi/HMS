
import java.util.List;

/**
 * The `AdminMenu` class provides an interactive menu for administrators to
 * perform various administrative tasks, including managing hospital staff,
 * viewing appointments, handling replenishment requests, and managing medication inventory.
 */
public class AdminMenu {
    private Admin admin;
    private MedicineInventory medicineInventory;

    /**
     * Constructs an `AdminMenu` instance for the specified administrator.
     *
     * @param admin The admin object associated with this menu.
     */
    public AdminMenu(Admin admin) {
        this.admin = admin;
        this.medicineInventory = MedicineInventory.getInstance(null);
    }

    /**
     * Displays the main admin menu and processes user input.
     *
     * @return `false` when the user chooses to log out, ending the menu loop.
     */
    public boolean display() {
        while (true) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve or Reject Replenishment Requests");
            System.out.println("5. Reset Password");
            System.out.println("6. Logout");

            try {
                int choice = InputHandler.getIntInput(1, 6);

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
                        resetPassword();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        return false;
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    /**
     * Resets the admin's password.
     */
    private void resetPassword() {
        String newPass = InputHandler.getStringInput("New Password: ");
        admin.changePassword(newPass);
        System.out.println("Password reset successfully.");
    }

    /**
     * Manages hospital staff, including adding, updating, removing, and filtering staff members.
     */
    private void manageHospitalStaff() {
        while (true) {
            try {
                System.out.println("\n---- Manage Hospital Staff ----");
                System.out.println("1. Display All Staff");
                System.out.println("2. Add New Staff");
                System.out.println("3. Update Existing Staff");
                System.out.println("4. Remove Staff");
                System.out.println("5. Filter Staff by Criteria");
                System.out.println("6. Back to Main Menu");

                int choice = InputHandler.getIntInput(1, 6);

                switch (choice) {
                    case 1 -> displayAllStaff();
                    case 2 -> addNewStaff();
                    case 3 -> updateStaff();
                    case 4 -> removeStaff();
                    case 5 -> filterStaff();
                    case 6 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error in staff management: " + e.getMessage());
            }
        }
    }

    /**
     * Displays all hospital staff members.
     */
    private void displayAllStaff() {
        try {
            System.out.println("\n--- All Hospital Staff ---");
            admin.displayAllStaff();
        } catch (Exception e) {
            System.out.println("Error displaying staff: " + e.getMessage());
        }
    }

    /**
     * Adds a new staff member to the hospital.
     */
    private void addNewStaff() {
        try {
            System.out.println("\n--- Add New Staff Member ---");

            String staffID = InputHandler.getStringInput("Enter Staff ID: ");
            String name = InputHandler.getStringInput("Enter Name: ");

            System.out.println("Select Role:");
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.println("3. Administrator");
            int roleChoice = InputHandler.getIntInput(1, 3);

            String gender = InputHandler.getStringInput("Enter Gender (M/F): ");
            System.out.println("Enter age:");
            int age = InputHandler.getIntInput(18, 100);
            String email = InputHandler.getStringInput("Enter Contact Email: ");
            String contactNumber = InputHandler.getStringInput("Enter Contact Number: ");

            User newStaff = switch (roleChoice) {
                case 1 -> new Doctor(staffID, name, "defaultpass", gender, email, contactNumber, age);
                case 2 -> new Pharmacist(staffID, name, "defaultpass", gender, email, contactNumber, age);
                case 3 -> new Admin(staffID, name, "defaultpass", gender, email, contactNumber, age);
                default -> throw new IllegalStateException("Invalid role selection");
            };

            admin.addStaff(newStaff);
            displayAllStaff();

        } catch (Exception e) {
            System.out.println("Error adding staff: " + e.getMessage());
        }
    }

    /**
     * Updates the details of an existing staff member.
     */
    private void updateStaff() {
        try {
            System.out.println("\n--- Update Staff Member ---");
            displayAllStaff();

            String staffID = InputHandler.getStringInput("Enter Staff ID to update: ");

            System.out.println("Select field to update:");
            System.out.println("1. Name");
            System.out.println("2. Contact Number");
            System.out.println("3. Email");
            System.out.println("4. Age");

            int fieldChoice = InputHandler.getIntInput(1, 4);
            String field = switch (fieldChoice) {
                case 1 -> "Name";
                case 2 -> "ContactNumber";
                case 3 -> "Email";
                case 4 -> "Age";
                default -> throw new IllegalStateException("Invalid field selection");
            };

            String newValue = InputHandler.getStringInput("Enter new value: ");
            admin.updateStaff(staffID, field, newValue);
            displayAllStaff();

        } catch (Exception e) {
            System.out.println("Error updating staff: " + e.getMessage());
        }
    }

    /**
     * Removes a staff member from the hospital.
     */
    private void removeStaff() {
        try {
            System.out.println("\n--- Remove Staff Member ---");
            displayAllStaff();

            String staffID = InputHandler.getStringInput("Enter Staff ID to remove: ");
            String confirm = InputHandler.getStringInput("Are you sure you want to remove this staff member? (yes/no): ");
            if (confirm.equalsIgnoreCase("yes")) {
                admin.removeStaff(staffID);
                displayAllStaff();
            } else {
                System.out.println("Removal cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error removing staff: " + e.getMessage());
        }
    }

    /**
     * Filters and displays hospital staff based on the specified criteria.
     */
    private void filterStaff() {
        try {
            System.out.println("\n--- Filter Staff ---");
            System.out.println("Select filter criteria:");
            System.out.println("1. Role");
            System.out.println("2. Gender");
            System.out.println("3. Age");

            int criteriaChoice = InputHandler.getIntInput(1, 3);
            String filterType = switch (criteriaChoice) {
                case 1 -> "Role";
                case 2 -> "Gender";
                case 3 -> "Age";
                default -> throw new IllegalStateException("Invalid criteria selection");
            };

            String filterValue = InputHandler.getStringInput("Enter filter value: ");
            admin.displayFilteredStaff(filterType, filterValue);

        } catch (Exception e) {
            System.out.println("Error filtering staff: " + e.getMessage());
        }
    }

    /**
     * Views appointment details for the hospital.
     */
    private void viewAppointmentDetails() {
        try {
            System.out.println("\n--- Appointment Details ---");
            AppointmentOutcomeRecord.getInstance().displayAllAppointments();
        } catch (Exception e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
        }
    }

    /**
     * Manages the medication inventory, including viewing and updating stock levels.
     */
    private void manageMedicationInventory() {
        while (true) {
            try {
                System.out.println("\n---- Manage Medication Inventory ----");
                System.out.println("1. View All Medicines");
                System.out.println("2. View Low Stock Medicines");
                System.out.println("3. Update Medicine Stock Level");
                System.out.println("4. Back to Main Menu");

                int choice = InputHandler.getIntInput(1, 4);

                switch (choice) {
                    case 1 -> viewAllMedicines();
                    case 2 -> viewLowStockMedicines();
                    case 3 -> updateMedicineStock();
                    case 4 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error in medication management: " + e.getMessage());
            }
        }
    }

    /**
     * Views all medicines in the inventory.
     */
    private void viewAllMedicines() {
        try {
            System.out.println("\n--- All Medicines in Inventory ---");
            medicineInventory.listAllMedicines();
        } catch (Exception e) {
            System.out.println("Error viewing medicines: " + e.getMessage());
        }
    }

    /**
     * Views medicines that are low on stock.
     */
    private void viewLowStockMedicines() {
        try {
            System.out.println("\n--- Low Stock Medicines ---");
            admin.viewLowStockMedicines();
        } catch (Exception e) {
            System.out.println("Error viewing low stock medicines: " + e.getMessage());
        }
    }

    /**
     * Updates the stock level of a specific medicine.
     */
    private void updateMedicineStock() {
        try {
            System.out.println("\n--- Update Medicine Stock ---");
            // Display a numbered menu for available medicines
            List<String> medicineNames = medicineInventory.getMedicineNames(); // Fetch all medicine names
            for (int i = 0; i < medicineNames.size(); i++) {
                System.out.println((i + 1) + ". " + medicineNames.get(i));
            }
            // Get user input for selecting a medicine
            int choice = InputHandler.getIntInput(1, medicineNames.size()); // Get a number between 1 and the size of the list
            String selectedMedicine = medicineNames.get(choice - 1); // Get the corresponding medicine name

            System.out.println("Enter the quantity:");
            int quantity = InputHandler.getIntInput(0, 1000);

            if (medicineInventory.updateStock(selectedMedicine, quantity)){
                     System.out.println("Medicine stock updated successfully.");
            }
            else {
            System.out.println("Error: Failed to update stock for " + selectedMedicine + ".");
            }
        } catch (Exception e) {
            System.out.println("Error updating medicine stock: " + e.getMessage());
        }
    }

    /**
     * Handles replenishment requests, including viewing, approving, and rejecting them.
     */
    private void handleReplenishmentRequests() {
        while (true) {
            try {
                System.out.println("\n---- Replenishment Requests ----");
                System.out.println("1. View All Requests");
                System.out.println("2. View Requests by Status");
                System.out.println("3. Approve Request");
                System.out.println("4. Reject Request");
                System.out.println("5. Back to Main Menu");

                int choice = InputHandler.getIntInput(1, 5);

                switch (choice) {
                    case 1 -> viewAllRequests();
                    case 2 -> viewRequestsByStatus();
                    case 3 -> approveRequest();
                    case 4 -> rejectRequest();
                    case 5 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error handling replenishment requests: " + e.getMessage());
            }
        }
    }

    /**
     * Views all replenishment requests in the system.
     */
    private void viewAllRequests() {
        try {
            System.out.println("\n--- All Replenishment Requests ---");
            admin.viewAllRequests();
        } catch (Exception e) {
            System.out.println("Error viewing requests: " + e.getMessage());
        }
    }

    /**
     * Views replenishment requests filtered by their status.
     */
    private void viewRequestsByStatus() {
        try {
            System.out.println("\n--- View Requests by Status ---");
            System.out.println("Select status:");
            System.out.println("1. PENDING");
            System.out.println("2. APPROVED");
            System.out.println("3. REJECTED");

            int statusChoice = InputHandler.getIntInput(1, 3);
            RequestStatus status = switch (statusChoice) {
                case 1 -> RequestStatus.PENDING;
                case 2 -> RequestStatus.APPROVED;
                case 3 -> RequestStatus.REJECTED;
                default -> throw new IllegalStateException("Invalid status selection");
            };

            admin.viewRequestsByStatus(status);
        } catch (Exception e) {
            System.out.println("Error viewing requests by status: " + e.getMessage());
        }
    }

    /**
     * Approves a replenishment request by its ID.
     */
    private void approveRequest() {
        try {
            System.out.println("\n--- Approve Replenishment Request ---");
            admin.viewRequestsByStatus(RequestStatus.PENDING);

            int requestId = InputHandler.getIntInput("Enter Request ID to approve: ", 1, Integer.MAX_VALUE);
            admin.approveRequest(requestId);
            System.out.println("Request approved successfully.");
        } catch (Exception e) {
            System.out.println("Error approving request: " + e.getMessage());
        }
    }

    /**
     * Rejects a replenishment request by its ID.
     */
    private void rejectRequest() {
        try {
            System.out.println("\n--- Reject Replenishment Request ---");
            admin.viewRequestsByStatus(RequestStatus.PENDING);

            int requestId = InputHandler.getIntInput("Enter Request ID to reject: ", 1, Integer.MAX_VALUE);
            admin.rejectRequest(requestId);
            System.out.println("Request rejected successfully.");
        } catch (Exception e) {
            System.out.println("Error rejecting request: " + e.getMessage());
        }
    }
}
