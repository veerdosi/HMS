import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class PatientMenu {
    private Patient patient;
    private Scanner scanner;
    AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance("/Data/Patient_List(Sheet1).csv",
            "/Data/Staff_List(Sheet1).csv");
    AppointmentOutcomeRecord outcomeRecord = AppointmentOutcomeRecord.getInstance();

    public PatientMenu(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return boolean
     */
    public boolean displayMenu() {
        Scanner scanner = new Scanner(System.in);

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

            // Validate input
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                scanner.next(); // Consume the invalid input
                continue; // Repeat the menu prompt
            }

            int choice = scanner.nextInt();

            // Validate the range of the choice
            if (choice < 1 || choice > 9) {
                System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                continue; // Repeat the menu prompt
            }

            // Handle valid choices
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
                    return false; // Exit the menu
            }
        }
    }

    // CASE 1: View Medical
    // Record////////////////////////////////////////////////////////////////////////
    private void viewMedicalRecord() {
        // Implement view medical record
        System.out.println("Fetching Medical Record...");
        patient.viewMedicalRecord(patient);
    }

    // CASE 2: Update Personal
    // Information///////////////////////////////////////////////////////////////
    private void updatePersonalInformation() {
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---- Update Personal Information ----");
            System.out.println("1. Update Contact Number");
            System.out.println("2. Update Email Address");
            System.out.println("3. Update Both");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    updateContactNumber(scanner);
                    break;
                case 2:
                    updateEmailAddress(scanner);
                    break;
                case 3:
                    updateBothContactInfo(scanner);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return; // Exit the submenu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Input-Validation -MobileNum
    private boolean validateContactNumber(String contactNumber) {
        // Regex to validate exactly 8 digits
        String regex = "^[0-9]{8}$";
        return Pattern.matches(regex, contactNumber);
    }

    // Input-Validation -Email
    private boolean validateEmailAddress(String email) {
        // Regex to validate presence of @
        String regex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, email);
    }

    private void updateContactNumber(Scanner scanner) {
        System.out.print("Enter new contact number (8 digits): ");
        String newContact = scanner.next();
        if (validateContactNumber(newContact)) {
            patient.updateContactNumber(newContact);
        } else {
            System.out.println("Invalid contact number. It must be exactly 8 digits.");
        }
    }

    private void updateEmailAddress(Scanner scanner) {
        System.out.print("Enter new email address: ");
        String newEmail = scanner.next();
        if (validateEmailAddress(newEmail)) {
            patient.updateEmailAddress(newEmail);
        } else {
            System.out.println("Invalid email address. It must contain '@'.");
        }
    }

    private void updateBothContactInfo(Scanner scanner) {
        System.out.print("Enter new contact number (8 digits): ");
        String newContact = scanner.next();
        System.out.print("Enter new email address: ");
        String newEmail = scanner.next();

        boolean isContactValid = validateContactNumber(newContact);
        boolean isEmailValid = validateEmailAddress(newEmail);

        if (isContactValid && isEmailValid) {
            patient.updateContactInfo(newEmail, newContact);
        } else {
            if (!isContactValid) {
                System.out.println("Invalid contact number. It must be exactly 8 digits.");
            }
            if (!isEmailValid) {
                System.out.println("Invalid email address. It must contain '@'.");
            }
        }
    }

    // CASE 3: View Available Appointment
    // Slots/////////////////////////////////////////////////////////
    private void viewAvailableAppointments() {
        // print out Appointments available today
        System.out.println("Fetching available appointments...");
        facade.getAvailableDoctors().forEach(doctor -> System.out.println(doctor));
    }

    // CASE 4: Schedule
    // Appointment/////////////////////////////////////////////////////////////////////
    private void scheduleAppointment() {
        scanner = new Scanner(System.in);
        String doctorId;
        LocalDateTime dateTime = null;

        // Prompt for Doctor ID
        while (true) {
            System.out.println("Enter Doctor ID:");
            doctorId = scanner.next();
            if (doctorId != null && !doctorId.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        }

        // Prompt for Appointment Date and Hour
        while (true) {
            System.out.println("Enter Appointment Date (dd-MM-yyyy):");
            String dateStr = scanner.next();
            System.out.println("Enter Appointment Hour (9-16):");
            int hour;

            try {
                hour = scanner.nextInt();
                if (hour < 9 || hour > 16) {
                    System.out.println("Invalid hour. Please enter a value between 9 and 16.");
                    continue;
                }

                // Parse and set the date-time with hour and 00 minutes
                dateTime = DateTimeHelper.parseDateAndHour(dateStr, hour);
                if (dateTime == null) {
                    System.out.println("Invalid date format. Please try again.");
                } else if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println(
                            "The appointment date and time cannot be in the past. Please enter a valid future date and time.");
                } else {
                    break; // Valid date and time entered
                }
            } catch (Exception e) {
                System.out.println("Invalid input for hour. Please enter a numeric value between 9 and 16.");
                scanner.next(); // Clear invalid input
            }
        }

        // Schedule the appointment
        facade.scheduleAppointment(patient, doctorId, dateTime);
        System.out.println("Appointment scheduled successfully.");
        scanner.close();
    }

    // CASE 5: Reschedule
    // Appointment/////////////////////////////////////////////////////////////////////////////////////
    private void rescheduleAppointment() {
        scanner = new Scanner(System.in);
        String appointmentId;
        LocalDateTime newDateTime = null;

        // Prompt for Appointment ID
        while (true) {
            System.out.println("Enter Appointment ID to Reschedule:");
            appointmentId = scanner.next();
            if (appointmentId != null && !appointmentId.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Invalid Appointment ID. Please try again.");
            }
        }

        // Prompt for New Date and Hour
        while (true) {
            System.out.println("Enter New Appointment Date (dd-MM-yyyy):");
            String dateStr = scanner.next();
            System.out.println("Enter Appointment Hour (9-16):");
            int hour;

            try {
                hour = scanner.nextInt();
                if (hour < 9 || hour > 16) {
                    System.out.println("Invalid hour. Please enter a value between 9 and 16.");
                    continue;
                }

                // Parse and set the date-time with hour and 00 minutes
                newDateTime = DateTimeHelper.parseDateAndHour(dateStr, hour);
                if (newDateTime == null) {
                    System.out.println("Invalid date format. Please try again.");
                } else if (newDateTime.isBefore(LocalDateTime.now())) {
                    System.out.println(
                            "The new appointment date and time cannot be in the past. Please enter a valid future date and time.");
                } else {
                    break; // Valid date and time entered
                }
            } catch (Exception e) {
                System.out.println("Invalid input for hour. Please enter a numeric value between 9 and 16.");
                scanner.next(); // Clear invalid input
            }
        }

        // Attempt to reschedule the appointment
        if (facade.rescheduleAppointment(appointmentId, newDateTime)) {
            System.out.println("Appointment rescheduled successfully.");
        } else {
            System.out.println("Failed to reschedule the appointment. Please check the Appointment ID and try again.");
        }
        scanner.close();
    }

    // CASE 6: Cancel
    // Appointment//////////////////////////////////////////////////////////////////////////
    private void cancelAppointment() {
        viewScheduledAppointments();
        scanner = new Scanner(System.in);
        System.out.println("Enter Appointment ID to Cancel:");
        String appointmentId = scanner.next();
        facade.processAppointment(appointmentId, false); // Assuming 'false' means cancellation
        System.out.println("Appointment canceled successfully.");
    }

    // CASE 7: View Scheduled
    // Appointments///////////////////////////////////////////////////////////////
    private void viewScheduledAppointments() {
        // Retrieve all appointments for the patient
        List<Appointment> allAppointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());

        // Filter appointments where status != COMPLETED
        List<Appointment> nonCompletedAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getStatus() != AppointmentStatus.COMPLETED)
                .toList();

        // Display the filtered list
        if (nonCompletedAppointments.isEmpty()) {
            System.out.println("No scheduled appointments found.");
        } else {
            System.out.println("Scheduled Appointments (Status shown):");
            nonCompletedAppointments.forEach(
                    appointment -> System.out.println(appointment + " [Status: " + appointment.getStatus() + "]"));
        }
    }

    // CASE 8:
    // ViewPastAppointmentRecords////////////////////////////////////////////////////////////////
    private void viewPastAppointmentOutcomes() {
        // Retrieve all past appointments for the patient
        List<Appointment> pastAppointments = outcomeRecord.getAppointmentsByPatient(patient.getUserID());

        // Filter appointments to include only those with status COMPLETED
        List<Appointment> completedAppointments = pastAppointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.COMPLETED)
                .toList();

        // Display the filtered list
        if (completedAppointments.isEmpty()) {
            System.out.println("No past appointments found.");
        } else {
            System.out.println("Past Appointment Records:");
            completedAppointments.forEach(System.out::println);
        }
    }

}
