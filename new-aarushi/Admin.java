
import java.util.*;

public class Admin extends User {
    private int age;

    public Admin(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.ADMIN, gender, contactEmail, contactNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    // Manage Staff
    public void addStaff(List<Staff> staffList, Staff newStaff) {
        staffList.add(newStaff);
        System.out.println("Staff added: " + newStaff);
    }

    public void removeStaff(List<Staff> staffList, String staffID) {
        staffList.removeIf(staff -> staff.getStaffID().equals(staffID));
        System.out.println("Staff removed with ID: " + staffID);
    }

    public void updateStaff(List<Staff> staffList, String staffID, String newName, String newRole, String newContact) {
        for (Staff staff : staffList) {
            if (staff.getStaffID().equals(staffID)) {
                staff.setName(newName);
                staff.setRole(newRole);
                staff.setContactInfo(newContact);
                System.out.println("Staff updated: " + staff);
                return;
            }
        }
        System.out.println("Staff ID not found.");
    }

    public void viewStaff(List<Staff> staffList) {
        for (Staff staff : staffList) {
            System.out.println(staff);
        }
    }

    // Manage Inventory
    public void viewMedicineInventory(List<Medicine> inventory) {
        for (Medicine medicine : inventory) {
            System.out.println(medicine);
        }
    }

    public void updateMedicineStock(List<Medicine> inventory, String medicineID, int quantity) {
        for (Medicine medicine : inventory) {
            if (medicine.getMedicineID().equals(medicineID)) {
                medicine.updateStock(quantity);
                System.out.println("Stock updated for: " + medicine.getName());
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    public void setLowStockAlert(List<Medicine> inventory, String medicineID, int alertLevel) {
        for (Medicine medicine : inventory) {
            if (medicine.getMedicineID().equals(medicineID)) {
                medicine.setLowStockAlert(alertLevel);
                System.out.println("Low stock alert updated for: " + medicine.getName());
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    // Approve Replenishment Requests
    public void viewReplenishmentRequests(List<ReplenishmentRequest> requests) {
        for (ReplenishmentRequest request : requests) {
            System.out.println(request);
        }
    }

    public void approveReplenishmentRequest(List<ReplenishmentRequest> requests, String requestID) {
        for (ReplenishmentRequest request : requests) {
            if (request.getRequestID().equals(requestID)) {
                request.approve();
                System.out.println("Request approved: " + request);
                return;
            }
        }
        System.out.println("Request ID not found.");
    }

    public void rejectReplenishmentRequest(List<ReplenishmentRequest> requests, String requestID) {
        for (ReplenishmentRequest request : requests) {
            if (request.getRequestID().equals(requestID)) {
                request.reject();
                System.out.println("Request rejected: " + request);
                return;
            }
        }
        System.out.println("Request ID not found.");
    }
}
