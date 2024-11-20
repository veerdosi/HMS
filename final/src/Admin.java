import java.util.List;
public class Admin extends User {
    private int age;

    public Admin(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.ADMIN, gender, contactEmail, contactNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Update staff details
    public void updateStaff(String staffID, String fieldToUpdate, String newValue) {
        boolean success = StaffServiceFacade.getInstance().updateStaff(staffID, fieldToUpdate, newValue);
        if (!success) {
            System.out.println("Failed to update staff details.");
        }
    }

    public void addStaff(User staffMember) {
        StaffServiceFacade.getInstance().addStaff(staffMember);
    }

    public void removeStaff(String staffID) {
        StaffServiceFacade.getInstance().removeStaff(staffID);
    }

    // Retrieve a staff member by ID
    public void viewStaffDetails(String staffID) {
        User staff = StaffServiceFacade.getInstance().getStaffById(staffID);
        if (staff != null) {
            System.out.println("Staff Details:\n" + staff);
        } else {
            System.out.println("No staff member found with ID: " + staffID);
        }
    }

    // Filter and display staff
    public void displayFilteredStaff(String filterType, String filterValue) {
        StaffServiceFacade.getInstance().displayFilteredStaff(filterType, filterValue);
    }

    public void displayAllStaff() {
        StaffServiceFacade.getInstance().displayAllStaff();
    }

    // Approve a replenishment request
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

    // Reject a replenishment request
    public void rejectRequest(int requestId) {
        ReplenishmentRequest request = RequestRecord.getInstance().getRequestById(requestId);
        if (request != null && request.getStatus() == RequestStatus.PENDING) {
            request.setStatus(RequestStatus.REJECTED);
            System.out.println("Request rejected: " + request);
        } else {
            System.out.println("Request ID " + requestId + " not found or already processed.");
        }
    }

    // View all replenishment requests
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

    // View requests by status
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

    // View low stock medicines
    public void viewLowStockMedicines() {
        System.out.println("Medicines low on stock:");
        MedicineInventory.getInstance(null).checkLowStock();
    }

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
