public class DoctorMenu {
    private Doctor doctor;
    private AppointmentServiceFacade facade;

    public DoctorMenu(Doctor doctor, AppointmentServiceFacade facade) {
        this.doctor = doctor;
        this.facade = facade;
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
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    // View Patient Medical Records
    private void viewPatientMedicalRecords() {
        System.out.println("\n--- View Patient Medical Records ---");
        System.out.println("Enter the Patient ID to view their medical records:");
        System.out.println("0. Back to Main Menu");

        String patientId = InputHandler.getStringInput("Enter Patient ID (or 0 to return): ");
if ("0".equals(patientId)) {
    return;
}

        if ("0".equals(patientId)) {
            return;
        }

        // Logic to fetch and display patient medical records
        // Use facade.getPatientById(patientId) to fetch details
    }

    // Update Patient Medical Records
    private void updatePatientMedicalRecords() {
        while (true) {
            System.out.println("\n--- Update Patient Medical Records ---");
            System.out.println("Enter the Patient ID to update their medical records:");
            System.out.println("Choose the information to update:");
            System.out.println("1. Add Diagnosis");
            System.out.println("2. Add Prescription");
            System.out.println("3. Update Treatment Plan");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 3);

            switch (choice) {
                case 1:
                    System.out.println("Enter Diagnosis Details:");
                    // Logic to add diagnosis
                    break;
                case 2:
                    System.out.println("Enter Prescription Details:");
                    // Logic to add prescription
                    break;
                case 3:
                    System.out.println("Enter Updated Treatment Plan:");
                    // Logic to update treatment plan
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View Personal Schedule
    private void viewPersonalSchedule() {
        System.out.println("\n--- View Personal Schedule ---");
        // Logic to display personal schedule using facade
        System.out.println("0. Back to Main Menu");
        InputHandler.getIntInput(0, 0);
    }

    // Set Availability
    private void setAvailability() {
        while (true) {
            System.out.println("\n--- Set Availability for Appointments ---");
            System.out.println("Choose an option:");
            System.out.println("1. Add Availability Slot");
            System.out.println("2. Remove Availability Slot");
            System.out.println("3. View Current Availability");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 3);

            switch (choice) {
                case 1:
                    System.out.println("Enter Date and Time for Availability:");
                    // Logic to add availability
                    break;
                case 2:
                    System.out.println("Enter Date and Time to Remove Availability:");
                    // Logic to remove availability
                    break;
                case 3:
                    System.out.println("Your Current Availability:");
                    // Logic to view availability
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handle Appointment Requests
    private void handleAppointmentRequests() {
        while (true) {
            System.out.println("\n--- Accept or Decline Appointment Requests ---");
            System.out.println("1. Accept Appointment");
            System.out.println("2. Decline Appointment");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 2);

            switch (choice) {
                case 1:
                    System.out.println("Enter Appointment ID to Accept:");
                    // Logic to accept appointment
                    break;
                case 2:
                    System.out.println("Enter Appointment ID to Decline:");
                    // Logic to decline appointment
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View Upcoming Appointments
    private void viewUpcomingAppointments() {
        System.out.println("\n--- View Upcoming Appointments ---");
        AppointmentOutcomeRecord.getInstance().displayConfirmedAppointmentsByDoctor(doctor.getUserID());
        System.out.println("0. Back to Main Menu");
        InputHandler.getIntInput(0, 0);
    }

    // Record Appointment Outcome
    private void recordAppointmentOutcome() {
        while (true) {
            System.out.println("\n--- Record Appointment Outcome ---");
            System.out.println("Enter the Appointment ID to record its outcome:");
            System.out.println("Choose an action:");
            System.out.println("1. Add Consultation Notes");
            System.out.println("2. Add Prescriptions");
            System.out.println("3. Mark Appointment as Completed");
            System.out.println("0. Back to Main Menu");

            int choice = InputHandler.getIntInput(0, 3);

            switch (choice) {
                case 1:
                    System.out.println("Enter Consultation Notes:");
                    // Logic to add consultation notes
                    break;
                case 2:
                    System.out.println("Enter Prescription Details:");
                    // Logic to add prescriptions
                    break;
                case 3:
                    System.out.println("Marking Appointment as Completed...");
                    // Logic to mark appointment as completed
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
