public class Doctor extends User {
    private int age;

    public Doctor(String userID, String name, String password,
                 int age, String gender) {
        super(userID, name, password, UserRole.DOCTOR, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
