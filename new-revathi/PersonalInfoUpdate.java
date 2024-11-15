package HMSpkg;

public interface PersonalInfoUpdate {
    public void updateContactNumber(String newContact);

    public void updateEmailAddress(String newEmail);

    public void updateContactInfo (String email, String contactNum);

    public boolean changePassword(String oldPass, String newPass);
}
