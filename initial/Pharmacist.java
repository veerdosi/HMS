public class Pharmacist extends User {
    private int age;

    public Pharmacist(String userID, String password, String contactNumber, String email,
                      int age, String gender) {
        super(userID, password, contactNumber, email, UserRole.PHARMACIST, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
