import java.util.List;
import java.util.Scanner;

public class DoctorMenu {

    private Doctor doctor;
    private AppointmentServiceFacade appointmentServiceFacade;

    // Constructor to initialize the menu with necessary services
    public DoctorMenu(Doctor doctor, AppointmentServiceFacade appointmentServiceFacade) {
        this.doctor = doctor;
        this.appointmentServiceFacade = appointmentServiceFacade;
    }

    // Display menu and handle interactions
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View Schedule");
            System.out.println("2. Set Availability");
            System.out.println("3. View Available Slots");
            System.out.println("4. Accept Appointment");
            System.out.println("5. Decline Appointment");
            System.out.println("6. Add Consultation Notes");
            System.out.println("7. Add Prescription");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewSchedule();
                    break;
                case 2:
                    setAvailability(scanner);
                    break;
                case 3:
                    viewAvailableSlots();
                    break;
                case 4:
                    acceptAppointment(scanner);
                    break;
                case 5:
                    declineAppointment(scanner);
                    break;
                case 6:
                    addConsultationNotes(scanner);
                    break;
                case 7:
                    addPrescription(scanner);
                    break;
                case 8:
                    System.out.println("Exiting Doctor Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    // 1. View Schedule
    private void viewSchedule() {
        System.out.println("\n--- Doctor's Schedule ---");
        doctor.viewSchedule();
    }

    // 2. Set Availability
    private void setAvailability(Scanner scanner) {
        System.out.println("\n--- Set Availability ---");
        List<TimeSlot> newSlots = AppointmentSlotUtil.generateDailySlots(); // Generate default slots
        System.out.println("Default slots generated. You can modify them manually if needed.");

        // Optional: Modify slots based on user input
        System.out.println("Do you want to mark any slot as unavailable? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter the index of the slot to mark unavailable (0-based): ");
            for (int i = 0; i < newSlots.size(); i++) {
                System.out.println(i + ": " + newSlots.get(i));
            }
            int index = scanner.nextInt();
            if (index >= 0 && index < newSlots.size()) {
                newSlots.get(index).setAvailable(false);
                System.out.println("Slot marked as unavailable.");
            } else {
                System.out.println("Invalid index.");
            }
        }

        doctor.setAvailability(newSlots);
        System.out.println("Availability updated.");
    }

    // 3. View Available Slots
    private void viewAvailableSlots() {
        System.out.println("\n--- Available Slots ---");
        List<TimeSlot> availableSlots = doctor.getAvailability();
        if (availableSlots == null || availableSlots.isEmpty()) {
            System.out.println("No available slots.");
        } else {
            for (TimeSlot slot : availableSlots) {
                System.out.println(slot);
            }
        }
    }

    // 4. Accept Appointment
    private void acceptAppointment(Scanner scanner) {
        System.out.println("\n--- Accept Appointment ---");
        List<Appointment> schedule = doctor.getSchedule();
        if (schedule == null || schedule.isEmpty()) {
            System.out.println("No pending appointments.");
            return;
        }

        System.out.println("Select an appointment to accept:");
        for (int i = 0; i < schedule.size(); i++) {
            System.out.println(i + ": " + schedule.get(i));
        }

        int index = scanner.nextInt();
        if (index >= 0 && index < schedule.size()) {
            Appointment appointment = schedule.get(index);
            appointmentServiceFacade.processAppointment(appointment.getId(), true);
            System.out.println("Appointment accepted for patient: " + appointment.getPatient().getName());
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // 5. Decline Appointment
    private void declineAppointment(Scanner scanner) {
        System.out.println("\n--- Decline Appointment ---");
        List<Appointment> schedule = doctor.getSchedule();
        if (schedule == null || schedule.isEmpty()) {
            System.out.println("No pending appointments.");
            return;
        }

        System.out.println("Select an appointment to decline:");
        for (int i = 0; i < schedule.size(); i++) {
            System.out.println(i + ": " + schedule.get(i));
        }

        int index = scanner.nextInt();
        if (index >= 0 && index < schedule.size()) {
            Appointment appointment = schedule.get(index);
            appointmentServiceFacade.processAppointment(appointment.getId(), false);
            System.out.println("Appointment declined for patient: " + appointment.getPatient().getName());
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // 6. Add Consultation Notes
    private void addConsultationNotes(Scanner scanner) {
        System.out.println("\n--- Add Consultation Notes ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.next();
        System.out.print("Enter Notes: ");
        scanner.nextLine(); // Consume newline
        String notes = scanner.nextLine();

        appointmentServiceFacade.addConsultationNotes(appointmentId, notes);
        System.out.println("Consultation notes added.");
    }

    // 7. Add Prescription
    private void addPrescription(Scanner scanner) {
        System.out.println("\n--- Add Prescription ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.next();
        System.out.print("Enter Prescription Details: ");
        scanner.nextLine(); // Consume newline
        String prescriptionDetails = scanner.nextLine();

        Prescription prescription = new Prescription(prescriptionDetails);
        appointmentServiceFacade.addPrescription(appointmentId, prescription);
        System.out.println("Prescription added.");
    }
}