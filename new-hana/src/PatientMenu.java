import java.util.Scanner;

public class PatientMenu {
    private Patient patient;

    public PatientMenu(Patient patient) {
        this.patient = patient;
    }

    public boolean display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("---- Patient Menu ----");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Reschedule Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    viewAvailableAppointments();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return false; // Return false to signal logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewMedicalRecord() {
        // Implement view medical record
    }

    private void updatePersonalInformation() {
        // Implement update personal information
    }

    private void viewAvailableAppointments() {
        // Implement view available appointments
    }

    private void scheduleAppointment() {
        // Implement schedule appointment
    }
}