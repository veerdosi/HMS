// File: ReplenishmentRequest.java

public class ReplenishmentRequest {
    private String requestID;
    private String medicineID;
    private int requestedQuantity;
    private String requestStatus; // e.g., "pending", "approved", "rejected"

    // Constructor
    public ReplenishmentRequest(String requestID, String medicineID, int requestedQuantity) {
        this.requestID = requestID;
        this.medicineID = medicineID;
        this.requestedQuantity = requestedQuantity;
        this.requestStatus = "pending";
    }

    // Getters
    public String getRequestID() {
        return requestID;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    // Approve the replenishment request
    public void approve() {
        this.requestStatus = "approved";
    }

    // Reject the replenishment request
    public void reject() {
        this.requestStatus = "rejected";
    }

    // Display request details
    public void displayRequestInfo() {
        System.out.println("Request ID: " + requestID + ", Medicine ID: " + medicineID 
                + ", Requested Quantity: " + requestedQuantity + ", Status: " + requestStatus);
    }
}
