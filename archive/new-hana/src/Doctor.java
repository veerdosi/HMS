public class Doctor extends User {
    private int age;

    public Doctor(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.DOCTOR, gender, contactEmail, contactNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
