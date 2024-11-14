public class Pharmacist extends User {
    private int age;

    public Pharmacist(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.PHARMACIST, gender, contactEmail, contactNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
