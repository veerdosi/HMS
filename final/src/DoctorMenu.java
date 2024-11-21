import java.util.Scanner;
import java.util.List;

public class DoctorMenu {
    private Doctor doctor;
    private AppointmentServiceFacade facade;
    private Scanner scanner;

    public DoctorMenu(Doctor doctor, AppointmentServiceFacade facade, Scanner scanner) {
        this.doctor = doctor;
        this.facade = facade;
        this.scanner = scanner;
    }

    public boolean displayMenu() {
        while (true) {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View Schedule");
            System.out.println("2. Set Availability");
            System.out.println("3. View Available Slots");
            System.out.println("4. Accept Appointment");
            System.out.println("5. Decline Appointment");
            System.out.println("6. Add Consultation Notes");
            System.out.println("7. Add Prescription");
            System.out.println("8. Log Out");

            try {
                int choice = InputHandler.getIntInput(1, 8);

                switch (choice) {
                    case 1:
                        doctor.viewSchedule();
                        break; // Don't return true, just break
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
                        return false;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    // Rest of the methods remain the same...
    private void setAvailability() {
        System.out.println("\n--- Set Availability ---");
        doctor.generateDefaultAvailability(); // Generate default slots
        System.out.println("Default slots generated. You can modify them manually if needed.");

        System.out.print("Do you want to mark any slot as unavailable? (yes/no): ");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            List<TimeSlot> availability = doctor.getAvailability();
            System.out.println("Enter the index of the slot to mark unavailable (0-based):");
            for (int i = 0; i < availability.size(); i++) {
                System.out.println(i + ": " + availability.get(i));
            }

            int index = InputHandler.getIntInput(0, availability.size() - 1);

            if (index >= 0 && index < availability.size()) {
                doctor.setCustomSlotAvailability(index, false);
                System.out.println("Slot marked as unavailable.");
            } else {
                System.out.println("Invalid index.");
            }
        }

        DoctorAvailabilityRepository.getInstance()
                .setDoctorAvailability(doctor.getUserID(), doctor.getAvailability());
    }

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

    private void acceptAppointment() {
        System.out.println("\n--- Accept Appointment ---");
        doctor.viewSchedule();
        System.out.print("Enter the index of the appointment to accept: ");
        int index = InputHandler.getIntInput(0, doctor.getSchedule().size() - 1);

        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), true);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void declineAppointment() {
        System.out.println("\n--- Decline Appointment ---");
        doctor.viewSchedule();
        System.out.print("Enter the index of the appointment to decline: ");
        int index = InputHandler.getIntInput(0, doctor.getSchedule().size() - 1);

        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), false);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void addConsultationNotes() {
        System.out.println("\n--- Add Consultation Notes ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine();
        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();
        facade.addConsultationNotes(appointmentId, notes);
        System.out.println("Consultation notes added to appointment ID: " + appointmentId);
    }

    private void addPrescription() {
        System.out.println("\n--- Add Prescription ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.nextLine();
        System.out.print("Enter Medicine Name: ");
        String medicineName = scanner.nextLine();

        Medicine medicine = new Medicine(medicineName, 0, 0);
        Prescription prescription = new Prescription(medicine);
        facade.addPrescription(appointmentId, prescription);
        System.out.println("Prescription added to appointment ID: " + appointmentId);
    }
}