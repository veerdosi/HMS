import java.util.ArrayList;
import java.util.List;

public class RequestRecord {
    private static RequestRecord instance; // Singleton instance
    private List<ReplenishmentRequest> requests; // List of all requests

    // Private constructor to prevent external instantiation
    private RequestRecord() {
        this.requests = new ArrayList<>();
    }

    /**
     * @return RequestRecord
     */
    // Method to get the single instance of RequestRecord
    public static RequestRecord getInstance() {
        if (instance == null) {
            instance = new RequestRecord();
        }
        return instance;
    }

    // Add a replenishment request
    public void addRequest(ReplenishmentRequest request) {
        requests.add(request);
        System.out.println("New request added: " + request);
    }

    // Retrieve all requests
    public List<ReplenishmentRequest> getAllRequests() {
        return requests;
    }

    // Retrieve requests by status (PENDING, APPROVED, REJECTED)
    public List<ReplenishmentRequest> getRequestsByStatus(RequestStatus status) {
        List<ReplenishmentRequest> filteredRequests = new ArrayList<>();
        for (ReplenishmentRequest request : requests) {
            if (request.getStatus() == status) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }

    // Retrieve a specific request by ID
    public ReplenishmentRequest getRequestById(int requestId) {
        for (ReplenishmentRequest request : requests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }

}
