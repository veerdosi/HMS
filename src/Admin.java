import java.util.List;

/**
 * The `Admin` class extends the `User` class and represents an administrator in the system.
 * Administrators can manage staff, approve or reject replenishment requests, and oversee
 * inventory-related operations.
 */
public class Admin extends User {
    private int age;

    /**
     * Constructs an `Admin` object with the specified attributes.
     *
     * @param userID        The unique identifier for the admin.
     * @param name          The name of the admin.
     * @param password      The admin's password.
     * @param gender        The gender of the admin.
     * @param contactEmail  The admin's contact email.
     * @param contactNumber The admin's contact number.
     * @param age           The age of the admin.
     */
    public Admin(String userID, String name, String password, String gender, String contactEmail, String contactNumber,
                 int age) {
        super(userID, name, password, UserRole.Administrator, gender, contactEmail, contactNumber);
        this.age = age;
    }

    /**
     * Gets the age of the admin.
     *
     * @return The admin's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the admin.
     *
     * @param age The new age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Updates the details of a staff member.
     *
     * @param staffID       The unique identifier of the staff member to update.
     * @param fieldToUpdate The field to update (e.g., name, contact).
     * @param newValue      The new value to set for the field.
     */
    public void updateStaff(String staffID, String fieldToUpdate, String newValue) {
        boolean success = StaffServiceFacade.getInstance().updateStaff(staffID, fieldToUpdate, newValue);
        if (!success) {
            System.out.println("Failed to update staff details.");
        }
    }

    /**
     * Adds a new staff member to the system.
     *
     * @param staffMember The staff member to add.
     */
    public void addStaff(User staffMember) {
        StaffServiceFacade.getInstance().addStaff(staffMember);
    }

    /**
     * Removes a staff member from the system.
     *
     * @param staffID The unique identifier of the staff member to remove.
     */
    public void removeStaff(String staffID) {
        StaffServiceFacade.getInstance().removeStaff(staffID);
    }

    /**
     * Retrieves and displays the details of a staff member.
     *
     * @param staffID The unique identifier of the staff member to retrieve.
     */
    public void viewStaffDetails(String staffID) {
        User staff = StaffServiceFacade.getInstance().getStaffById(staffID);
        if (staff != null) {
            System.out.println("Staff Details:\n" + staff);
        } else {
            System.out.println("No staff member found with ID: " + staffID);
        }
    }

    /**
     * Displays a filtered list of staff based on the specified filter type and value.
     *
     * @param filterType  The type of filter (e.g., role, department).
     * @param filterValue The value to filter by.
     */
    public void displayFilteredStaff(String filterType, String filterValue) {
        StaffServiceFacade.getInstance().displayFilteredStaff(filterType, filterValue);
    }

    /**
     * Displays all staff members in the system.
     */
    public void displayAllStaff() {
        StaffServiceFacade.getInstance().displayAllStaff();
    }

    /**
     * Approves a replenishment request and updates the medicine inventory.
     *
     * @param requestId The unique identifier of the replenishment request to approve.
     */
    public void approveRequest(int requestId) {
        ReplenishmentRequest request = RequestRecord.getInstance().getRequestById(requestId);
        if (request != null && request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.APPROVED);

            // Access MedicineInventory Singleton directly here
            MedicineInventory.getInstance(null).updateStock(request.getMedicineName(), request.getRequestedQuantity());

            System.out.println("Request approved: " + request);
        } else {
            System.out.println("Request ID " + requestId + " not found or already processed.");
        }
    }

    /**
     * Rejects a replenishment request.
     *
     * @param requestId The unique identifier of the replenishment request to reject.
     */
    public void rejectRequest(int requestId) {
        ReplenishmentRequest request = RequestRecord.getInstance().getRequestById(requestId);
        if (request != null && request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.REJECTED);
            System.out.println("Request rejected: " + request);
        } else {
            System.out.println("Request ID " + requestId + " not found or already processed.");
        }
    }

    /**
     * Displays all replenishment requests in the system.
     */
    public void viewAllRequests() {
        List<ReplenishmentRequest> requests = RequestRecord.getInstance().getAllRequests();
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests found.");
            return;
        }
        System.out.println("All replenishment requests:");
        for (ReplenishmentRequest request : requests) {
            System.out.println(request);
        }
    }

    /**
     * Displays replenishment requests filtered by their status.
     *
     * @param status The status to filter requests by (e.g., PENDING, APPROVED).
     */
    public void viewRequestsByStatus(RequestStatus status) {
        List<ReplenishmentRequest> requests = RequestRecord.getInstance().getRequestsByStatus(status);
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests with status: " + status);
            return;
        }
        System.out.println("Replenishment requests with status: " + status);
        for (ReplenishmentRequest request : requests) {
            System.out.println(request);
        }
    }

    /**
     * Displays a list of medicines that are low on stock.
     */
    public void viewLowStockMedicines() {
        System.out.println("Medicines low on stock:");
        MedicineInventory.getInstance(null).checkLowStock();
    }

    /**
     * Returns a string representation of the `Admin` object, including its attributes.
     *
     * @return A string containing the admin's details.
     */
    @Override
    public String toString() {
        return "ID: " + userID +
                ", Name: " + name +
                ", Role: " + role +
                ", Gender: " + gender +
                ", Age: " + age +
                ", Email: " + contactEmail +
                ", Contact: " + contactNumber;
    }
}
