
public class Staff {
    private String staffID;
    private String name;
    private String role; // Doctor or Pharmacist
    private String contactInfo;

    public Staff(String staffID, String name, String role, String contactInfo) {
        this.staffID = staffID;
        this.name = name;
        this.role = role;
        this.contactInfo = contactInfo;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String toString() {
        return "ID: " + staffID + ", Name: " + name + ", Role: " + role + ", Contact: " + contactInfo;
    }
}
