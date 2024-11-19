import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReplenishmentRequest {
    private String requestID;
    private String medicineID;
    private int requestedQuantity;
    private RequestStatus requestStatus; // Enum for status: PENDING, APPROVED, REJECTED
    private MedicineInventory inventory;

    // Enum for Request Status
    public enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }

    // Constructor
    public ReplenishmentRequest(String requestID, String medicineID, int requestedQuantity,
            MedicineInventory inventory) {
        Medicine medicine = inventory.getMedicineByName(medicineID);
        if (medicine == null) {
            throw new IllegalArgumentException("Invalid medicineID: Medicine not found in inventory");
        }
        this.requestID = requestID;
        this.medicineID = medicineID;
        this.requestedQuantity = requestedQuantity;
        this.requestStatus = RequestStatus.PENDING;
        this.inventory = inventory;
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

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    // Approve the replenishment request
    public void approve() {
        if (this.requestStatus == RequestStatus.PENDING) {
            this.requestStatus = RequestStatus.APPROVED;
            inventory.updateStock(medicineID, requestedQuantity);
            System.out.println(
                    "Replenishment request approved. Request ID: " + requestID + ", Medicine ID: " + medicineID);
            logApproval();
        } else {
            System.out.println("Replenishment request cannot be approved as it is not in PENDING state.");
        }
    }

    // Reject the replenishment request
    public void reject() {
        if (this.requestStatus == RequestStatus.PENDING) {
            this.requestStatus = RequestStatus.REJECTED;
            System.out.println(
                    "Replenishment request rejected. Request ID: " + requestID + ", Medicine ID: " + medicineID);
            logRejection();
        } else {
            System.out.println("Replenishment request cannot be rejected as it is not in PENDING state.");
        }
    }

    // Log approval details
    private void logApproval() {
        logToFile("[LOG] Replenishment request approved: Request ID: " + requestID + ", Medicine ID: " + medicineID
                + ", Requested Quantity: " + requestedQuantity);
    }

    // Log rejection details
    private void logRejection() {
        logToFile("[LOG] Replenishment request rejected: Request ID: " + requestID + ", Medicine ID: " + medicineID
                + ", Requested Quantity: " + requestedQuantity);
    }

    // Display request details
    public void displayRequestInfo() {
        System.out.println("Request ID: " + requestID + ", Medicine ID: " + medicineID
                + ", Requested Quantity: " + requestedQuantity + ", Status: " + requestStatus);
        logDisplayInfo();
    }

    // Log the display details
    private void logDisplayInfo() {
        logToFile("[LOG] Displaying request info: Request ID: " + requestID + ", Medicine ID: " + medicineID
                + ", Requested Quantity: " + requestedQuantity + ", Status: " + requestStatus);
    }

    // Utility method to log to a file
    private void logToFile(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter("replenishment_log.txt", true))) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Error logging information: " + e.getMessage());
        }
    }
}