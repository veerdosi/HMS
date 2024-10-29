// Abstract User Class
public abstract class User {
    private String userID;
    private String password;
    private String name;
    private String contactNumber;
    private String email;
    private UserRole role;

    public User(String userID, String password, String name, String contactNumber, String email, UserRole role) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.role = role;
    }

    public boolean login(String userID, String password) {
        return this.userID.equals(userID) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateContactInfo(String email, String phone) {
        this.email = email;
        this.contactNumber = phone;
    }

    // Abstract method for role-specific actions
    public abstract void performRoleSpecificActions();

    // Getters for general user information
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }
}
