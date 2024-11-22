import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            System.out.println("0. Logout");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Reschedule Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Reset Password");
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
 * Displays all available appointments for all doctors in a table format.
 * Combines previous implementations for streamlined output.
 */
private void viewAvailableAppointments() {
    System.out.println("\n--- Available Appointments ---");

    // Fetch all doctor availabilities
    List<DoctorAvailability> allAvailabilities = DoctorAvailabilityRepository.getInstance()
            .getAllDoctorAvailabilities();

    // Check if no availabilities exist
    if (allAvailabilities == null || allAvailabilities.isEmpty()) {
        System.out.println("No available appointments found.");
        return;
    }

    // Print table header
    System.out.println("+----------+---------+");
    System.out.println("| Doctor ID| Time    |");
    System.out.println("+----------+---------+");

    // Print each doctor's available slots in a table format
    for (DoctorAvailability docAvail : allAvailabilities) {
        List<TimeSlot> slots = docAvail.getSlots();
        for (TimeSlot slot : slots) {
            if (slot.isAvailable()) { // Only show available slots
                System.out.printf("| %-8s | %-7s |\n",
                        docAvail.getDoctorId(),
                        slot.getStartTime());
            }
        }
    }

    System.out.println("+----------+---------+");
}


    /**
 * Schedules a new appointment for the patient using indexed selections.
 */
