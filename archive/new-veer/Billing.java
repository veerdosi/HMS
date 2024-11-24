public class Billing extends User {
    public Billing(String userID, String name, UserRole role, String gender, String contactEmail,
            String contactNumber) {
        super(userID, name, DEFAULT_PASSWORD, role, gender, contactEmail, contactNumber); // Pass default password
    }

    /**
     * @param patientId
     * @param serviceDetails
     * @param amount
     */
    public void generateInvoice(String patientId, String serviceDetails, double amount) {
        System.out.println("Generating invoice for Patient ID: " + patientId);
        System.out.println("Service Details: " + serviceDetails);
        System.out.println("Amount: $" + amount);
        // Logic to generate and save invoice in the system
    }

    public void processPayment(String patientId, double amount) {
        System.out.println("Processing payment of $" + amount + " for Patient ID: " + patientId);
        // Logic to process payment (e.g., updating payment status in records)
    }

    public void viewOutstandingPayments() {
        System.out.println("Viewing outstanding payments...");
        // Logic to retrieve and display a list of outstanding payments
    }
}