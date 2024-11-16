import java.util.List;
import java.util.Scanner;

public class PharmacistMenu {
    private Pharmacist pharmacist;

    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 5) {
            System.out.println("\n--- Pharmacist Menu ---");
            System.out.println("1. View Appointment Outcome Records");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecords();
                    break;
                case 2:
                    updatePrescriptionStatus(scanner);
                    break;
                case 3:
                    viewMedicationInventory();
                    break;
                case 4:
                    submitReplenishmentRequest(scanner);
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void viewAppointmentOutcomeRecords() {
        System.out.println("\n--- Viewing Appointment Outcome Records ---");
        List<AppointmentOutcomeRecord> records = pharmacist.viewAppointmentOutcomeRecords();
        if (records == null || records.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (AppointmentOutcomeRecord record : records) {
                record.displayDetails();
            }
        }
    }

    private void updatePrescriptionStatus(Scanner scanner) {
        System.out.println("\n--- Update Prescription Status ---");
        System.out.print("Enter Prescription ID: ");
        String prescriptionID = scanner.nextLine();

        Prescription prescription = new Prescription(prescriptionID, "M001", "P001", "pending", "2024-11-16");

        if (prescription != null) {
            System.out.println("Updating status for Prescription ID: " + prescription.getPrescriptionID());
            pharmacist.updatePrescriptionStatus(prescription);
            System.out.println("Prescription status updated to 'dispensed'.");
        } else {
            System.out.println("Prescription not found.");
        }
    }

    private void viewMedicationInventory() {
        System.out.println("\n--- Viewing Medication Inventory ---");
        List<Medicine> inventory = pharmacist.viewMedicationInventory();
        if (inventory == null || inventory.isEmpty()) {
            System.out.println("No medications found in inventory.");
        } else {
            for (Medicine medicine : inventory) {
                medicine.displayDetails();
            }
        }
    }

    private void submitReplenishmentRequest(Scanner scanner) {
        System.out.println("\n--- Submit Replenishment Request ---");
        System.out.print("Enter Medicine ID: ");
        String medicineID = scanner.nextLine();
        System.out.print("Enter Quantity to Replenish: ");
        int quantity = scanner.nextInt();

        Medicine medicine = new Medicine(medicineID, "Paracetamol", 100, 20);

        if (medicine != null) {
            pharmacist.submitReplenishmentRequest(medicine, quantity);
            System.out.println("Replenishment request submitted for Medicine ID: " + medicineID + ".");
        } else {
            System.out.println("Medicine not found.");
        }
    }

    private void logout() {
        System.out.println("Logging out...");
        pharmacist.logout();
        System.out.println("Logged out successfully.");
    }
}
