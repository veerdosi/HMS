import java.util.Scanner;

public class PharmacistMenu {
    private Pharmacist pharmacist;
    private Scanner scanner = new Scanner(System.in);

    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public boolean display() {
        while (true) {
            System.out.println("---- Pharmacist Menu ----");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
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
                    return false; // Return false to signal logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAppointmentOutcomeRecords() {
        // Implement view appointment outcome records
    }

    private void updatePrescriptionStatus() {
        // Implement update prescription status
    }

    private void viewMedicationInventory() {
        // Implement view medication inventory
    }

    private void submitReplenishmentRequest() {
        // Implement submit replenishment request
    }
}
