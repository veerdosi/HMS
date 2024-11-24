/**
 * The `ReplenishmentRequest` class represents a request for replenishing the stock of a specific medicine.
 * It contains details such as the request ID, medicine name, requested quantity, pharmacist ID, and status.
 */
public class ReplenishmentRequest {
    private static int nextRequestId = 1; // Counter to generate unique request IDs
    private int requestId;
    private String medicineName;
    private int requestedQuantity;
    private String pharmacistId;
    private RequestStatus status;

    /**
     * Constructs a `ReplenishmentRequest` with the specified details.
     * The status is set to `PENDING` by default.
     *
     * @param medicineName      the name of the medicine being requested.
     * @param requestedQuantity the quantity of the medicine being requested.
     * @param pharmacistId      the ID of the pharmacist making the request.
     */
    public ReplenishmentRequest(String medicineName, int requestedQuantity, String pharmacistId) {
        this.requestId = nextRequestId++;
        this.medicineName = medicineName;
        this.requestedQuantity = requestedQuantity;
        this.pharmacistId = pharmacistId;
        this.status = RequestStatus.PENDING;
    }

    /**
     * Retrieves the unique ID of the request.
     *
     * @return the request ID.
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Retrieves the name of the medicine being requested.
     *
     * @return the medicine name.
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Retrieves the quantity of the medicine being requested.
     *
     * @return the requested quantity.
     */
    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    /**
     * Retrieves the ID of the pharmacist who made the request.
     *
     * @return the pharmacist ID.
     */
    public String getPharmacistId() {
        return pharmacistId;
    }

    /**
     * Retrieves the current status of the request.
     *
     * @return the `RequestStatus` of the request.
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of the request.
     *
     * @param status the new `RequestStatus` to set.
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the replenishment request.
     *
     * @return a string describing the request, including its ID, medicine name, quantity, pharmacist ID, and status.
     */
    @Override
    public String toString() {
        return "Request ID: " + requestId +
                ", Medicine: " + medicineName +
                ", Quantity: " + requestedQuantity +
                ", Pharmacist ID: " + pharmacistId +
                ", Status: " + status;
    }
}
