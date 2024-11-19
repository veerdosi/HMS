public class Pharmacist extends User {
    private int age;

    public Pharmacist(String userID, String name, String password,
                 int age, String gender) {
        super(userID, name, password, UserRole.PHARMACIST, gender);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
