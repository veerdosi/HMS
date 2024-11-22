import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Revathi Selvasevaran
 *         The `PatientMenu` class provides an interactive menu for patients to
 *         manage appointments, update personal information, and reset
 *         passwords.
 *         It interacts with the `AppointmentServiceFacade` and
 *         `AppointmentOutcomeRecord` for appointment-related operations and
 *         uses
 *         the `PatientInfoUpdater` for managing personal information updates.
 */
public class PatientMenu {
    private final Patient patient;
    private final AppointmentServiceFacade facade;
    private final AppointmentOutcomeRecord outcomeRecord;
    private final PatientInfoUpdater infoUpdater;

    /**
     * Constructs a new `PatientMenu` for the specified patient.
     *
     * @param patient     The patient using the menu.
     * @param infoUpdater An instance of `PatientInfoUpdater` for managing personal
     *                    information updates.
     */
    public PatientMenu(Patient patient, PatientInfoUpdater infoUpdater) {
        this.patient = patient;
        this.infoUpdater = infoUpdater;
        this.facade = AppointmentServiceFacade.getInstance(null, null);
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
    }

    /**
     * Displays the main menu for the patient and handles menu navigation.
     *
     * @return `false` when the user chooses to log out, ending the menu loop.
     */

    public boolean displayMenu() {
        while (true) {
            System.out.println("");
            System.out.println("---- Patient Menu ----");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Reschedule Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Reset Password");
            System.out.println("0. Logout");
            System.out.println("");

            int choice = InputHandler.getIntInput(0, 9);

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    infoUpdater.displayUpdateInfoMenu(patient);
                    break;
                case 3:
                    viewAvailableAppointments();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastAppointmentOutcomes();
                    break;
                case 9:
                    resetPassword();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return false;
            }
        }
    }

    private void resetPassword() {
        String newPass = InputHandler.getStringInput("New Password: ");
        Patient p = this.patient;
        p.changePassword(newPass);
    }

    /**
     * Fetches and displays the patient's medical record.
     */
    private void viewMedicalRecord() {
        System.out.println("Fetching Medical Record...");
        MedicalRecord record = patient.viewMedicalRecord(patient);
        if (record != null) {
            System.out.println(record);
        } else {
            System.out.println("No medical records found for this patient.");
        }
    }

    /**
     * Displays available appointment slots for booking.
     */
    private void viewAvailableAppointments() {
        System.out.println("\n--- Available Appointments ---");
        List<DoctorAvailability> allAvailabilities = DoctorAvailabilityRepository.getInstance()
                .getAllDoctorAvailabilities();

        if (allAvailabilities == null || allAvailabilities.isEmpty()) {
            System.out.println("No available appointments found.");
            return;
        }

        // Print table header
        System.out.println("+----------+---------+-----------+------------+");
        System.out.println("| Doctor ID| Start   | End       | Status     |");
        System.out.println("+----------+---------+-----------+------------+");

        // Print each doctor's available slots
        for (DoctorAvailability docAvail : allAvailabilities) {
            List<TimeSlot> slots = docAvail.getSlots();
            for (TimeSlot slot : slots) {
                if (slot.isAvailable()) { // Only show available slots to patients
                    System.out.printf("| %-8s | %-7s | %-9s | %-10s |\n",
                            docAvail.getDoctorId(),
                            slot.getStartTime(),
                            slot.getEndTime(),
                            "Available");
                }
            }
        }

        System.out.println("+----------+---------+-----------+------------+");
    }

    /**
     * Schedules a new appointment for the patient.
     */
    private void scheduleAppointment() {
        System.out.println("\n--- Schedule New Appointment ---");

        // First show available slots and doctors
        viewAvailableAppointments();

        // Get all available doctors
        List<Doctor> availableDoctors = facade.getAvailableDoctors();
        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors are currently available for appointments.");
            return;
        }

        // Show doctor list
        System.out.println("\nAvailable Doctors:");
        for (Doctor doctor : availableDoctors) {
            System.out.println("Doctor ID: " + doctor.getUserID());
        }

        // Get doctor selection
        String doctorId = InputHandler.getStringInput("Enter Doctor ID from the list above: ");

        // Validate doctor selection
        DoctorAvailability docAvail = facade.getDoctorAvailability(doctorId);
        if (docAvail == null) {
            System.out.println("Invalid doctor ID or no availability for this doctor.");
            return;
        }

        // Get date from user
        String dateStr = InputHandler.getStringInput("Enter Appointment Date (dd-MM-yyyy): ");

        // Get hour ensuring it's within business hours
        int hour = InputHandler.getIntInput("Enter Appointment Hour (9-16): ", 9, 16);

        // Parse date and time
        LocalDateTime dateTime = Helper.parseDateAndHour(dateStr, hour);
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Invalid appointment date or time. Please select a future date and time.");
            return;
        }

        // Check if the selected time slot is available
        boolean timeSlotAvailable = false;
        for (TimeSlot slot : docAvail.getSlots()) {
            if (slot.isAvailable() &&
                    slot.getStartTime().equals(String.format("%02d:00", hour))) {
                timeSlotAvailable = true;
                break;
            }
        }

        if (!timeSlotAvailable) {
            System.out.println("The selected time slot is not available. Please choose another time.");
            return;
        }

        try {
            // Schedule the appointment
            facade.scheduleAppointment(patient, doctorId, dateTime);
            System.out.println("Appointment scheduled successfully!");
            System.out.println("Date: " + dateStr);
            System.out.println("Time: " + String.format("%02d:00", hour));
            System.out.println("Doctor ID: " + doctorId);
        } catch (Exception e) {
            System.out.println("Failed to schedule appointment: " + e.getMessage());
        }
    }

    /**
     * Reschedules an existing appointment.
     */
    private void rescheduleAppointment() {
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID to Reschedule: ");
        String dateStr = InputHandler.getStringInput("Enter New Appointment Date (dd-MM-yyyy): ");
        int hour = InputHandler.getIntInput("Enter Appointment Hour (9-16): ", 9, 16);

        LocalDateTime newDateTime = Helper.parseDateAndHour(dateStr, hour);
        if (facade.rescheduleAppointment(appointmentId, newDateTime)) {
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Failed to reschedule the appointment.");
        }
    }

    /**
     * Cancels an existing appointment.
     */
    private void cancelAppointment() {
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID to Cancel: ");
        facade.cancelAppointment(appointmentId);
        System.out.println("Appointment canceled successfully.");
    }

    /**
     * Displays all scheduled appointments for the patient.
     */
    private void viewScheduledAppointments() {
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        appointments.stream()
                .filter(app -> app.getStatus() != AppointmentStatus.COMPLETED)
                .forEach(System.out::println);
    }

    /**
     * Displays all past appointments and their outcomes.
     */
    private void viewPastAppointmentOutcomes() {
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        appointments.stream()
                .filter(app -> app.getStatus() == AppointmentStatus.COMPLETED)
                .forEach(System.out::println);
    }
}
