public class Pharmacist extends User {
    private int age;

    public Pharmacist(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.PHARMACIST, gender, contactEmail, contactNumber);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    // Submit a replenishment request
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        ReplenishmentRequest request = new ReplenishmentRequest(medicineName, quantity, this.userID);
        RequestRecord.getInstance().addRequest(request); // Add to the Singleton RequestRecord
        System.out.println("Replenishment request submitted: " + request);
    }

    // View all requests submitted by this pharmacist
    public void viewMyRequests() {
        System.out.println("Requests by Pharmacist ID: " + this.userID);
        for (ReplenishmentRequest request : RequestRecord.getInstance().getAllRequests()) {
            if (request.getPharmacistId().equals(this.userID)) {
                System.out.println(request);
            }
        }
    }

    // Method to update prescription status
    public void updatePrescriptionStatus(String appointmentId, String medicineName, PrescriptionStatus status) {
        // Access the Singleton instance of AppointmentServiceFacade
        AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(null, null);

        // Fetch the MedicineInventory singleton
        MedicineInventory inventory = MedicineInventory.getInstance("C:/Users/LENOVO/Desktop/HMS/Data/Medicine_List(Sheet1).csv");

        // Get the medicine from the inventory
        Medicine medicine = inventory.getMedicineByName(medicineName);
        if (medicine == null) {
            System.out.println("Medicine not found in inventory: " + medicineName);
            return;
        }

        // Update the prescription status through the facade
        facade.updatePrescriptionStatus(appointmentId, medicine, status);
        System.out.println("Prescription for " + medicineName + " in Appointment ID " + appointmentId + " updated to status: " + status);

        // If the prescription status is DISPENSED, decrease the stock of the medicine
        if (status == PrescriptionStatus.DISPENSED) {
            inventory.decreaseStock(medicineName);
        }
    }


    // Method to view all medicines and their stock levels
    public void viewMedicineInventory() {
        //Checks for low stock levels
        checkLowStock();
        // Fetch the MedicineInventory singleton
        MedicineInventory inventory = MedicineInventory.getInstance("C:/Users/LENOVO/Desktop/HMS/Data/Medicine_List(Sheet1).csv");

        // List all medicines
        inventory.listAllMedicines();
    }

    // Method to check for low stock medicines
    public void checkLowStock() {
        // Fetch the MedicineInventory singleton
        MedicineInventory inventory = MedicineInventory.getInstance("C:/Users/LENOVO/Desktop/HMS/Data/Medicine_List(Sheet1).csv");

        // Display low stock warnings
        inventory.checkLowStock();
    }


}


