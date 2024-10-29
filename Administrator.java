import java.util.List;

public class Administrator extends User implements InventoryManageable {
    private String adminID;

    public Administrator(String userID, String password, String name, String contactNumber, String email,
            String adminID) {
        super(userID, password, name, contactNumber, email, UserRole.ADMINISTRATOR);
        this.adminID = adminID;
    }

    // Add staff
    public void addStaff(User staff) {
        // Logic to add a new staff member
    }

    // Remove staff
    public void removeStaff(String staffID) {
        // Logic to remove a staff member by ID
    }

    @Override
    public void updateStock(int quantity) {
        // Logic to update stock of medications
    }

    @Override
    public void submitReplenishmentRequest(Medicine medicine, int quantity) {
        // Logic to submit a replenishment request
    }

    @Override
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        request.approve();
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Performing administrator-specific actions.");
    }
}
