
import java.util.List;

public class Admin extends User {
    private List<User> staffList;
    private List<ReplenishmentRequest> requests;

    public Admin(String userId, String password, String contactNumber, String email) {
        super(userId, password, contactNumber, email);
    }

    public void addStaff(User staff) {
        staffList.add(staff);
    }

    public void removeStaff(String staffID) {
        staffList.removeIf(staff -> staff.userId.equals(staffID));
    }

    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        request.approve();
        updateMedicineStock(request.getMedicine(), request.getRequestedQuantity());
    }

    public List<Appointment> viewAllAppointments(AppointmentServiceFacade appointmentService) {
        return appointmentService.getAllAppointments();
    }

    public void updateMedicineStock(Medicine medicine, int quantity) {
        medicine.setCurrentStock(medicine.getCurrentStock() + quantity);
    }

    @Override
    public void displayMenu() {
        AdminMenu menu = new AdminMenu(this);
        menu.show();
    }
}
