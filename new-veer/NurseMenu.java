import java.util.Scanner;

public class NurseMenu {
    private Nurse nurse;

    public NurseMenu(Nurse nurse) {
        this.nurse = nurse;
    }

    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nNurse Menu:");
            System.out.println("1. Record Patient Vitals");
            System.out.println("2. Prepare for Appointment");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID: ");
                    String patientId = sc.next();
                    System.out.print("Enter Vitals Information: ");
                    sc.nextLine(); // Consume newline
                    String vitals = sc.nextLine();
                    nurse.recordPatientVitals(patientId, vitals);
                    break;
                case 2:
                    System.out.print("Enter Appointment ID: ");
                    String appointmentId = sc.next();
                    nurse.prepareForAppointment(appointmentId);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        sc.close();
    }
}