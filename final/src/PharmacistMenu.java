/**
 * The `PharmacistMenu` class provides an interactive command-line interface (CLI) menu for pharmacists.
 * It allows pharmacists to view appointment outcomes, update prescription statuses, manage the medication inventory, 
 * submit replenishment requests, and reset their passwords.
 * This class integrates with other services and entities such as `Pharmacist`, `AppointmentOutcomeRecord`,
 * `MedicineInventory`, and `RequestRecord`.
 */
public class PharmacistMenu {
    private Pharmacist pharmacist; // The pharmacist using the menu
    private AppointmentOutcomeRecord outcomeRecord; // The record of appointment outcomes

    /**
     * Constructs a `PharmacistMenu` object for the specified pharmacist.
     *
     * @param pharmacist the `Pharmacist` object interacting with the menu.
     */
    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
    }

    /**
     * Displays the pharmacist menu and handles user interactions.
     * 
     * Provides options for viewing appointment outcomes, updating prescription statuses, managing
     * medication inventory, submitting replenishment requests, and resetting passwords.
     *
     * @return `false` when the pharmacist chooses to log out, allowing the program to terminate or return to a parent menu.
     */
    public boolean displayMenu() {
        while (true) {
            System.out.println("\n--- Pharmacist Menu ---");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Reset Password");
            System.out.println("6. Logout");

            try {
                int choice = InputHandler.getIntInput(1, 6);

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
                        resetPassword();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        return false; // Logout and exit the menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    /**
     * Resets the pharmacist's password.
     * 
     * Prompts the pharmacist to enter a new password and updates it.
     */
    private void resetPassword() {
        String newPass = InputHandler.getStringInput("New Password: ");
        Pharmacist p = this.pharmacist;
        p.changePassword(newPass);
    }

    /**
     * Displays the outcome of a specific appointment.
     * 
     * Prompts the user to enter an appointment ID, validates the input, and displays
     * the corresponding appointment outcome if found.
     */
    private void viewAppointmentOutcome() {
    try {
        System.out.println("Displaying all appointment outcomes:\n");
        outcomeRecord.displayAllAppointments(); // Call the method to display all appointments
    } catch (Exception e) {
        System.out.println("Error viewing appointment outcomes: " + e.getMessage());
    }
}

    /**
     * Updates the status of a prescription.
     * 
     * Prompts the user to provide prescription ID, medicine name, and the new status.
     * Validates the input and updates the prescription status if valid.
     */
    private void updatePrescriptionStatus() {
        try {
            String prescriptionID = InputHandler.getStringInput("Enter Prescription ID: ");

            if (!validateInput(prescriptionID)) {
                System.out.println("Invalid Prescription ID. Please try again.");
                return;
            }

            String medicineName = InputHandler.getStringInput("Enter Medicine Name: ");

            if (!validateInput(medicineName)) {
                System.out.println("Invalid Medicine Name. Please try again.");
                return;
            }

            System.out.println("Select new status:");
            System.out.println("1. PENDING");
            System.out.println("2. DISPENSED");
            System.out.println("3. CANCELLED");

            int statusChoice = InputHandler.getIntInput(1, 3);
            PrescriptionStatus status;
            switch (statusChoice) {
                case 1:
                    status = PrescriptionStatus.PENDING;
                    break;
                case 2:
                    status = PrescriptionStatus.DISPENSED;
                    break;
                case 3:
                    status = PrescriptionStatus.CANCELLED;
                    break;
                default:
                    System.out.println("Invalid status choice.");
                    return;
            }

            if(pharmacist.updatePrescriptionStatus(prescriptionID, medicineName, status)){
               // System.out.println("Prescription status updated successfully.");
            }
            else{
                System.out.println("Prescription status not updated - please retry with valid inputs.");
            }
        } catch (Exception e) {
            System.out.println("Error updating prescription status: " + e.getMessage());
        }
    }

    /**
     * Displays the medication inventory.
     * 
     * Fetches and displays all medicines along with their stock levels.
     * Also provides warnings for medicines low on stock.
     */
    private void viewMedicationInventory() {
        try {
            System.out.println("\n--- Medication Inventory ---");
            pharmacist.viewMedicineInventory();
        } catch (Exception e) {
            System.out.println("Error viewing medication inventory: " + e.getMessage());
        }
    }

    /**
     * Submits a replenishment request for a medicine.
     * 
     * Prompts the user to provide the medicine name and quantity, validates the input,
     * and submits a replenishment request.
     */
    private void submitReplenishmentRequest() {
        try {
            String medicineName = InputHandler.getStringInput("Enter Medicine Name: ");

            if (!validateInput(medicineName)) {
                System.out.println("Invalid Medicine Name. Please try again.");
                return;
            }

            int quantity = InputHandler.getIntInput("Enter Quantity to Replenish (1-1000): ", 1, 1000);

            pharmacist.submitReplenishmentRequest(medicineName, quantity);
            System.out.println("Replenishment request submitted successfully.");
        } catch (Exception e) {
            System.out.println("Error submitting replenishment request: " + e.getMessage());
        }
    }

    /**
     * Validates user input to ensure it is not null or empty.
     * 
     * @param input the input string to validate.
     * @return `true` if the input is valid, otherwise `false`.
     */
    private boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
