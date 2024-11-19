import java.util.Scanner;
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
        scanner.close();
    }

    private void viewMedicalRecord() {
        // Implement view medical record
        System.out.println("Fetching Medical Record...");
        patient.viewMedicalRecord(patient);
    }

    private void updatePersonalInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new contact number:");
        String newContact = scanner.next();
        System.out.println("Enter new email:");
        String newEmail = scanner.next();
        patient.updateContactInfo(newEmail, newContact);
        scanner.close();
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
