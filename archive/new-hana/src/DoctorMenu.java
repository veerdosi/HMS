import java.util.Scanner;

public class DoctorMenu {
    private Doctor doctor;

    public DoctorMenu(Doctor doctor) {
        this.doctor = doctor;
    }
    public boolean display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("---- Doctor Menu ----");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewPatientRecords();
                    break;
                case 2:
                    updateMedicalRecords();
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    setAvailability();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return false; // Return false to signal logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewPatientRecords() {
        // Implement view patient records
    }

    private void updateMedicalRecords() {
        // Implement update medical records
    }

    private void viewSchedule() {
        // Implement view schedule
    }

    private void setAvailability() {
        // Implement set availability
    }
}