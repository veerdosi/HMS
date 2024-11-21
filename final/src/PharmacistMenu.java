import java.util.Scanner;

public class PharmacistMenu {
    private Pharmacist pharmacist;
    private AppointmentOutcomeRecord outcomeRecord; // Singleton for appointment outcomes
    private Scanner scanner; // Shared scanner instance

    // Constructor
    public PharmacistMenu(Pharmacist pharmacist, Scanner scanner) {
        this.pharmacist = pharmacist;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
        this.scanner = scanner; // Shared scanner passed in
    }

    /**
     * Displays the Pharmacist Menu and handles interactions.
     * 
     * @return boolean indicating successful execution or logout.
     */
    public boolean displayMenu() {
        while (true) {
            try {
                System.out.println("\nPharmacist Menu:");
                System.out.println("1. View Appointment Outcome Record");
                System.out.println("2. Update Prescription Status");
                System.out.println("3. View Medication Inventory");
                System.out.println("4. Submit Replenishment Request");
                System.out.println("5. Logout");
                System.out.print("Please select an option: ");

                // Read and validate menu choice
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    scanner.next(); // Consume invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (!processChoice(choice)) {
                    return false; // Exit menu if the user selects "Logout"
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input to avoid infinite loops
            }
        }
    }

    // Process menu choices
    private boolean processChoice(int choice) {
        switch (choice) {
            case 1:
                viewAppointmentOutcome();
                break;
            case 2:
                updatePrescriptionStatus();
                break;
            case 3:
                viewMedicationInventory();
                break;
            case 4:
                submitReplenishmentRequest();
                break;
            case 5:
                System.out.println("Logging out...");
                return false; // Exit menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true; // Continue showing the menu
    }

    // View Appointment Outcome Record
    private void viewAppointmentOutcome() {
        String appointmentID = readStringInput("Enter Appointment ID: ");
        if (validateInput(appointmentID)) {
            Appointment appointment = outcomeRecord.getAppointmentById(appointmentID);
            if (appointment != null) {
                System.out.println("Appointment Outcome Record:");
                System.out.println(appointment);
            } else {
                System.out.println("No outcome record found for the provided Appointment ID.");
            }
        } else {
            System.out.println("Invalid Appointment ID. Please try again.");
        }
    }

    // Update Prescription Status
    private void updatePrescriptionStatus() {
        String prescriptionID = readStringInput("Enter Prescription ID: ");
        if (!validateInput(prescriptionID)) {
            System.out.println("Invalid Prescription ID. Please try again.");
            return;
        }

        String medicineName = readStringInput("Enter Medicine Name: ");
        if (!validateInput(medicineName)) {
            System.out.println("Invalid Medicine Name. Please try again.");
            return;
        }

        String statusStr = readStringInput("Enter new status (PENDING, DISPENSED, CANCELLED): ");
        if (validateEnumInput(statusStr, PrescriptionStatus.class)) {
            PrescriptionStatus status = PrescriptionStatus.valueOf(statusStr.toUpperCase());
            pharmacist.updatePrescriptionStatus(prescriptionID, medicineName, status);
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Invalid status. Please enter a valid status.");
        }
    }

    // View Medication Inventory
    private void viewMedicationInventory() {
        System.out.println("\n--- Medication Inventory ---");
        pharmacist.viewMedicineInventory();
    }

    // Submit Replenishment Request
    private void submitReplenishmentRequest() {
        String medicineName = readStringInput("Enter Medicine Name: ");
        if (!validateInput(medicineName)) {
            System.out.println("Invalid Medicine Name. Please try again.");
            return;
        }

        System.out.print("Enter Quantity to Replenish: ");
        if (scanner.hasNextInt()) {
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (quantity > 0) {
                pharmacist.submitReplenishmentRequest(medicineName, quantity);
                System.out.println("Replenishment request submitted successfully.");
            } else {
                System.out.println("Quantity must be greater than zero.");
            }
        } else {
            System.out.println("Invalid input for quantity. Please enter a positive number.");
            scanner.nextLine(); // Consume invalid input
        }
    }

    // Helper method: Validate general input
    private boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Helper method: Validate input against an enum
    private <T extends Enum<T>> boolean validateEnumInput(String input, Class<T> enumClass) {
        try {
            Enum.valueOf(enumClass, input.toUpperCase());
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

    // Helper method: Read string input with a prompt
    private String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}