import java.time.format.DateTimeFormatter;
import java.util.List;

public class DoctorMenu {
    private Doctor doctor;
    private AppointmentServiceFacade facade;
    private final AppointmentOutcomeRecord outcomeRecord;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public DoctorMenu(Doctor doctor, AppointmentServiceFacade facade) {
        this.doctor = doctor;
        this.facade = facade;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
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
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void viewPatientMedicalRecords() {
        System.out.println("\n--- View Patient Medical Records ---");
        List<Appointment> confirmedAppointments = outcomeRecord
                .getConfirmedAppointmentsByDoctor(doctor.getUserID());

        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        displayAppointmentsForRecordAccess(confirmedAppointments);
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
        List<Appointment> confirmedAppointments = outcomeRecord
                .getConfirmedAppointmentsByDoctor(doctor.getUserID());

        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments found.");
            return;
        }

        displayAppointmentsForRecordAccess(confirmedAppointments);
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
        } else {
            String treatment = InputHandler.getStringInput("Enter Treatment: ");
            record.addTreatment(treatment);
            System.out.println("Treatment added successfully.");
        }
    }

    private void displayAppointmentsForRecordAccess(List<Appointment> appointments) {
        System.out.println("\nSelect an appointment:");
        System.out.println("+-----+---------------+-------------+-------------------------+");
        System.out.println("| No. | Appointment ID| Patient ID  | Date & Time            |");
        System.out.println("+-----+---------------+-------------+-------------------------+");

        int index = 1;
        for (Appointment apt : appointments) {
            System.out.printf("| %-3d | %-13s | %-11s | %-21s |\n",
                    index++,
                    apt.getId(),
                    apt.getPatientID(),
                    apt.getDateTime().format(DATE_TIME_FORMATTER));
        }
        System.out.println("+-----+---------------+-------------+-------------------------+");
    }

    private void viewPersonalSchedule() {
        System.out.println("\n--- Available Slots ---");
        List<TimeSlot> slots = doctor.getAvailability();

        if (slots == null || slots.isEmpty()) {
            System.out.println("No available slots found.");
            return;
        }

        System.out.println("+-------------------------+-------------------------+------------+");
        System.out.println("| Start Date/Time         | End Date/Time          | Status     |");
        System.out.println("+-------------------------+-------------------------+------------+");

        for (TimeSlot slot : slots) {
            System.out.printf("| %-23s | %-23s | %-10s |\n",
                    slot.getStartDateTime().format(DATE_TIME_FORMATTER),
                    slot.getEndDateTime().format(DATE_TIME_FORMATTER),
                    slot.isAvailable() ? "Available" : "Booked");
        }
        System.out.println("+-------------------------+-------------------------+------------+");
    }

    private void setAvailability() {
        while (true) {
            System.out.println("\n--- Set Availability ---");
            System.out.println("1. Generate Default Weekly Schedule");
            System.out.println("2. Modify Specific Slots");
            System.out.println("0. Back");

            int choice = InputHandler.getIntInput(0, 2);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    doctor.generateDefaultAvailability();
                    System.out.println("Default weekly schedule generated.");
                    viewPersonalSchedule();
                    break;
                case 2:
                    modifySpecificSlots();
                    break;
            }
        }
    }

    private void modifySpecificSlots() {
        viewPersonalSchedule();
        List<TimeSlot> slots = doctor.getAvailability();
        if (slots.isEmpty()) {
            System.out.println("No slots available to modify.");
            return;
        }

        System.out.println("\nEnter the slot number to modify (0 to cancel): ");
        int slotIndex = InputHandler.getIntInput(0, slots.size());
        if (slotIndex == 0)
            return;

        slotIndex--; // Convert to 0-based index
        System.out.println("1. Mark as Available");
        System.out.println("2. Mark as Unavailable");

        int availabilityChoice = InputHandler.getIntInput(1, 2);
        boolean makeAvailable = availabilityChoice == 1;

        doctor.setCustomSlotAvailability(slotIndex, makeAvailable);
    }

    private void handleAppointmentRequests() {
        while (true) {
            System.out.println("\n--- Accept or Decline Appointment Requests ---");
            List<Appointment> pendingAppointments = outcomeRecord.getRequestedAppointmentsByDoctor(doctor.getUserID());

            if (pendingAppointments.isEmpty()) {
                System.out.println("No pending appointment requests.");
                return;
            }

            System.out.println("\nPending Appointments:");
            System.out.println("+---------------+-------------+-------------------------+");
            System.out.println("| Appointment ID| Patient ID  | Date & Time            |");
            System.out.println("+---------------+-------------+-------------------------+");

            for (Appointment apt : pendingAppointments) {
                System.out.printf("| %-13s | %-11s | %-21s |\n",
                        apt.getId(),
                        apt.getPatientID(),
                        apt.getDateTime().format(DATE_TIME_FORMATTER));
            }
            System.out.println("+---------------+-------------+-------------------------+");

            System.out.println("\nChoose an action:");
            System.out.println("1. Process Appointment Request");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 1);
            if (choice == 0)
                return;

            String appointmentId = InputHandler.getStringInput("Enter Appointment ID to process: ");
            Appointment selectedAppointment = pendingAppointments.stream()
                    .filter(apt -> apt.getId().equals(appointmentId))
                    .findFirst()
                    .orElse(null);

            if (selectedAppointment == null) {
                System.out.println("Invalid Appointment ID or appointment not found.");
                continue;
            }

            System.out.println("1. Accept");
            System.out.println("2. Decline");
            int decision = InputHandler.getIntInput(1, 2);
            boolean accept = decision == 1;

            try {
                // Remove the availability check since the slot is already reserved
                doctor.processAppointment(selectedAppointment, accept);
                facade.processAppointment(appointmentId, accept);
                System.out.println("Appointment " + (accept ? "accepted" : "declined") + " successfully.");
            } catch (Exception e) {
                System.out.println("Error processing appointment: " + e.getMessage());
            }
        }
    }

    private void viewUpcomingAppointments() {
        System.out.println("\n--- View Upcoming Appointments ---");
        outcomeRecord.displayConfirmedAppointmentsByDoctor(doctor.getUserID());
        System.out.println("\nPress 0 to return to main menu");
        InputHandler.getIntInput(0, 0);
    }

    private void recordAppointmentOutcome() {
        while (true) {
            System.out.println("\n--- Record Appointment Outcome ---");
            String aptID = InputHandler.getStringInput("Enter the Appointment ID (or 0 to return): ");

            if (aptID.equals("0"))
                return;

            System.out.println("Choose an action:");
            System.out.println("1. Add Consultation Notes");
            System.out.println("2. Add Prescriptions");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 2);
            switch (choice) {
                case 0:
                    return;
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
                    Medicine medicine = new Medicine(medicineName);
                    Prescription prescription = new Prescription(medicine);
                    try {
                        facade.addPrescription(aptID, prescription);
                        System.out.println("Prescription successfully added.");
                    } catch (Exception e) {
                        System.out.println("Error adding prescription: " + e.getMessage());
                    }
                    break;
            }
        }
    }
}