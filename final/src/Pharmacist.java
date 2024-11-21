/**
 * The `Pharmacist` class represents a user with the role of a pharmacist in the system.
 * Pharmacists can submit replenishment requests, view their requests, and update prescription statuses.
 */
public class Pharmacist extends User {
    private int age; // Age of the pharmacist

    /**
     * Constructs a `Pharmacist` object.
     *
     * @param userID       the unique ID of the pharmacist.
     * @param name         the name of the pharmacist.
     * @param password     the password for the pharmacist's account.
     * @param gender       the gender of the pharmacist.
     * @param contactEmail the contact email of the pharmacist.
     * @param contactNumber the contact number of the pharmacist.
     * @param age          the age of the pharmacist.
     */
    public Pharmacist(String userID, String name, String password, String gender, String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.PHARMACIST, gender, contactEmail, contactNumber);
        this.age = age;
    }

    /**
     * Retrieves the age of the pharmacist.
     *
     * @return the pharmacist's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Updates the pharmacist's age.
     *
     * @param age the new age of the pharmacist.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Submits a replenishment request for a specific medicine.
     *
     * @param medicineName the name of the medicine to replenish.
     * @param quantity     the quantity to replenish.
     */
    public void submitReplenishmentRequest(String medicineName, int quantity) {
        ReplenishmentRequest request = new ReplenishmentRequest(medicineName, quantity, this.userID);
        RequestRecord.getInstance().addRequest(request);
        System.out.println("Replenishment request submitted: " + request);
    }

    /**
     * Displays all replenishment requests submitted by this pharmacist.
     */
    public void viewMyRequests() {
        System.out.println("Requests by Pharmacist ID: " + this.userID);
        for (ReplenishmentRequest request : RequestRecord.getInstance().getAllRequests()) {
            if (request.getPharmacistId().equals(this.userID)) {
                System.out.println(request);
            }
        }
    }

    /**
     * Updates the status of a prescription and adjusts medicine stock if dispensed.
     *
     * @param appointmentId the ID of the appointment.
     * @param medicineName  the name of the medicine in the prescription.
     * @param status        the new status of the prescription.
     */
    public void updatePrescriptionStatus(String appointmentId, String medicineName, PrescriptionStatus status) {
        AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(null, null);
        MedicineInventory inventory = MedicineInventory.getInstance("path_to_medicine_file");

        Medicine medicine = inventory.getMedicineByName(medicineName);
        if (medicine == null) {
            System.out.println("Medicine not found in inventory: " + medicineName);
            return;
        }

        if (facade.updatePrescriptionStatus(appointmentId, medicine, status)) {
            System.out.println("Prescription for " + medicineName + " in Appointment ID " + appointmentId + " updated to status: " + status);
            if (status == PrescriptionStatus.DISPENSED) {
                inventory.decreaseStock(medicineName);
            }
        }
    }

    /**
     * Displays all medicines in the inventory along with their stock levels.
     */
    public void viewMedicineInventory() {
        checkLowStock();
        MedicineInventory inventory = MedicineInventory.getInstance("path_to_medicine_file");
        inventory.listAllMedicines();
    }

    /**
     * Checks and displays warnings for medicines that are low on stock.
     */
    public void checkLowStock() {
        MedicineInventory inventory = MedicineInventory.getInstance("path_to_medicine_file");
        inventory.checkLowStock();
    }

    /**
     * Returns a string representation of the pharmacist.
     *
     * @return a string containing the pharmacist's details.
     */
    @Override
    public String toString() {
        return "ID: " + userID +
                ", Name: " + name +
                ", Role: " + role +
                ", Gender: " + gender +
                ", Age: " + age +
                ", Email: " + contactEmail +
                ", Contact: " + contactNumber;
    }
}
