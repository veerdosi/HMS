/**
 * The `IPersonalInfoUpdate` interface provides a contract for objects that allow
 * personal contact information updates, such as email and phone number.
 *
 * Implementing classes are expected to provide logic for updating individual contact details
 * as well as a method to update both email and contact number together.
 *
 */
public interface IPersonalInfoUpdate {

    /**
     * Updates the contact number for the implementing object.
     *
     * @param newContactNumber The new contact number to set.
     */
    void updateContactNumber(String newContactNumber);

    /**
     * Updates the email address for the implementing object.
     *
     * @param newEmailAddress The new email address to set.
     */
    void updateEmailAddress(String newEmailAddress);

    /**
     * Updates both the contact number and the email address.
     * This default implementation calls `updateContactNumber` and `updateEmailAddress`
     * methods for the updates.
     *
     * @param newEmailAddress The new email address to set.
     * @param newContactNumber The new contact number to set.
     */
    default void updateContactInfo(String newEmailAddress, String newContactNumber) {
        updateContactNumber(newContactNumber);
        updateEmailAddress(newEmailAddress);
    }
}
