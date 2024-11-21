import java.util.List;
/**
 * The `StaffServiceFacade` class provides a centralized interface for managing
 * hospital staff, including adding, updating, removing, and retrieving staff details.
 * It follows the Singleton design pattern to ensure a single instance is used throughout the application.
 */
public class StaffServiceFacade {
    private static StaffServiceFacade instance;

    /**
     * Private constructor to enforce the Singleton design pattern.
     */
    private StaffServiceFacade() {
    }

    /**
     * Provides the Singleton instance of the `StaffServiceFacade`.
     *
     * @return The Singleton instance of the `StaffServiceFacade`.
     */
    public static StaffServiceFacade getInstance() {
        if (instance == null) {
            instance = new StaffServiceFacade();
        }
        return instance;
    }

    /**
     * Updates a specific field of a staff member's record.
     *
     * @param staffID       The ID of the staff member to update.
     * @param fieldToUpdate The field to update (e.g., "Name", "Email").
     * @param newValue      The new value for the field.
     * @return `true` if the update was successful, otherwise `false`.
     */
    public boolean updateStaff(String staffID, String fieldToUpdate, String newValue) {
        return StaffList.getInstance(null).updateStaff(staffID, fieldToUpdate, newValue);
    }

    /**
     * Adds a new staff member to the system.
     *
     * @param staffMember The staff member to add.
     */
    public void addStaff(User staffMember) {
        StaffList.getInstance(null).addStaff(staffMember);
        System.out.println("Staff added: " + staffMember);
    }

    /**
     * Removes a staff member from the system by their ID.
     *
     * @param staffID The ID of the staff member to remove.
     */
    public void removeStaff(String staffID) {
        boolean removed = StaffList.getInstance(null).removeStaff(staffID);
        if (removed) {
            System.out.println("Staff removed: " + staffID);
        } else {
            System.out.println("Staff not found: " + staffID);
        }
    }

    /**
     * Retrieves a staff member's details by their ID.
     *
     * @param staffID The ID of the staff member.
     * @return The `User` object representing the staff member, or `null` if not found.
     */
    public User getStaffById(String staffID) {
        return StaffList.getInstance(null).getStaffById(staffID);
    }

    /**
     * Retrieves a list of all staff members.
     *
     * @return A list of all `User` objects representing staff members.
     */
    public List<User> getAllStaff() {
        return StaffList.getInstance(null).getStaff();
    }

    /**
     * Filters staff members based on specific criteria.
     *
     * @param filterType  The type of filter to apply (e.g., "Role", "Gender").
     * @param filterValue The value to filter by.
     * @return A list of staff members matching the filter criteria.
     */
    public List<User> filterStaff(String filterType, String filterValue) {
        return StaffList.getInstance(null).filterStaff(filterType, filterValue);
    }

    /**
     * Displays staff members that match specific filter criteria in a tabular format.
     *
     * @param filterType  The type of filter to apply (e.g., "Role", "Gender").
     * @param filterValue The value to filter by.
     */
    public void displayFilteredStaff(String filterType, String filterValue) {
        List<User> filteredStaff = filterStaff(filterType, filterValue);
        if (filteredStaff.isEmpty()) {
            System.out.println("No staff found matching the criteria.");
            return;
        }

        // Print filtered staff in a tabular format
        System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                "Staff ID", "Name", "Role", "Gender", "Age", "Contact Email", "Contact Number");
        System.out.println("-------------------------------------------------------------------------------");

        for (User staff : filteredStaff) {
            String age = extractAge(staff);
            System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                    staff.getUserID(), staff.getName(), staff.getRole(), staff.getGender(), age,
                    staff.getContactEmail(), staff.getContactNumber());
        }
    }

    /**
     * Displays all staff members in a tabular format.
     */
    public void displayAllStaff() {
        List<User> allStaff = StaffList.getInstance(null).getStaff();
        if (allStaff.isEmpty()) {
            System.out.println("No staff found.");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                "Staff ID", "Name", "Role", "Gender", "Age", "Contact Email", "Contact Number");
        System.out.println("-------------------------------------------------------------------------------");

        for (User staff : allStaff) {
            String age = extractAge(staff);
            System.out.printf("%-10s %-20s %-15s %-10s %-5s %-30s %-15s\n",
                    staff.getUserID(), staff.getName(), staff.getRole(), staff.getGender(), age,
                    staff.getContactEmail(), staff.getContactNumber());
        }
    }

    /**
     * Extracts the age of a staff member if applicable.
     *
     * @param staff The staff member.
     * @return The age as a string, or an empty string if not applicable.
     */
    private String extractAge(User staff) {
        if (staff instanceof Doctor) {
            return String.valueOf(((Doctor) staff).getAge());
        } else if (staff instanceof Pharmacist) {
            return String.valueOf(((Pharmacist) staff).getAge());
        } else if (staff instanceof Admin) {
            return String.valueOf(((Admin) staff).getAge());
        }
        return "";
    }
}
