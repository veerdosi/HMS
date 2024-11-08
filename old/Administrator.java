package old;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends User implements InventoryManageable {
    private String adminID;
    private List<User> staffList; // A list to hold staff members

    public Administrator(String userID, String password, String name, String contactNumber, String email,
            String adminID) {
        super(userID, password, name, contactNumber, email, UserRole.ADMINISTRATOR);
        this.adminID = adminID;
        this.staffList = new ArrayList<>(); // Initialize staff list
    }

    // Add staff
    public void addStaff(User staff) {
        staffList.add(staff);
        System.out.println("Added staff: " + staff.getName() + " with ID: " + staff.getUserID());
    }

    public List<User> filterStaffByRole(UserRole role) {
        List<User> filteredStaff = new ArrayList<>();
        for (User staff : staffList) {
            if (staff.getRole() == role) {
                filteredStaff.add(staff);
            }
        }
        return filteredStaff;
    }

    // Remove staff
    public void removeStaff(String staffID) {
        staffList.removeIf(staff -> staff.getUserID().equals(staffID));
        System.out.println("Removed staff with ID: " + staffID);
    }

    @Override
    public void updateStock(String medicineID, int quantity) {
        for (Medicine medicine : inventory) {
            if (medicine.getMedicineID().equals(medicineID)) {
                medicine.updateStock(quantity);
                System.out.println("Stock updated for: " + medicine.getName());
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    @Override
    public void submitReplenishmentRequest(Medicine medicine, int quantity) {
        // Administrators can also submit replenishment requests if required
        ReplenishmentRequest request = new ReplenishmentRequest(medicine.getMedicineID(), quantity);
        System.out.println("Administrator submitted replenishment request for " + medicine.getName()
                + " with quantity: " + quantity);
    }

    @Override
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        request.approve();
        System.out.println("Replenishment request approved for medicine: " + request.getMedicineID());
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Performing administrator-specific actions.");
    }

    // Method to view all staff
    public List<User> viewAllStaff() {
        return staffList;
    }

    // Method to view all appointments if needed
    public void viewAllAppointments(AppointmentManager appointmentManager) {
        List<Appointment> allAppointments = appointmentManager.getAllAppointments();
        // Print details or manage appointments as needed
        System.out.println("All Appointments: " + allAppointments);
    }
}
