package old;

public abstract class User {
    private String userID;
    private String password;
    private String name;
    private String contactNumber;
    private String email;
    private UserRole role;
    private boolean firstLogin = true; // Track if this is the first login

    // Constructor
    public User(String userID, String password, String name, String contactNumber, String email, UserRole role) {
        this.userID = userID;
        this.password = (password != null) ? password : "password"; // Default password
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.role = role;
    }

    // Login method to validate userID and password
    public boolean login(String userID, String password) {
        boolean isValid = this.userID.equals(userID) && this.password.equals(password);
        if (isValid && firstLogin) {
            System.out.println("Welcome! Please change your password for security.");
        }
        return isValid;
    }

    // Password change logic
    public void changePassword(String newPassword) {
        if (firstLogin) {
            this.password = newPassword;
            this.firstLogin = false;
            System.out.println("Password changed successfully! You can now log in with the new password.");
        } else {
            this.password = newPassword;
            System.out.println("Password updated successfully.");
        }
    }

    // Update contact information (non-medical data)
    public void updateContactInfo(String email, String phone) {
        this.email = email;
        this.contactNumber = phone;
        System.out.println("Contact information updated successfully.");
    }

    // Abstract method for role-specific actions to be implemented by subclasses
    public abstract void performRoleSpecificActions();

    // Getters for user attributes
    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }
}
