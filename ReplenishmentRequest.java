
public class ReplenishmentRequest {
    private String requestID;
    private Medicine medicine;
    private int requestedQuantity;
    private boolean approved;

    public ReplenishmentRequest(String requestID, Medicine medicine, int requestedQuantity) {
        this.requestID = requestID;
        this.medicine = medicine;
        this.requestedQuantity = requestedQuantity;
        this.approved = false;
    }

    public void approve() {
        this.approved = true;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }
}
