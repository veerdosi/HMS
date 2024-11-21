import java.util.List;

public class DoctorMenu {
    private Doctor doctor;
    private AppointmentServiceFacade facade;

    public DoctorMenu(Doctor doctor, AppointmentServiceFacade facade) {
        this.doctor = doctor;
        this.facade = facade;
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
                        return false;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private void setAvailability() {
        System.out.println("\n--- Set Availability ---");
        doctor.generateDefaultAvailability(); // Generate default slots
        System.out.println("Default slots generated. You can modify them manually if needed.");

        boolean modifySlots = InputHandler.getYesNoInput("Do you want to mark any slot as unavailable?");
        if (modifySlots) {
            List<TimeSlot> availability = doctor.getAvailability();
            System.out.println("Enter the index of the slot to mark unavailable (0-based):");
            for (int i = 0; i < availability.size(); i++) {
                System.out.println(i + ": " + availability.get(i));
            }

            int index = InputHandler.getIntInput("Enter the slot index: ", 0, availability.size() - 1);

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
        int index = InputHandler.getIntInput("Enter the index of the appointment to accept: ", 0,
                doctor.getSchedule().size() - 1);

        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), true);
            System.out.println("Appointment accepted.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void declineAppointment() {
        System.out.println("\n--- Decline Appointment ---");
        doctor.viewSchedule();
        int index = InputHandler.getIntInput("Enter the index of the appointment to decline: ", 0,
                doctor.getSchedule().size() - 1);

        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), false);
            System.out.println("Appointment declined.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void addConsultationNotes() {
        System.out.println("\n--- Add Consultation Notes ---");
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID: ");
        String notes = InputHandler.getStringInput("Enter Notes: ");
        facade.addConsultationNotes(appointmentId, notes);
        System.out.println("Consultation notes added to appointment ID: " + appointmentId);
    }

    private void addPrescription() {
        System.out.println("\n--- Add Prescription ---");
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID: ");
        String medicineName = InputHandler.getStringInput("Enter Medicine Name: ");

        Medicine medicine = new Medicine(medicineName, 0, 0);
        Prescription prescription = new Prescription(medicine);
        facade.addPrescription(appointmentId, prescription);
        System.out.println("Prescription added to appointment ID: " + appointmentId);
    }
}
