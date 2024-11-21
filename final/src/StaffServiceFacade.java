import java.util.List;

public class StaffServiceFacade {
    private static StaffServiceFacade instance;

    // Private constructor to enforce Singleton
    private StaffServiceFacade() {
    }

    /**
     * @return StaffServiceFacade
     */
    // Singleton access method
    public static StaffServiceFacade getInstance() {
        if (instance == null) {
            instance = new StaffServiceFacade();
        }
        return instance;
    }

    public boolean updateStaff(String staffID, String fieldToUpdate, String newValue) {
        return StaffList.getInstance(null).updateStaff(staffID, fieldToUpdate, newValue);
    }

    public void addStaff(User staffMember) {
        StaffList.getInstance(null).addStaff(staffMember);
        System.out.println("Staff added: " + staffMember);
    }

    public void removeStaff(String staffID) {
        boolean removed = StaffList.getInstance(null).removeStaff(staffID);
        if (removed) {
            System.out.println("Staff removed: " + staffID);
        } else {
            System.out.println("Staff not found: " + staffID);
        }
    }

    public User getStaffById(String staffID) {
        return StaffList.getInstance(null).getStaffById(staffID);
    }

    public List<User> getAllStaff() {
        return StaffList.getInstance(null).getStaff();
    }

    public List<User> filterStaff(String filterType, String filterValue) {
        return StaffList.getInstance(null).filterStaff(filterType, filterValue);
    }

    public void displayFilteredStaff(String filterType, String filterValue) {
        List<User> filteredStaff = filterStaff(filterType, filterValue);

        if (filteredStaff.isEmpty()) {
            System.out.println("No staff found matching the criteria.");
            return;
        }

        // Print header
        System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                "Staff ID", "Name", "Role", "Gender", "Age", "Contact Email", "Contact Number");

        System.out.println("-------------------------------------------------------------------------------");

        // Print each staff member
        for (User staff : filteredStaff) {
            String age = "";
            if (staff instanceof Doctor) {
                age = String.valueOf(((Doctor) staff).getAge());
            } else if (staff instanceof Pharmacist) {
                age = String.valueOf(((Pharmacist) staff).getAge());
            } else if (staff instanceof Admin) {
                age = String.valueOf(((Admin) staff).getAge());
            }

            System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                    staff.getUserID(),
                    staff.getName(),
                    staff.getRole(),
                    staff.getGender(),
                    age,
                    staff.getContactEmail(),
                    staff.getContactNumber());
        }
    }

    public void displayAllStaff() {
        List<User> allStaff = StaffList.getInstance(null).getStaff();

        if (allStaff.isEmpty()) {
            System.out.println("No staff found.");
            return;
        }

        // Print header
        System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                "Staff ID", "Name", "Role", "Gender", "Age", "Contact Email", "Contact Number");

        System.out.println("-------------------------------------------------------------------------------");

        // Print each staff member in tabular format
        for (User staffMember : allStaff) {
            String age = "";
            if (staffMember instanceof Doctor) {
                age = String.valueOf(((Doctor) staffMember).getAge());
            } else if (staffMember instanceof Pharmacist) {
                age = String.valueOf(((Pharmacist) staffMember).getAge());
            } else if (staffMember instanceof Admin) {
                age = String.valueOf(((Admin) staffMember).getAge());
            }

            System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                    staffMember.getUserID(),
                    staffMember.getName(),
                    staffMember.getRole(),
                    staffMember.getGender(),
                    age,
                    staffMember.getContactEmail(),
                    staffMember.getContactNumber());
        }
    }

}
