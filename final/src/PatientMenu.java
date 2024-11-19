import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Date;

public class PatientMenu {
    private Patient patient;
    AppointmentServiceFacade facade = AppointmentServiceFacade.getInstance(null, null);   
    AppointmentOutcomeRecord outcomeRecord = AppointmentOutcomeRecord.getInstance();

    public PatientMenu(Patient patient, AppointmentServiceFacade facade) {
        this.patient = patient;
        this.facade = facade;
    }

    public boolean display() {
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
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    facade.viewAvailableAppointments(patient.getUserID());
                    // show list of doctors with specialties
                    // choose doctor
                    // check doctor availability
                    break;
                case 4:
                    menu_scheduleAppt(patient);
                    //should this call the schedule appointment from patient class
                    //Ask lis
                    // choose date and time from there
                    // get manual date and time
                    // send in appointment creation - patientID, doctorID, appointmentDate
                    
                    facade.scheduleAppointment(this.patient, null, null); 
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    outcomeRecord.getAppointmentsByPatient(patient.getUserID());;
                    break;
                case 8:
                    facade.viewPastAppointments(patient.getUserID());
                    break;
                case 9:
                    System.out.println("Logging out...");
                    return false; // Return false to signal logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //CASE 1: View Medical Record
    private void viewMedicalRecord() {
        // Implement view medical record
        System.out.println("Fetching Medical Record...");
        patient.viewMedicalRecord(patient);
    }
    //CASE 2: Update Personal Information
    private void updatePersonalInformation() {
        Scanner scanner = new Scanner(System.in);

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
    //Input-Validation -MobileNum
    private boolean validateContactNumber(String contactNumber) {
        // Regex to validate exactly 8 digits
        String regex = "^[0-9]{8}$";
        return Pattern.matches(regex, contactNumber);
    }
    //Input-Validation -Email
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
            System.out.println("Contact number updated successfully.");
        } else {
            System.out.println("Invalid contact number. It must be exactly 8 digits.");
        }
    }

    private void updateEmailAddress(Scanner scanner) {
        System.out.print("Enter new email address: ");
        String newEmail = scanner.next();
        if (validateEmailAddress(newEmail)) {
            patient.updateEmailAddress(newEmail);
            System.out.println("Email address updated successfully.");
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
            System.out.println("Contact information updated successfully.");
        } else {
            if (!isContactValid) {
                System.out.println("Invalid contact number. It must be exactly 8 digits.");
            }
            if (!isEmailValid) {
                System.out.println("Invalid email address. It must contain '@'.");
            }
        }
    }

    private void viewAvailableAppointments() {
        //print out Appointments available today
        System.out.println("These are the appointment slots available today. Enter");
    }

    private void scheduleAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Doctor ID:");
        String doctorId = scanner.next();
        System.out.println("Enter Appointment Date (dd-MM-yyyy):");
        String dateStr = scanner.next();
        System.out.println("Enter Appointment Time (HH:mm):");
        String timeStr = scanner.next();

        Date dateTime = DateTimeHelper.parseDateTime(dateStr + " " + timeStr);
        if (dateTime != null) {
            patientService.scheduleAppointment(patient, doctorId, dateTime);
        } else {
            System.out.println("Invalid date or time format.");
        }
        scanner.close();
    }

    private void rescheduleAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Appointment ID to Reschedule:");
        String appointmentId = scanner.next();
        System.out.println("Enter New Date (dd-MM-yyyy):");
        String newDateStr = scanner.next();
        System.out.println("Enter New Time (HH:mm):");
        String newTimeStr = scanner.next();

        Date newDateTime = DateTimeHelper.parseDateTime(newDateStr + " " + newTimeStr);
        if (newDateTime != null) {
            patientService.rescheduleAppointment(appointmentId, newDateTime);
        } else {
            System.out.println("Invalid date or time format.");
        }
        scanner.close();
    }

    private void cancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Appointment ID to Cancel:");
        String appointmentId = scanner.next();
        patientService.cancelAppointment(appointmentId);
        scanner.close();
    }

    public void menu_scheduleAppt(Patient patient){
        Scanner scanner = new Scanner(System.in);

        scanner.close();
    }
}