private void scheduleAppointment() {
    System.out.println("\n--- Schedule New Appointment ---");

    // Step 1: Display available appointments
    viewAvailableAppointments();

    // Step 2: Select Doctor
    String doctorId = selectDoctorFromAvailableAppointments();
    if (doctorId == null) return;

    // Step 3: Select Time Slot
    String startTime = selectTimeSlot(doctorId);
    if (startTime == null) return;

    // Step 4: Prompt for Appointment Date
    String dateStr = InputHandler.getStringInput("Enter the appointment date (dd-MM-yyyy): ");
    LocalDateTime dateTime;
    try {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        dateTime = LocalDateTime.parse(dateStr + " " + startTime, dateFormatter);

        if (dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Cannot schedule an appointment in the past.");
            return;
        }
    } catch (Exception e) {
        System.out.println("Invalid date format. Please use dd-MM-yyyy.");
        return;
    }

    // Step 5: Schedule the Appointment
    try {
        facade.scheduleAppointment(patient, doctorId, dateTime);
        System.out.println("Appointment successfully scheduled on " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    } catch (Exception e) {
        System.out.println("Failed to schedule appointment: " + e.getMessage());
    }
}


        /**
     * Reschedules an existing appointment using indexed selections.
     */
    private void rescheduleAppointment() {
        System.out.println("\n--- Reschedule Appointment ---");

        // Step 1: Display Patient's Appointments and Select One
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        if (appointments.isEmpty()) {
            System.out.println("No appointments to reschedule.");
            return;
        }

        System.out.println("Select an appointment to reschedule:");
        int index = 1;
        for (Appointment app : appointments) {
            System.out.printf("%d. Appointment ID: %s, Doctor ID: %s, Date: %s\n",
                    index, app.getId(), app.getDoctorID(),
                    app.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            index++;
        }

        int selection = InputHandler.getIntInput(1, appointments.size());
        String appointmentId = appointments.get(selection - 1).getId();

        // Step 2: Display Available Appointments and Select New Doctor
        viewAvailableAppointments();

        String newDoctorId = selectDoctorFromAvailableAppointments();
        if (newDoctorId == null) return;

        // Step 3: Select New Time Slot
        String newStartTime = selectTimeSlot(newDoctorId);
        if (newStartTime == null) return;

        // Step 4: Prompt for New Date
        String newDateStr = InputHandler.getStringInput("Enter the new appointment date (dd-MM-yyyy): ");
        LocalDateTime newDateTime;
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            newDateTime = LocalDateTime.parse(newDateStr + " " + newStartTime, dateFormatter);

            if (newDateTime.isBefore(LocalDateTime.now())) {
                System.out.println("Cannot reschedule to a past date or time.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            return;
        }

        // Step 5: Attempt Rescheduling
        if (facade.rescheduleAppointment(appointmentId, newDateTime)) {
            System.out.println("Appointment successfully rescheduled to " + newDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        } else {
            System.out.println("Failed to reschedule the appointment.");
        }
    }


    /**
     * Cancels an existing appointment using indexed selection.
     */
    private void cancelAppointment() {
        System.out.println("\n--- Cancel Appointment ---");

        // Step 1: Fetch and Display Patient's Appointments
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        List<Appointment> cancelableAppointments = appointments.stream()
                .filter(app -> app.getStatus() != AppointmentStatus.COMPLETED)
                .toList();

        if (cancelableAppointments.isEmpty()) {
            System.out.println("No appointments to cancel.");
            return;
        }

        System.out.println("Select an appointment to cancel:");
        int index = 1;
        for (Appointment app : cancelableAppointments) {
            System.out.printf("%d. Appointment ID: %s, Doctor ID: %s, Date: %s\n",
                    index, app.getId(), app.getDoctorID(),
                    app.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            index++;
        }

        int selection = InputHandler.getIntInput(1, cancelableAppointments.size());
        String appointmentId = cancelableAppointments.get(selection - 1).getId();

        // Step 2: Cancel the Appointment
        try {
            facade.cancelAppointment(appointmentId);
            System.out.println("Appointment canceled successfully.");
        } catch (Exception e) {
            System.out.println("Failed to cancel appointment: " + e.getMessage());
        }
    }


        /**
     * Displays all scheduled (upcoming) appointments for the patient in a tabular format.
     */
    private void viewScheduledAppointments() {
        System.out.println("\n--- Scheduled Appointments ---");

        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        List<Appointment> scheduledAppointments = appointments.stream()
                .filter(app -> app.getStatus() != AppointmentStatus.COMPLETED)
                .toList();

        if (scheduledAppointments.isEmpty()) {
            System.out.println("No scheduled appointments found.");
            return;
        }

        // Print table header
        System.out.println("+---------------+----------+----------+---------------------+");
        System.out.println("| Appointment ID| Doctor ID| Status   | Date & Time         |");
        System.out.println("+---------------+----------+----------+---------------------+");

        // Print each scheduled appointment
        for (Appointment app : scheduledAppointments) {
            System.out.printf("| %-13s | %-8s | %-8s | %-19s |\n",
                    app.getId(),
                    app.getDoctorID(),
                    app.getStatus(),
                    app.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }

        System.out.println("+---------------+----------+----------+---------------------+");
    }


        /**
     * Displays all past appointments and their outcomes in a tabular format.
     */
    private void viewPastAppointmentOutcomes() {
        System.out.println("\n--- Past Appointment Outcomes ---");

        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        List<Appointment> pastAppointments = appointments.stream()
                .filter(app -> app.getStatus() == AppointmentStatus.COMPLETED)
                .toList();

        if (pastAppointments.isEmpty()) {
            System.out.println("No past appointments found.");
            return;
        }

        // Print table header
        System.out.println("+---------------+----------+----------+---------------------+------------------+");
        System.out.println("| Appointment ID| Doctor ID| Status   | Date & Time         | Notes            |");
        System.out.println("+---------------+----------+----------+---------------------+------------------+");

        // Print each past appointment
        for (Appointment app : pastAppointments) {
            System.out.printf("| %-13s | %-8s | %-8s | %-19s | %-16s |\n",
                    app.getId(),
                    app.getDoctorID(),
                    app.getStatus(),
                    app.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                    app.getConsultationNotes() == null ? "None" : app.getConsultationNotes());
        }

        System.out.println("+---------------+----------+----------+---------------------+------------------+");
    }


    
    
    
    
    //Utility methods for appointment-related functions
    
    
    
    
    /**
    * Prompts the user to select a doctor from the list of available appointments.
     *
     * @return The selected Doctor ID, or null if no valid selection is made.
    */
    private String selectDoctorFromAvailableAppointments() {
        List<DoctorAvailability> allAvailabilities = facade.getAllDoctorAvailabilities();
        if (allAvailabilities == null || allAvailabilities.isEmpty()) {
            System.out.println("No available appointments found.");
            return null;
        }

        System.out.println("Select a doctor by entering the corresponding number:");
        int index = 1;
        for (DoctorAvailability docAvail : allAvailabilities) {
            System.out.println(index + ". Doctor ID: " + docAvail.getDoctorId());
            index++;
        }

        int selection = InputHandler.getIntInput(1, allAvailabilities.size());
        return allAvailabilities.get(selection - 1).getDoctorId();
    }
    /**
     * Prompts the user to select a time slot for a given doctor.
     *
     * @param doctorId The ID of the doctor whose availability is displayed.
     * @return The selected time slot as a string, or null if no valid selection is made.
     */
    private String selectTimeSlot(String doctorId) {
        DoctorAvailability docAvail = facade.getDoctorAvailability(doctorId);
        if (docAvail == null) {
            System.out.println("No availability found for the selected doctor.");
            return null;
        }

        System.out.println("\nAvailable Time Slots:");
        List<TimeSlot> slots = docAvail.getSlots();
        int index = 1;
        for (TimeSlot slot : slots) {
            if (slot.isAvailable()) {
                System.out.println(index + ". " + slot.getStartTime() + "-" + slot.getEndTime());
                index++;
            }
        }

        int selection = InputHandler.getIntInput(1, slots.size());
        return slots.get(selection - 1).getStartTime();
    }


    
}
