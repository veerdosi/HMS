import java.util.*;

public class Admin {
    private List<Staff> staffList;
    private List<Appointment> appointments;
    private Map<String, Medicine> inventory;
    private List<ReplenishmentRequest> replenishmentRequests;

    // Constructor
    public Admin() {
        this.staffList = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.replenishmentRequests = new ArrayList<>();
    }

    // Add Staff
    public void addStaff(String staffDetails) {
        // Expected format: Name,Role,Age,Gender
        String[] details = staffDetails.split(",");
        if (details.length == 4) {
            Staff newStaff = new Staff(details[0], details[1], Integer.parseInt(details[2]), details[3]);
            staffList.add(newStaff);
            System.out.println("Staff added successfully: " + newStaff);
        } else {
            System.out.println("Invalid format. Staff not added.");
        }
    }

    // Remove Staff
    public void removeStaff(String staffID) {
        boolean removed = staffList.removeIf(staff -> staff.getId().equals(staffID));
        if (removed) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Staff ID not found.");
        }
    }

    // Update Staff Details
    public void updateStaff(String updateDetails) {
        // Expected format: ID,NewDetails
        String[] details = updateDetails.split(",");
        if (details.length == 2) {
            for (Staff staff : staffList) {
                if (staff.getId().equals(details[0])) {
                    staff.updateDetails(details[1]);
                    System.out.println("Staff updated successfully.");
                    return;
                }
            }
            System.out.println("Staff ID not found.");
        } else {
            System.out.println("Invalid format. Staff not updated.");
        }
    }

    // View Appointment Details
    public void viewAppointmentDetails() {
        System.out.println("Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    // Add Medication
    public void addMedication(String medicineDetails) {
        // Expected format: Name,Stock,AlertLevel
        String[] details = medicineDetails.split(",");
        if (details.length == 3) {
            Medicine newMedicine = new Medicine(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2]));
            inventory.put(details[0], newMedicine);
            System.out.println("Medication added successfully: " + newMedicine);
        } else {
            System.out.println("Invalid format. Medication not added.");
        }
    }

    // Update Stock Levels
    public void updateStockLevels(String updateStock) {
        // Expected format: Name,NewStock
        String[] details = updateStock.split(",");
        if (details.length == 2 && inventory.containsKey(details[0])) {
            Medicine medicine = inventory.get(details[0]);
            medicine.setStock(Integer.parseInt(details[1]));
            System.out.println("Stock updated successfully: " + medicine);
        } else {
            System.out.println("Invalid format or medication not found.");
        }
    }

    // Approve Replenishment Requests
    public void approveReplenishmentRequests() {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (!request.isApproved()) {
                request.approve();
                Medicine medicine = inventory.get(request.getMedicineName());
                if (medicine != null) {
                    medicine.replenishStock(request.getQuantity());
                    System.out.println("Replenishment approved for: " + medicine.getName());
                } else {
                    System.out.println("Medicine not found: " + request.getMedicineName());
                }
            }
        }
    }
}