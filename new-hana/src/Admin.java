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
}
