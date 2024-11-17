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
    public void displayMenu() {
        int choice = -1;
        while (choice != 5) {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View Appointment Outcome Record
    private void viewAppointmentOutcome() {
        System.out.print("Enter Appointment ID: ");
        String appointmentID = scanner.nextLine();
        pharmacist.viewAppointmentOutcome(appointmentID);
    }

    // Update Prescription Status
    private void updatePrescriptionStatus() {
        System.out.print("Enter Prescription ID: ");
        String prescriptionID = scanner.nextLine();
        System.out.print("Enter new status (PENDING, DISPENSED, CANCELLED): ");
        String statusStr = scanner.nextLine().toUpperCase();
        try {
            PrescriptionStatus status = PrescriptionStatus.valueOf(statusStr);
            pharmacist.updatePrescriptionStatus(prescriptionID, status);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Please enter a valid status.");
        }
    }

    // View Medication Inventory
    private void viewMedicationInventory() {
        pharmacist.viewMedicationInventory();
    }

    // Submit Replenishment Request
    private void submitReplenishmentRequest() {
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine();
        System.out.print("Enter Quantity to Replenish: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        pharmacist.submitReplenishmentRequest(medicineName, quantity);
    }
}


