//COMPLETE
public interface IPersonalInfoUpdate {
    void updateContactNumber(String newContactNumber);

    void updateEmailAddress(String newEmailAddress);

    default void updateContactInfo(String newEmailAddress, String newContactNumber) {
        updateContactNumber(newContactNumber);
        updateEmailAddress(newEmailAddress);
    }

    public boolean changePassword(String oldPass, String newPass);
}
