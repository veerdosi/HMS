public class Admin extends User {
    private int age;

    public Admin(String userID, String password, String contactNumber, String email,
                 int age, String gender) {
        super(userID, password, contactNumber, email, UserRole.ADMIN, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
