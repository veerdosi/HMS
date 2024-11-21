public class ReplenishmentRequest {
    private static int nextRequestId = 1;
    private int requestId;
    private String medicineName;
    private int requestedQuantity;
    private String pharmacistId;
    private RequestStatus status;

    public ReplenishmentRequest(String medicineName, int requestedQuantity, String pharmacistId) {
        this.requestId = nextRequestId++;
        this.medicineName = medicineName;
        this.requestedQuantity = requestedQuantity;
        this.pharmacistId = pharmacistId;
        this.status = RequestStatus.PENDING;
    }

    /**
     * @return int
     */
    public int getRequestId() {
        return requestId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId +
                ", Medicine: " + medicineName +
                ", Quantity: " + requestedQuantity +
                ", Pharmacist ID: " + pharmacistId +
                ", Status: " + status;
    }
}
