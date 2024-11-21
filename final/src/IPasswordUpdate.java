/**
 * The `IPasswordUpdate` interface defines a contract for objects
 * that support password update functionality.
 */
public interface IPasswordUpdate {
    /**
     * Updates the password for the implementing object.
     *
     * @param newPass The new password to set.
     */
    void changePassword(String newPass);
}
