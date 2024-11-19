import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Admin {
    private List<Staff> staffList; // List of hospital staff
    private Map<String, Medicine> inventory; // Map of medicine by name
    private List<ReplenishmentRequest> replenishmentRequests; // List of replenishment requests
    private AppointmentServiceFacade appointmentServiceFacade; // Appointment Service Facade for appointments

    // Constructor
    public Admin(AppointmentServiceFacade appointmentServiceFacade, Map<String, Medicine> inventory,
            List<Staff> initialStaff) {
        this.staffList = initialStaff != null ? new ArrayList<>(initialStaff) : new ArrayList<>();
        this.replenishmentRequests = new ArrayList<>();
        this.appointmentServiceFacade = appointmentServiceFacade;
        this.inventory = inventory != null ? inventory : new HashMap<>();
    }

    // Add Staff
    public void addStaff(String staffDetails) {
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

    // View Appointment Details via AppointmentServiceFacade
    public void viewAppointmentDetails() {
        try {
            List<Appointment> appointments = appointmentServiceFacade.getAppointmentService().getAllAppointments();
            if (appointments.isEmpty()) {
                System.out.println("No appointments found.");
            } else {
                System.out.println("Appointments:");
                for (Appointment appointment : appointments) {
                    System.out.println(appointment);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching appointments: " + e.getMessage());
        }
    }

    // Add Medication
    public void addMedication(String medicineDetails) {
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
                Medicine medicine = inventory.get(request.getMedicineID());
                if (medicine != null) {
                    medicine.replenishStock(request.getRequestedQuantity());
                    System.out.println("Stock updated for medicine: " + medicine.getName());
                } else {
                    System.out.println("Medicine not found: " + request.getMedicineID());
                }
            }
        }
    }

    // Add Replenishment Request
    public void addReplenishmentRequest(ReplenishmentRequest request) {
        replenishmentRequests.add(request);
        System.out.println("Replenishment request added: " + request);
    }
}