public class Doctor extends User {
    private int age;

    public Doctor(String userID, String password, String contactNumber, String email,
                  int age, String gender) {
        super(userID, password, contactNumber, email, UserRole.DOCTOR, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
