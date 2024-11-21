import java.util.Scanner;

public class HMSUserApp {
    private static HMSUserApp instance = null;
    private AuthenticationService authService;
    private AppointmentServiceFacade facade;
    private MedicineInventory medicineInventory;
    private Scanner scanner;
    private boolean running;

    private HMSUserApp() {
        authService = new AuthenticationService();
        running = true;

        String patientFilePath = "Data/Patient_List(Sheet1).csv";
        String staffFilePath = "Data/Staff_List(Sheet1).csv";
        String medicineFilePath = "Data/Medicine_List(Sheet1).csv";

        facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);
        medicineInventory = MedicineInventory.getInstance(medicineFilePath);
    }

    public static HMSUserApp getInstance() {
        if (instance == null) {
            instance = new HMSUserApp();
        }
        return instance;
    }

    public void start() {
        System.out.println("Welcome to Hospital Management Software - Version 1.0");
        System.out.println("------------------------------------------------");

        while (running) {
            displayLoginMenu();
        }

        System.out.println("Thank you for using Hospital Management Software!");
    }

    private void displayLoginMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Log in");
        System.out.println("2. Exit the program");

        int choice = InputHandler.getIntInput(1, 2);

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                running = false;
                break;
        }
    }

    private void login() {
        try {
            String userId = InputHandler.getStringInput("Enter User ID: ");
            String password = InputHandler.getStringInput("Enter Password: ");

            User user = authService.authenticateUser(userId, password);

            if (user != null) {
                System.out.println("\nLogin successful! Welcome, " + user.getName());
                handleUserSession(user);
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
    }

    private void handleUserSession(User user) {
        boolean keepRunning = true;

        while (keepRunning) {
            switch (user.getRole()) {
                case DOCTOR:
                    DoctorMenu doctorMenu = new DoctorMenu((Doctor) user, facade, InputHandler.getScanner());
                    keepRunning = doctorMenu.displayMenu();
                    break;

                case PATIENT:
                    PatientMenu patientMenu = new PatientMenu((Patient) user);
                    keepRunning = patientMenu.displayMenu();
                    break;

                case ADMIN:
                    AdminMenu adminMenu = new AdminMenu((Admin) user, InputHandler.getScanner());
                    keepRunning = adminMenu.display();
                    break;

                case PHARMACIST:
                    PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) user, InputHandler.getScanner());
                    keepRunning = pharmacistMenu.displayMenu();
                    break;

                default:
                    System.out.println("Unsupported user role.");
                    keepRunning = false;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            HMSUserApp app = HMSUserApp.getInstance();
            app.start();
        } catch (Exception e) {
            System.out.println("Fatal error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            InputHandler.getScanner().close();
        }
    }
}