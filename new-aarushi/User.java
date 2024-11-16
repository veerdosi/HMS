
public abstract class User {
    protected String userId;
    protected String password;
    protected String contactNumber;
    protected String email;
    
    public User(String userId, String password, String contactNumber, String email) {
        this.userId = userId;
        this.password = password;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public abstract void displayMenu();
}
