import java.util.Scanner;

public class PharmacistMenu {
    private Pharmacist pharmacist;
    private Scanner scanner;

    // Constructor
    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.scanner = new Scanner(System.in);
    }

    // Display Pharmacist Menu
    public boolean displayMenu() {
        int choice = -1;
        while (choice != 5) {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
    
            choice = readMenuChoice();
            if (!processChoice(choice)) {
                return false; // Exit if processChoice signals to stop
            }
        }
        return true; // Return true to indicate successful execution
    }
    
    // Process menu choices and return boolean
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
                return false; // Signal to exit menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true; // Continue showing menu
    }
    
    // Read menu choice (helper method for input validation)
    private int readMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    

    // View Appointment Outcome Record
    private void viewAppointmentOutcome() {
        String appointmentID = readStringInput("Enter Appointment ID: ");
        if (validateInput(appointmentID)) {
            pharmacist.viewAppointmentOutcome(appointmentID);
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

        String statusStr = readStringInput("Enter new status (PENDING, DISPENSED, CANCELLED): ");
        if (validateEnumInput(statusStr, PrescriptionStatus.class)) {
            PrescriptionStatus status = PrescriptionStatus.valueOf(statusStr.toUpperCase());
            pharmacist.updatePrescriptionStatus(prescriptionID, status);
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Invalid status. Please enter a valid status.");
        }
    }

    // View Medication Inventory
    private void viewMedicationInventory() {
        pharmacist.viewMedicationInventory();
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

    // Input validation methods
    private boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    private boolean validateEnumInput(String input, Class<? extends Enum<?>> enumClass) {
        try {
            Enum.valueOf(enumClass, input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

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