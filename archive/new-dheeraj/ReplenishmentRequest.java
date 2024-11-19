// File: ReplenishmentRequest.java

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
    public ReplenishmentRequest(String requestID, String medicineID, int requestedQuantity, MedicineInventory inventory) {
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

    // Reject the replenishment request
    public void reject() {
        if (this.requestStatus == RequestStatus.PENDING) {
            this.requestStatus = RequestStatus.REJECTED;
            System.out.println("Replenishment request rejected. Request ID: " + requestID + ", Medicine ID: " + medicineID);
            // Log the rejection for administrative tracking
            logRejection();
        } else {
            System.out.println("Replenishment request cannot be rejected as it is not in PENDING state.");
        }
    }

    // Log the rejection details
    private void logRejection() {
        try (PrintWriter out = new PrintWriter(new FileWriter("replenishment_log.txt", true))) {
            out.println("[LOG] Replenishment request rejected: Request ID: " + requestID + ", Medicine ID: " + medicineID + ", Requested Quantity: " + requestedQuantity);
        } catch (IOException e) {
            System.err.println("Error logging rejection: " + e.getMessage());
        }
    }

    // Display request details
    public void displayRequestInfo() {
        System.out.println("Request ID: " + requestID + ", Medicine ID: " + medicineID 
                + ", Requested Quantity: " + requestedQuantity + ", Status: " + requestStatus);
        logDisplayInfo();
    }

    // Log the display details
    private void logDisplayInfo() {
        try (PrintWriter out = new PrintWriter(new FileWriter("replenishment_log.txt", true))) {
            out.println("[LOG] Displaying request info: Request ID: " + requestID + ", Medicine ID: " + medicineID 
                    + ", Requested Quantity: " + requestedQuantity + ", Status: " + requestStatus);
        } catch (IOException e) {
            System.err.println("Error logging display info: " + e.getMessage());
        }
    }
}
