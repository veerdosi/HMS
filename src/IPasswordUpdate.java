<<<<<<< Updated upstream
/**
 * The `IPasswordUpdate` interface defines a contract for objects
 * that support password update functionality.
 *
 * Implementing classes are expected to provide their own logic for updating passwords.
 * This interface ensures a standardized method signature for changing passwords.
 *
 */
public interface IPasswordUpdate {

    /**
     * Updates the password for the implementing object.
     *
     * @param newPass The new password to set. It must adhere to the specific
     *                password requirements of the implementing class.
=======
/** @author Revathi Selvasevaran
 * The `IPasswordUpdate` interface defines a contract for objects
 * that support password update functionality.
 */
public interface IPasswordUpdate {
    /**
     * Updates the password for the implementing object.
     *
     * @param newPass The new password to set.
>>>>>>> Stashed changes
     */
    void changePassword(String newPass);
}
