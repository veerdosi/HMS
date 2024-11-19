import java.util.UUID;

public class Staff {
    private String id;
    private String name;
    private String role; // E.g., "Doctor", "Pharmacist"
    private int age;
    private String gender;

    // Constructor
    public Staff(String name, String role, int age, String gender) {
        this.id = UUID.randomUUID().toString(); // Generate unique ID
        this.name = name;
        this.role = role;
        this.age = age;
        this.gender = gender;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    // Update staff details (for simplicity, we accept a comma-separated string)
    public void updateDetails(String newDetails) {
        String[] details = newDetails.split(",");
        if (details.length == 4) {
            this.name = details[0];
            this.role = details[1];
            this.age = Integer.parseInt(details[2]);
            this.gender = details[3];
        } else {
            System.out.println("Invalid format for updating staff details. Expected: Name,Role,Age,Gender");
        }
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}