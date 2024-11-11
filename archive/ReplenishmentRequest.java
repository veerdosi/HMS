package archive;

public class ReplenishmentRequest {
    private String requestID;
    private String medicineID;
    private int requestedQuantity;
    private RequestStatus status;

    public ReplenishmentRequest(String medicineID, int requestedQuantity) {
        this.requestID = generateRequestID();
        this.medicineID = medicineID;
        this.requestedQuantity = requestedQuantity;
        this.status = RequestStatus.PENDING;
    }

    public String getMedicineID() {
        return medicineID;
    }

    private String generateRequestID() {
        return "REQ-" + System.currentTimeMillis();
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
    }
}
