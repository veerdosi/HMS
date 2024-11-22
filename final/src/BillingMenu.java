import java.util.Scanner;

public class BillingMenu {
    private Billing billing;

    public BillingMenu(Billing billing) {
        this.billing = billing;
    }

    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nBilling Menu:");
            System.out.println("1. Generate Invoice");
            System.out.println("2. Process Payment");
            System.out.println("3. View Outstanding Payments");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID: ");
                    String patientId = sc.next();
                    System.out.print("Enter Service Details: ");
                    sc.nextLine(); // Consume newline
                    String serviceDetails = sc.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();
                    billing.generateInvoice(patientId, serviceDetails, amount);
                    break;
                case 2:
                    System.out.print("Enter Patient ID: ");
                    patientId = sc.next();
                    System.out.print("Enter Payment Amount: ");
                    amount = sc.nextDouble();
                    billing.processPayment(patientId, amount);
                    break;
                case 3:
                    billing.viewOutstandingPayments();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        sc.close();
    }
}