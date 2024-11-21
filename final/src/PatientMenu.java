import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The `PatientMenu` class provides an interactive menu for patients to access
 * various medical appointment-related functionalities, such as viewing and managing
 * appointments, updating personal information, and accessing medical records.
 */
public class PatientMenu {
    private Patient patient;
    private AppointmentServiceFacade facade;
    private AppointmentOutcomeRecord outcomeRecord;

    /**
     * Constructs a new `PatientMenu` instance.
     *
     * @param patient The patient associated with this menu.
     */
    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.facade = AppointmentServiceFacade.getInstance(null, null);
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
    }

    /**
     * Displays the main menu for the patient and processes the selected options.
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
                    updatePersonalInformation();
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
    private void resetPassword(){
        String newPass = InputHandler.getStringInput("New Password: ");
        Patient p = this.patient;
        p.changePassword(newPass);
    }
    /**
     * Displays a submenu for updating the patient's personal information,
     * including contact number and email address.
     */
    private void updatePersonalInformation() {
        while (true) {
            System.out.println("");
            System.out.println("---- Update Personal Information ----");
            System.out.println("1. Update Contact Number");
            System.out.println("2. Update Email Address");
            System.out.println("3. Update Both");
            System.out.println("4. Back to Main Menu");
            System.out.println("");
            System.out.print("Enter your choice: ");
            int option = InputHandler.getIntInput(1, 4);

            switch (option) {
                case 1:
                    updateContactNumber();
                    break;
                case 2:
                    updateEmailAddress();
                    break;
                case 3:
                    updateBothContactInfo();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return; // Exit the submenu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Validates if a contact number is valid (must be exactly 8 digits).
     *
     * @param contactNumber The contact number to validate.
     * @return `true` if valid, `false` otherwise.
     */
    private boolean validateContactNumber(String contactNumber) {
        String regex = "^[0-9]{8}$";
        return Pattern.matches(regex, contactNumber);
    }

    /**
     * Validates if an email address is in a valid format.
     *
     * @param email The email address to validate.
     * @return `true` if valid, `false` otherwise.
     */
    private boolean validateEmailAddress(String email) {
        String regex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, email);
    }

    /**
     * Updates the patient's contact number.
     */
    private void updateContactNumber() {
        String newContact = InputHandler.getStringInput("Enter new contact number (8 digits): ");
        if (validateContactNumber(newContact)) {
            patient.updateContactNumber(newContact);
            System.out.println("Contact number updated successfully.");
        } else {
            System.out.println("Invalid contact number. It must be exactly 8 digits.");
        }
    }

    /**
     * Updates the patient's email address.
     */
    private void updateEmailAddress() {
        String newEmail = InputHandler.getStringInput("Enter new email address: ");
        if (validateEmailAddress(newEmail)) {
            patient.updateEmailAddress(newEmail);
            System.out.println("Email address updated successfully.");
        } else {
            System.out.println("Invalid email address format.");
        }
    }

    /**
     * Updates both the patient's contact number and email address.
     */
    private void updateBothContactInfo() {
        String newContact = InputHandler.getStringInput("Enter new contact number (8 digits): ");
        String newEmail = InputHandler.getStringInput("Enter new email address: ");

        boolean isContactValid = validateContactNumber(newContact);
        boolean isEmailValid = validateEmailAddress(newEmail);

        if (isContactValid && isEmailValid) {
            patient.updateContactInfo(newEmail, newContact);
            System.out.println("Contact information updated successfully.");
        } else {
            if (!isContactValid)
                System.out.println("Invalid contact number.");
            if (!isEmailValid)
                System.out.println("Invalid email address.");
        }
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
        System.out.println("Available Appointments:");
        facade.getAllDoctorAvailabilities();
        //facade.getAvailableDoctors().forEach(System.out::println);
        System.out.println("");
    }

    /**
     * Schedules a new appointment for the patient.
     */
    private void scheduleAppointment() {
        String doctorId = InputHandler.getStringInput("Enter Doctor ID: ");
        String dateStr = InputHandler.getStringInput("Enter Appointment Date (dd-MM-yyyy): ");
        int hour = InputHandler.getIntInput("Enter Appointment Hour (9-16): ", 9, 16);

        LocalDateTime dateTime = Helper.parseDateAndHour(dateStr, hour);
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Invalid appointment date or time.");
            return;
        }

        facade.scheduleAppointment(patient, doctorId, dateTime);
        System.out.println("Appointment scheduled successfully.");
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
