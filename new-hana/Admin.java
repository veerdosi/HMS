public class Admin extends User {
    private int age;

    public Admin(String userID, String name, String password,
                 int age, String gender) {
        super(userID, name, password, UserRole.ADMIN, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
