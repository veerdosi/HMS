import java.util.Scanner;

public class PharmacistMenu {
    private Pharmacist pharmacist;
    private AppointmentOutcomeRecord outcomeRecord;
    private Scanner scanner;

    // Constructor
    public PharmacistMenu(Pharmacist pharmacist, Scanner scanner) {
        this.pharmacist = pharmacist;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
        this.scanner = scanner;
    }

    public boolean displayMenu() {
        while (true) {
            try {
                System.out.println("\nPharmacist Menu:");
                System.out.println("1. View Appointment Outcome Record");
                System.out.println("2. Update Prescription Status");
                System.out.println("3. View Medication Inventory");
                System.out.println("4. Submit Replenishment Request");
                System.out.println("5. Logout");

                int choice = InputHandler.getIntInput(1, 5);

                switch (choice) {
                    case 1 -> viewAppointmentOutcome();
                    case 2 -> updatePrescriptionStatus();
                    case 3 -> viewMedicationInventory();
                    case 4 -> submitReplenishmentRequest();
                    case 5 -> {
                        System.out.println("Logging out...");
                        return false;
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the buffer
            }
        }
    }

    private void viewAppointmentOutcome() {
        try {
            System.out.print("Enter Appointment ID: ");
            String appointmentID = scanner.nextLine();

            if (!validateInput(appointmentID)) {
                System.out.println("Invalid Appointment ID. Please try again.");
                return;
            }

            Appointment appointment = outcomeRecord.getAppointmentById(appointmentID);
            if (appointment != null) {
                System.out.println("Appointment Outcome Record:");
                System.out.println(appointment);
            } else {
                System.out.println("No outcome record found for the provided Appointment ID.");
            }
        } catch (Exception e) {
            System.out.println("Error viewing appointment outcome: " + e.getMessage());
        }
    }

    private void updatePrescriptionStatus() {
        try {
            System.out.print("Enter Prescription ID: ");
            String prescriptionID = scanner.nextLine();

            if (!validateInput(prescriptionID)) {
                System.out.println("Invalid Prescription ID. Please try again.");
                return;
            }

            System.out.print("Enter Medicine Name: ");
            String medicineName = scanner.nextLine();

            if (!validateInput(medicineName)) {
                System.out.println("Invalid Medicine Name. Please try again.");
                return;
            }

            System.out.println("Select new status:");
            System.out.println("1. PENDING");
            System.out.println("2. DISPENSED");
            System.out.println("3. CANCELLED");

            int statusChoice = InputHandler.getIntInput(1, 3);
            PrescriptionStatus status = switch (statusChoice) {
                case 1 -> PrescriptionStatus.PENDING;
                case 2 -> PrescriptionStatus.DISPENSED;
                case 3 -> PrescriptionStatus.CANCELLED;
                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
            };

            pharmacist.updatePrescriptionStatus(prescriptionID, medicineName, status);
            System.out.println("Prescription status updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating prescription status: " + e.getMessage());
        }
    }

    private void viewMedicationInventory() {
        try {
            System.out.println("\n--- Medication Inventory ---");
            pharmacist.viewMedicineInventory();
        } catch (Exception e) {
            System.out.println("Error viewing medication inventory: " + e.getMessage());
        }
    }

    private void submitReplenishmentRequest() {
        try {
            System.out.print("Enter Medicine Name: ");
            String medicineName = scanner.nextLine();

            if (!validateInput(medicineName)) {
                System.out.println("Invalid Medicine Name. Please try again.");
                return;
            }

            System.out.print("Enter Quantity to Replenish: ");
            int quantity = InputHandler.getIntInput(1, 1000);

            pharmacist.submitReplenishmentRequest(medicineName, quantity);
            System.out.println("Replenishment request submitted successfully.");
        } catch (Exception e) {
            System.out.println("Error submitting replenishment request: " + e.getMessage());
        }
    }

    // Helper method to validate input
    private boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }
}