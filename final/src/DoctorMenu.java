import java.time.format.DateTimeFormatter;
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

    private void viewPatientMedicalRecords() {
        System.out.println("\n--- View Patient Medical Records ---");

        List<Appointment> confirmedAppointments = AppointmentOutcomeRecord.getInstance()
                .getConfirmedAppointmentsByDoctor(doctor.getUserID());
        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        System.out.println("Select an appointment to view the patient's medical record:");
        int index = 1;
        for (Appointment appointment : confirmedAppointments) {
            System.out.printf("%d. Appointment ID: %s, Patient ID: %s, Date & Time: %s\n",
                    index++, appointment.getId(), appointment.getPatientID(),
                    appointment.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }

        int selection = InputHandler.getIntInput(1, confirmedAppointments.size());
        Appointment selectedAppointment = confirmedAppointments.get(selection - 1);

        Patient patient = facade.getPatientById(selectedAppointment.getPatientID());
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        MedicalRecord record = patient.viewMedicalRecord(doctor);
        if (record != null) {
            record.displayRecord();
        }
    }

    private void updatePatientMedicalRecords() {
        System.out.println("\n--- Update Patient Medical Records ---");

        List<Appointment> confirmedAppointments = AppointmentOutcomeRecord.getInstance()
                .getConfirmedAppointmentsByDoctor(doctor.getUserID());
        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        System.out.println("Select an appointment to update the patient's medical record:");
        int index = 1;
        for (Appointment appointment : confirmedAppointments) {
            System.out.printf("%d. Appointment ID: %s, Patient ID: %s, Date & Time: %s\n",
                    index++, appointment.getId(), appointment.getPatientID(),
                    appointment.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }

        int selection = InputHandler.getIntInput(1, confirmedAppointments.size());
        Appointment selectedAppointment = confirmedAppointments.get(selection - 1);

        Patient patient = facade.getPatientById(selectedAppointment.getPatientID());
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        MedicalRecord record = patient.viewMedicalRecord(doctor);
        if (record == null) {
            System.out.println("Access denied to update the medical record.");
            return;
        }

        System.out.println("1. Add Diagnosis");
        System.out.println("2. Add Treatment");
        int choice = InputHandler.getIntInput(1, 2);

        if (choice == 1) {
            String diagnosis = InputHandler.getStringInput("Enter Diagnosis: ");
            record.addDiagnosis(diagnosis);
            System.out.println("Diagnosis added successfully.");
        } else if (choice == 2) {
            String treatment = InputHandler.getStringInput("Enter Treatment: ");
            record.addTreatment(treatment);
            System.out.println("Treatment added successfully.");
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

    private void handleAppointmentRequests() {
        while (true) {
            System.out.println("\n--- Accept or Decline Appointment Requests ---");

            // Get all appointments for this doctor that are in REQUESTED status
            List<Appointment> pendingAppointments = AppointmentOutcomeRecord.getInstance()
                    .getRequestedAppointmentsByDoctor(doctor.getUserID());

            if (pendingAppointments.isEmpty()) {
                System.out.println("No pending appointment requests.");
                return;
            }

            // Display all pending appointments
            System.out.println("\nPending Appointments:");
            System.out.println("+---------------+-------------+-------------------------+");
            System.out.println("| Appointment ID | Patient ID | Date & Time            |");
            System.out.println("+---------------+-------------+-------------------------+");

            for (Appointment apt : pendingAppointments) {
                System.out.printf("| %-13s | %-11s | %-21s |\n",
                        apt.getId(),
                        apt.getPatientID(),
                        apt.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }
            System.out.println("+---------------+-------------+-------------------------+");

            System.out.println("\nChoose an action:");
            System.out.println("1. Process Appointment Request");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 1);

            switch (choice) {
                case 1:
                    // Get appointment ID from user
                    String appointmentId = InputHandler.getStringInput("Enter Appointment ID to process: ");

                    // Verify the appointment exists and belongs to this doctor
                    Appointment selectedAppointment = pendingAppointments.stream()
                            .filter(apt -> apt.getId().equals(appointmentId))
                            .findFirst()
                            .orElse(null);

                    if (selectedAppointment == null) {
                        System.out.println("Invalid Appointment ID or appointment not found.");
                        continue;
                    }

                    // Get accept/decline decision
                    System.out.println("\nDo you want to accept this appointment?");
                    System.out.println("1. Accept");
                    System.out.println("2. Decline");

                    int decision = InputHandler.getIntInput(1, 2);
                    boolean accept = decision == 1;

                    try {
                        // First update the doctor's schedule if accepting
                        if (accept) {
                            // Book the time slot
                            if (!doctor.bookSlot(selectedAppointment.getDateTime().toLocalTime())) {
                                System.out.println("Error: Time slot is no longer available.");
                                continue;
                            }

                            // Add to doctor's schedule
                            doctor.getSchedule().add(selectedAppointment);
                        } else {
                            // If declining, ensure the slot is freed
                            doctor.freeSlot(selectedAppointment.getDateTime().toLocalTime());
                            // Remove from doctor's schedule if it was there
                            doctor.getSchedule().removeIf(apt -> apt.getId().equals(appointmentId));
                        }

                        // Then process the appointment through the facade
                        facade.processAppointment(appointmentId, accept);

                        System.out.println("Appointment " + (accept ? "accepted" : "declined") + " successfully.");

                    } catch (Exception e) {
                        System.out.println("Error processing appointment: " + e.getMessage());
                        // Rollback changes to doctor's schedule if there was an error
                        if (accept) {
                            doctor.freeSlot(selectedAppointment.getDateTime().toLocalTime());
                            doctor.getSchedule().removeIf(apt -> apt.getId().equals(appointmentId));
                        }
                    }
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
