import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class PatientMenu {
    private Patient patient;
    private AppointmentServiceFacade facade;
    private AppointmentOutcomeRecord outcomeRecord;

    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.facade = AppointmentServiceFacade.getInstance(null, null);
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
    }

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
            System.out.println("9. Logout");
            System.out.println("");

            int choice = InputHandler.getIntInput(1, 9);

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
                    System.out.println("Logging out...");
                    return false;
            }
        }
    }

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

    private boolean validateContactNumber(String contactNumber) {
        String regex = "^[0-9]{8}$";
        return Pattern.matches(regex, contactNumber);
    }

    private boolean validateEmailAddress(String email) {
        String regex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, email);
    }

    private void updateContactNumber() {
        String newContact = InputHandler.getStringInput("Enter new contact number (8 digits): ");
        if (validateContactNumber(newContact)) {
            patient.updateContactNumber(newContact);
            System.out.println("Contact number updated successfully.");
        } else {
            System.out.println("Invalid contact number. It must be exactly 8 digits.");
        }
    }

    private void updateEmailAddress() {
        String newEmail = InputHandler.getStringInput("Enter new email address: ");
        if (validateEmailAddress(newEmail)) {
            patient.updateEmailAddress(newEmail);
            System.out.println("Email address updated successfully.");
        } else {
            System.out.println("Invalid email address format.");
        }
    }

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

    private void viewMedicalRecord() {
        System.out.println("Fetching Medical Record...");
        MedicalRecord record = patient.viewMedicalRecord(patient);
        if (record != null) {
            System.out.println(record);
        } else {
            System.out.println("No medical records found for this patient.");
        }
    }

    private void viewAvailableAppointments() {
        System.out.println("Available Appointments:");
        facade.getAvailableDoctors().forEach(System.out::println);
        System.out.println("");
    }

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

    private void cancelAppointment() {
        String appointmentId = InputHandler.getStringInput("Enter Appointment ID to Cancel: ");
        facade.cancelAppointment(appointmentId);
        System.out.println("Appointment canceled successfully.");
    }

    private void viewScheduledAppointments() {
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        appointments.stream()
                .filter(app -> app.getStatus() != AppointmentStatus.COMPLETED)
                .forEach(System.out::println);
    }

    private void viewPastAppointmentOutcomes() {
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        appointments.stream()
                .filter(app -> app.getStatus() == AppointmentStatus.COMPLETED)
                .forEach(System.out::println);
    }
}
