import java.util.regex.Pattern;

/**
 * The `PatientInfoUpdater` class provides functionality to update a patient's
 * personal information, including their contact number and email address.
 * It validates input and interacts with objects implementing the
 * `IPersonalInfoUpdate` interface.
 *
 * This class is designed to ensure input validation and proper interaction with
 * entities that allow personal information updates.
 *
 * Author: Revathi Selvasevaran
 */
public class PatientInfoUpdater {

    /**
     * Displays a menu for updating personal information.
     * The menu allows the user to update their contact number, email address, or both.
     *
     * @param infoUpdater An object implementing the `IPersonalInfoUpdate`
     *                    interface, representing the entity whose information
     *                    is to be updated.
     */
    public void displayUpdateInfoMenu(IPersonalInfoUpdate infoUpdater) {
        while (true) {
            System.out.println("\n---- Update Personal Information ----");
            System.out.println("1. Update Contact Number");
            System.out.println("2. Update Email Address");
            System.out.println("3. Update Both");
            System.out.println("4. Back to Main Menu");

            int choice = InputHandler.getIntInput(1, 4);

            switch (choice) {
                case 1:
                    updateContactNumber(infoUpdater);
                    break;
                case 2:
                    updateEmailAddress(infoUpdater);
                    break;
                case 3:
                    updateBothContactInfo(infoUpdater);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    /**
     * Updates the contact number of the provided entity.
     * Validates the input before updating.
     *
     * @param updater An object implementing the `IPersonalInfoUpdate` interface.
     */
    private void updateContactNumber(IPersonalInfoUpdate updater) {
        String newContact = InputHandler.getStringInput("Enter new contact number (8 digits): ");
        if (isValidContactNumber(newContact)) {
            updater.updateContactNumber(newContact);
            System.out.println("Contact number updated successfully.");
        } else {
            System.out.println("Invalid contact number. It must be exactly 8 digits.");
        }
    }

    /**
     * Updates the email address of the provided entity.
     * Validates the input before updating.
     *
     * @param updater An object implementing the `IPersonalInfoUpdate` interface.
     */
    private void updateEmailAddress(IPersonalInfoUpdate updater) {
        String newEmail = InputHandler.getStringInput("Enter new email address: ");
        if (isValidEmailAddress(newEmail)) {
            updater.updateEmailAddress(newEmail);
            System.out.println("Email address updated successfully.");
        } else {
            System.out.println("Invalid email address format.");
        }
    }

    /**
     * Updates both the contact number and email address of the provided entity.
     * Validates both inputs before updating.
     *
     * @param updater An object implementing the `IPersonalInfoUpdate` interface.
     */
    private void updateBothContactInfo(IPersonalInfoUpdate updater) {
        String newContact = InputHandler.getStringInput("Enter new contact number (8 digits): ");
        String newEmail = InputHandler.getStringInput("Enter new email address: ");

        boolean isContactValid = isValidContactNumber(newContact);
        boolean isEmailValid = isValidEmailAddress(newEmail);

        if (isContactValid && isEmailValid) {
            updater.updateContactInfo(newEmail, newContact);
            System.out.println("Contact information updated successfully.");
        } else {
            if (!isContactValid) {
                System.out.println("Invalid contact number. It must be exactly 8 digits.");
            }
            if (!isEmailValid) {
                System.out.println("Invalid email address format.");
            }
        }
    }

    /**
     * Validates if a contact number is valid (must be exactly 8 digits).
     *
     * @param contactNumber The contact number to validate.
     * @return `true` if valid, `false` otherwise.
     */
    private boolean isValidContactNumber(String contactNumber) {
        String regex = "^[0-9]{8}$";
        return Pattern.matches(regex, contactNumber);
    }

    /**
     * Validates if an email address is in a valid format.
     *
     * @param email The email address to validate.
     * @return `true` if valid, `false` otherwise.
     */
    private boolean isValidEmailAddress(String email) {
        String regex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, email);
    }
}
