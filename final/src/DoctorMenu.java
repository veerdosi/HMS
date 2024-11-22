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
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Log Out");

            try {
                int choice = InputHandler.getIntInput(1, 8);

                switch (choice) {
                    case 1:
                        viewPatientMedicalRecords();
                        break;
                    case 2:
                        updatePatientMedicalRecords();
                        break;
                    case 3:
                        viewPersonalSchedule();
                        break;
                    case 4:
                        setAvailability();
                        break;
                    case 5:
                        handleAppointmentRequests();
                        break;
                    case 6:
                        viewUpcomingAppointments();
                        break;
                    case 7:
                        recordAppointmentOutcome();
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

    // View Patient Medical Records
    private void viewPatientMedicalRecords() {
        System.out.println("\n--- View Patient Medical Records ---");
        System.out.println("Enter the Patient ID to view their medical records:");
        System.out.println("0. Back to Main Menu");

        String patientId = InputHandler.getStringInput();

        if ("0".equals(patientId)) {
            return;
        }

        // Logic to fetch and display patient medical records
        // Use facade.getPatientById(patientId) to fetch details
    }

    // Update Patient Medical Records
    private void updatePatientMedicalRecords() {
        while (true) {
            System.out.println("\n--- Update Patient Medical Records ---");
            System.out.println("Enter the Patient ID to update their medical records:");
            System.out.println("Choose the information to update:");
            System.out.println("1. Add Diagnosis");
            System.out.println("2. Add Prescription");
            System.out.println("3. Update Treatment Plan");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 3);

            switch (choice) {
                case 1:
                    System.out.println("Enter Diagnosis Details:");
                    // Logic to add diagnosis
                    break;
                case 2:
                    System.out.println("Enter Prescription Details:");
                    // Logic to add prescription
                    break;
                case 3:
                    System.out.println("Enter Updated Treatment Plan:");
                    // Logic to update treatment plan
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the doctor's current availability slots.
     * If no availability is defined, a message is displayed to the doctor.
     */
    private void viewPersonalSchedule() {
        System.out.println("\n--- Available Slots ---");
        List<TimeSlot> slots = doctor.getAvailability();

        if (slots == null || slots.isEmpty()) {
            System.out.println("No available slots.");
            return;
        }

        // Print table header
        System.out.println("+----------+-----------+------------+");
        System.out.println("| Start    | End       | Status     |");
        System.out.println("+----------+-----------+------------+");

        // Print each slot in table format
        for (TimeSlot slot : slots) {
            System.out.printf("| %-8s | %-9s | %-10s |\n",
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.isAvailable() ? "Available" : "Booked");
        }

        System.out.println("+----------+-----------+------------+");
    }

    // Set Availability
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

        DoctorAvailabilityRepository.getInstance().setDoctorAvailability(doctor.getUserID(), doctor.getAvailability());
    }

    // Handle Appointment Requests
    private void handleAppointmentRequests() {
        while (true) {
            System.out.println("\n--- Accept or Decline Appointment Requests ---");
            System.out.println("1. Accept Appointment");
            System.out.println("2. Decline Appointment");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 2);

            switch (choice) {
                case 1:
                    System.out.println("Enter Appointment ID to Accept:");
                    // Logic to accept appointment
                    break;
                case 2:
                    System.out.println("Enter Appointment ID to Decline:");
                    // Logic to decline appointment
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View Upcoming Appointments
    private void viewUpcomingAppointments() {
        System.out.println("\n--- View Upcoming Appointments ---");
        AppointmentOutcomeRecord.getInstance().displayConfirmedAppointmentsByDoctor(doctor.getUserID());
        System.out.println("0. Back to Main Menu");
        InputHandler.getIntInput(0, 0);
    }

    private void recordAppointmentOutcome() {
        while (true) {
            System.out.println("\n--- Record Appointment Outcome ---");

            // Get the Appointment ID from the user
            String aptID = InputHandler.getStringInput("Enter the Appointment ID to record its outcome: ");

            System.out.println("Choose an action:");
            System.out.println("1. Add Consultation Notes");
            System.out.println("2. Add Prescriptions");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 2);

            switch (choice) {
                case 1:
                    String notes = InputHandler.getStringInput("Enter Consultation Notes: ");
                    try {
                        facade.addConsultationNotes(aptID, notes);
                        System.out.println("Consultation notes successfully added.");
                    } catch (Exception e) {
                        System.out.println("Error adding consultation notes: " + e.getMessage());
                    }
                    break;

                case 2:
                    String medicineName = InputHandler.getStringInput("Enter Medicine Name: ");

                    // Create a Medicine object
                    Medicine medicine = new Medicine(medicineName);

                    // Create a Prescription object
                    Prescription prescription = new Prescription(medicine);

                    try {
                        facade.addPrescription(aptID, prescription);
                        System.out.println("Prescription successfully added.");
                    } catch (Exception e) {
                        System.out.println("Error adding prescription: " + e.getMessage());
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
