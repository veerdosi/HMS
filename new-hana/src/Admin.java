public class Admin extends User {
    private int age;

    public Admin(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.ADMIN, gender, contactEmail, contactNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
