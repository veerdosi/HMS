import java.util.List;

/**
 * The `DoctorMenu` class represents the menu system that allows a doctor to
 * manage
 * their appointments, availability, and prescriptions. It provides interaction
 * options for the doctor to perform various tasks related to their role.
 */
public class DoctorMenu {
    private Doctor doctor;
    private AppointmentServiceFacade facade;

    /**
     * Constructs a `DoctorMenu` with the specified doctor and appointment service
     * facade.
     *
     * @param doctor The doctor associated with this menu. All actions performed in
     *               this menu
     *               are specific to this doctor.
     * @param facade The `AppointmentServiceFacade` instance that provides
     *               operations for
     *               managing appointments, prescriptions, and related activities.
     */
    public DoctorMenu(Doctor doctor, AppointmentServiceFacade facade) {
        this.doctor = doctor;
        this.facade = facade;
    }

    /**
     * Displays the doctor's menu and processes the doctor's input to execute
     * actions.
     * The menu runs in a loop until the doctor chooses to log out.
     *
     * @return `false` when the doctor logs out, terminating the menu loop.
     */
    public boolean displayMenu() {
        while (true) {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records ");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests ");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Log Out");

            try {
                int choice = InputHandler.getIntInput(1, 9);

                switch (choice) {
                    case 1:
                        
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        
                        break;
                    case 4:
                        
                        break;
                    case 5:
                        
                        break;
                    case 6:
                        
                        break;
                    case 7:
                        
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

    /**
     * Resets the doctor's password. Prompts the doctor for a new password and
     * updates it.
     */
    private void resetPassword() {
        String newPass = InputHandler.getStringInput("New Password: ");
        doctor.changePassword(newPass);
    }

    /**
     * Allows the doctor to set their availability. Generates default availability
     * slots
     * and provides an option to mark specific slots as unavailable.
     */
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

    /**
     * Displays the doctor's current availability slots.
     * If no availability is defined, a message is displayed to the doctor.
     */
    private void viewAvailableSlots() {
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

    /**
     * Allows the doctor to accept an appointment from their schedule.
     * The appointment is marked as accepted and added to the doctor's schedule.
     */
    private void acceptAppointment() {
        System.out.println("\n--- Accept Appointment ---");
        List<Appointment> appointments = doctor.getSchedule();

        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No pending appointments to accept.");
            return;
        }

        // Display pending appointments
        System.out.println("Pending Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment apt = appointments.get(i);
            if (apt.getStatus() == AppointmentStatus.REQUESTED) {
                System.out.printf("%d. Appointment ID: %s, Patient ID: %s, DateTime: %s\n",
                        i, apt.getId(), apt.getPatientID(), apt.getDateTime());
            }
        }

        int index = InputHandler.getIntInput("Enter the index of the appointment to accept: ", 0,
                appointments.size() - 1);

        if (index >= 0 && index < appointments.size()) {
            Appointment appointment = appointments.get(index);
            if (appointment.getStatus() == AppointmentStatus.REQUESTED) {
                // Use facade to process the appointment
                facade.processAppointment(appointment.getId(), true);

                // Update the appointment status in doctor's schedule
                appointment.setStatus(AppointmentStatus.CONFIRMED);
                System.out.println("Appointment accepted successfully.");
            } else {
                System.out.println("This appointment is not in pending status.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Allows the doctor to decline an appointment from their schedule.
     * The appointment is marked as declined and removed from the schedule.
     */
    private void declineAppointment() {
        System.out.println("\n--- Decline Appointment ---");
        List<Appointment> appointments = doctor.getSchedule();

        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No pending appointments to decline.");
            return;
        }

        // Display pending appointments
        System.out.println("Pending Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment apt = appointments.get(i);
            if (apt.getStatus() == AppointmentStatus.REQUESTED) {
                System.out.printf("%d. Appointment ID: %s, Patient ID: %s, DateTime: %s\n",
                        i, apt.getId(), apt.getPatientID(), apt.getDateTime());
            }
        }

        int index = InputHandler.getIntInput("Enter the index of the appointment to decline: ", 0,
                appointments.size() - 1);

        if (index >= 0 && index < appointments.size()) {
            Appointment appointment = appointments.get(index);
            if (appointment.getStatus() == AppointmentStatus.REQUESTED) {
                // Use facade to process the appointment
                facade.processAppointment(appointment.getId(), false);

                // Remove the declined appointment from doctor's schedule
                appointments.remove(index);
                System.out.println("Appointment declined successfully.");
            } else {
                System.out.println("This appointment is not in pending status.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Adds consultation notes for a specific appointment.
     * The doctor is prompted to enter the appointment ID and the consultation
     * notes.
     */
    private void addConsultationNotes() {
        System.out.println("\n--- Add Consultation Notes ---");
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID: ");
        String notes = InputHandler.getStringInput("Enter Notes: ");
        facade.addConsultationNotes(appointmentId, notes);
        System.out.println("Consultation notes added to appointment ID: " + appointmentId);
    }

    /**
     * Adds a prescription for a specific appointment.
     * The doctor is prompted to enter the appointment ID and the details of the
     * prescription.
     */
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