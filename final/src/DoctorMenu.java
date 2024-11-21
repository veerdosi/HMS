import java.util.Scanner;
import java.util.List;

public class DoctorMenu {
    private Doctor doctor;
    private AppointmentServiceFacade facade; // Use facade for appointment-related operations
    private Scanner scanner; // Shared scanner instance

    // Constructor
    public DoctorMenu(Doctor doctor, AppointmentServiceFacade facade, Scanner scanner) {
        this.doctor = doctor;
        this.facade = facade;
        this.scanner = scanner; // Shared scanner instance passed in
    }

    // Display menu and handle interactions
    public boolean displayMenu() {
        while (true) {
            try {
                System.out.println("\n--- Doctor Menu ---");
                System.out.println("1. View Schedule");
                System.out.println("2. Set Availability");
                System.out.println("3. View Available Slots");
                System.out.println("4. Accept Appointment");
                System.out.println("5. Decline Appointment");
                System.out.println("6. Add Consultation Notes");
                System.out.println("7. Add Prescription");
                System.out.println("8. Log Out");
                System.out.print("Enter your choice: ");

                // Validate input
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 8.");
                    scanner.next(); // Consume invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left by nextInt()

                // Handle menu options
                switch (choice) {
                    case 1:
                        doctor.viewSchedule();
                        break;
                    case 2:
                        setAvailability();
                        break;
                    case 3:
                        viewAvailableSlots();
                        break;
                    case 4:
                        acceptAppointment();
                        break;
                    case 5:
                        declineAppointment();
                        break;
                    case 6:
                        addConsultationNotes();
                        break;
                    case 7:
                        addPrescription();
                        break;
                    case 8:
                        System.out.println("Logging Out...");
                        return false; // Exit menu
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input to avoid infinite loops
            }
        }
    }

    // Set Availability
    private void setAvailability() {
        System.out.println("\n--- Set Availability ---");
        doctor.generateDefaultAvailability(); // Generate default slots
        System.out.println("Default slots generated. You can modify them manually if needed.");

        System.out.print("Do you want to mark any slot as unavailable? (yes/no): ");
        String response = scanner.next();
        scanner.nextLine(); // Consume newline

        if (response.equalsIgnoreCase("yes")) {
            List<TimeSlot> availability = doctor.getAvailability();
            System.out.println("Enter the index of the slot to mark unavailable (0-based):");
            for (int i = 0; i < availability.size(); i++) {
                System.out.println(i + ": " + availability.get(i));
            }
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (index >= 0 && index < availability.size()) {
                doctor.setCustomSlotAvailability(index, false); // Mark slot as unavailable
                System.out.println("Slot marked as unavailable.");
            } else {
                System.out.println("Invalid index.");
            }
        }

        // Sync the updated availability with the centralized repository
        DoctorAvailabilityRepository.getInstance()
                .setDoctorAvailability(doctor.getUserID(), doctor.getAvailability());
    }

    // View Available Slots
    private void viewAvailableSlots() {
        System.out.println("\n--- Available Slots ---");
        List<TimeSlot> slots = doctor.getAvailability();
        if (slots == null || slots.isEmpty()) {
            System.out.println("No available slots.");
        } else {
            for (int i = 0; i < slots.size(); i++) {
                System.out.println(i + ": " + slots.get(i));
            }
        }
    }

    // Accept Appointment
    private void acceptAppointment() {
        System.out.println("\n--- Accept Appointment ---");
        doctor.viewSchedule(); // Display current appointments
        System.out.print("Enter the index of the appointment to accept: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), true);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Decline Appointment
    private void declineAppointment() {
        System.out.println("\n--- Decline Appointment ---");
        doctor.viewSchedule(); // Display current appointments
        System.out.print("Enter the index of the appointment to decline: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), false);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Add Consultation Notes
    private void addConsultationNotes() {
        System.out.println("\n--- Add Consultation Notes ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();
        facade.addConsultationNotes(appointmentId, notes);
        System.out.println("Consultation notes added to appointment ID: " + appointmentId);
    }

    // Add Prescription
    private void addPrescription() {
        System.out.println("\n--- Add Prescription ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine();

        // Create a Medicine object with just the name
        Medicine medicine = new Medicine(medicineName, 0, 0); // Stock and alert level are ignored here

        // Create Prescription object with the Medicine
        Prescription prescription = new Prescription(medicine);

        // Add Prescription using facade
        facade.addPrescription(appointmentId, prescription);
        System.out.println("Prescription added to appointment ID: " + appointmentId);
    }
}