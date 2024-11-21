import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class PatientMenu {
    private Patient patient;
    private Scanner scanner;
    private AppointmentServiceFacade facade;
    private AppointmentOutcomeRecord outcomeRecord;

    // Constructor
    public PatientMenu(Patient patient, Scanner sharedScanner) {
        this.patient = patient;
        this.scanner = sharedScanner; // Shared scanner instance
        this.facade = AppointmentServiceFacade.getInstance("/Data/Patient_List(Sheet1).csv",
                "/Data/Staff_List(Sheet1).csv");
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
    }

    /**
     * Displays the Patient Menu.
     * 
     * @return boolean indicating whether to continue or exit.
     */
    public boolean displayMenu() {
        while (true) {
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
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                    scanner.nextLine(); // Consume invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice < 1 || choice > 9) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                    continue;
                }

                switch (choice) {
                    case 1 -> viewMedicalRecord();
                    case 2 -> updatePersonalInformation();
                    case 3 -> viewAvailableAppointments();
                    case 4 -> scheduleAppointment();
                    case 5 -> rescheduleAppointment();
                    case 6 -> cancelAppointment();
                    case 7 -> viewScheduledAppointments();
                    case 8 -> viewPastAppointmentOutcomes();
                    case 9 -> {
                        System.out.println("Logging out...");
                        return false; // Exit the menu
                    }
                }
        }
    }

    // Case 1: View Medical Record
    private void viewMedicalRecord() {
        System.out.println("Fetching Medical Record...");
        MedicalRecord record = patient.viewMedicalRecord(patient); // Pass the patient object
        if (record != null) {
            System.out.println(record);
        } else {
            System.out.println("No medical records found for this patient.");
        }
    }

    // Case 2: Update Personal Information
    private void updatePersonalInformation() {
        while (true) {
            System.out.println("---- Update Personal Information ----");
            System.out.println("1. Update Contact Number");
            System.out.println("2. Update Email Address");
            System.out.println("3. Update Both");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1 -> updateContactNumber();
                case 2 -> updateEmailAddress();
                case 3 -> updateBothContactInfo();
                case 4 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
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
        System.out.print("Enter new contact number (8 digits): ");
        String newContact = scanner.nextLine();
        if (validateContactNumber(newContact)) {
            patient.updateContactNumber(newContact);
            System.out.println("Contact number updated successfully.");
        } else {
            System.out.println("Invalid contact number. It must be exactly 8 digits.");
        }
    }

    private void updateEmailAddress() {
        System.out.print("Enter new email address: ");
        String newEmail = scanner.nextLine();
        if (validateEmailAddress(newEmail)) {
            patient.updateEmailAddress(newEmail);
            System.out.println("Email address updated successfully.");
        } else {
            System.out.println("Invalid email address. It must contain '@'.");
        }
    }

    private void updateBothContactInfo() {
        System.out.print("Enter new contact number (8 digits): ");
        String newContact = scanner.nextLine();
        System.out.print("Enter new email address: ");
        String newEmail = scanner.nextLine();

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

    // Case 3: View Available Appointments
    private void viewAvailableAppointments() {
        System.out.println("Available Appointments:");
        facade.getAvailableDoctors().forEach(System.out::println);
    }

    // Case 4: Schedule Appointment
    private void scheduleAppointment() {
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();

        System.out.print("Enter Appointment Date (dd-MM-yyyy): ");
        String dateStr = scanner.nextLine();
        System.out.print("Enter Appointment Hour (9-16): ");
        int hour = scanner.nextInt();

        LocalDateTime dateTime = DateTimeHelper.parseDateAndHour(dateStr, hour);
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("Invalid appointment date or time.");
            return;
        }

        facade.scheduleAppointment(patient, doctorId, dateTime);
        System.out.println("Appointment scheduled successfully.");
    }

    // Case 5: Reschedule Appointment
    private void rescheduleAppointment() {
        System.out.print("Enter Appointment ID to Reschedule: ");
        String appointmentId = scanner.nextLine();

        System.out.print("Enter New Appointment Date (dd-MM-yyyy): ");
        String dateStr = scanner.nextLine();
        System.out.print("Enter Appointment Hour (9-16): ");
        int hour = scanner.nextInt();

        LocalDateTime newDateTime = DateTimeHelper.parseDateAndHour(dateStr, hour);
        if (facade.rescheduleAppointment(appointmentId, newDateTime)) {
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Failed to reschedule the appointment.");
        }
    }

    // Case 6: Cancel Appointment
    private void cancelAppointment() {
        System.out.print("Enter Appointment ID to Cancel: ");
        String appointmentId = scanner.nextLine();
        facade.cancelAppointment(appointmentId);
        System.out.println("Appointment canceled successfully.");
    }

    // Case 7: View Scheduled Appointments
    private void viewScheduledAppointments() {
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        appointments.stream()
                .filter(app -> app.getStatus() != AppointmentStatus.COMPLETED)
                .forEach(System.out::println);
    }

    // Case 8: View Past Appointments
    private void viewPastAppointmentOutcomes() {
        List<Appointment> appointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());
        appointments.stream()
                .filter(app -> app.getStatus() == AppointmentStatus.COMPLETED)
                .forEach(System.out::println);
    }
}