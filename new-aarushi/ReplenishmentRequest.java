
public class ReplenishmentRequest {
    private String requestID;
    private String medicineID;
    private int requestedQuantity;
    private String status; // Pending, Approved, Rejected

    public ReplenishmentRequest(String requestID, String medicineID, int requestedQuantity) {
        this.requestID = requestID;
        this.medicineID = medicineID;
        this.requestedQuantity = requestedQuantity;
        this.status = "Pending";
    }

    public String getRequestID() {
        return requestID;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void approve() {
        this.status = "Approved";
    }

    public void reject() {
        this.status = "Rejected";
    }

    public String toString() {
        return "RequestID: " + requestID + ", MedicineID: " + medicineID + ", Quantity: " + requestedQuantity + ", Status: " + status;
    }
}
