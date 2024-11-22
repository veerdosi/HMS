/**@author Revathi Selvasevaran
 * The `IPersonalInfoUpdate` interface provides a contract for objects that allow
 * personal contact information updates, such as email and phone number.
 */
public interface IPersonalInfoUpdate {
    /**
     * Updates the contact number.
     *
     * @param newContactNumber The new contact number to set.
     */
    void updateContactNumber(String newContactNumber);

    /**
     * Updates the email address.
     *
     * @param newEmailAddress The new email address to set.
     */
    void updateEmailAddress(String newEmailAddress);

    /**
     * Updates both the contact number and email address.
     *
     * @param newEmailAddress   The new email address to set.
     * @param newContactNumber The new contact number to set.
     */
    default void updateContactInfo(String newEmailAddress, String newContactNumber) {
        updateContactNumber(newContactNumber);
        updateEmailAddress(newEmailAddress);
    }
}
