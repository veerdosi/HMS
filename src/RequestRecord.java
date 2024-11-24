import java.util.ArrayList;
import java.util.List;

/**
 * The `RequestRecord` class manages a centralized list of replenishment requests using the Singleton design pattern.
 * It provides functionality to add, retrieve, and filter requests based on their status or ID.
 */
public class RequestRecord {
    private static RequestRecord instance; // Singleton instance
    private List<ReplenishmentRequest> requests; // List of all requests

    /**
     * Private constructor to enforce the Singleton design pattern.
     */
    private RequestRecord() {
        this.requests = new ArrayList<>();
    }

    /**
     * Provides the Singleton instance of the `RequestRecord`.
     *
     * @return the Singleton instance of the `RequestRecord`.
     */
    public static RequestRecord getInstance() {
        if (instance == null) {
            instance = new RequestRecord();
        }
        return instance;
    }

    /**
     * Adds a new replenishment request to the record.
     *
     * @param request the `ReplenishmentRequest` to add.
     */
    public void addRequest(ReplenishmentRequest request) {
        requests.add(request);
        System.out.println("New request added: " + request);
    }

    /**
     * Retrieves all replenishment requests.
     *
     * @return a list of all `ReplenishmentRequest` objects.
     */
    public List<ReplenishmentRequest> getAllRequests() {
        return requests;
    }

    /**
     * Retrieves all replenishment requests filtered by their status.
     *
     * @param status the `RequestStatus` to filter by.
     * @return a list of `ReplenishmentRequest` objects with the specified status.
     */
    public List<ReplenishmentRequest> getRequestsByStatus(RequestStatus status) {
        List<ReplenishmentRequest> filteredRequests = new ArrayList<>();
        for (ReplenishmentRequest request : requests) {
            if (request.getStatus() == status) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }

    /**
     * Retrieves a specific replenishment request by its unique ID.
     *
     * @param requestId the ID of the request to retrieve.
     * @return the `ReplenishmentRequest` object with the specified ID, or `null` if not found.
     */
    public ReplenishmentRequest getRequestById(int requestId) {
        for (ReplenishmentRequest request : requests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }
}
