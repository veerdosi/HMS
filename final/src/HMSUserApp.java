import java.util.Scanner;

public class HMSUserApp {
    private static HMSUserApp instance = null;
    private AuthenticationService authService;
    private AppointmentServiceFacade facade;
    private MedicineInventory medicineInventory;
    private boolean running;

    // Private constructor prevents instantiation from other classes
    private HMSUserApp() {
        authService = new AuthenticationService();
        running = true;

        // Define the file paths
        String patientFilePath = "Data/Patient_List(Sheet1).csv";
        String staffFilePath = "Data/Staff_List(Sheet1).csv";
        String medicineFilePath = "Data/Medicine_List(Sheet1).csv";

        // Initialize services
        facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);
        medicineInventory = MedicineInventory.getInstance(medicineFilePath);
    }

    /**
     * Returns the singleton instance of HMSUserApp
     * 
     * @return HMSUserApp instance
     */
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
            case 1 -> login();
            case 2 -> running = false;
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
        boolean loggedIn = true;

        while (loggedIn) {
            try {
                loggedIn = displayUserMenu(user);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    private boolean displayUserMenu(User user) {
        boolean continueSession = true;

        switch (user.getRole()) {
            case DOCTOR -> {
                DoctorMenu doctorMenu = new DoctorMenu((Doctor) user, facade, InputHandler.getScanner());
                continueSession = doctorMenu.displayMenu();
            }
            case PATIENT -> {
                PatientMenu patientMenu = new PatientMenu((Patient) user, InputHandler.getScanner());
                continueSession = patientMenu.displayMenu();
            }
            case ADMIN -> {
                AdminMenu adminMenu = new AdminMenu((Admin) user, InputHandler.getScanner());
                continueSession = adminMenu.display();
            }
            case PHARMACIST -> {
                PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) user, InputHandler.getScanner());
                continueSession = pharmacistMenu.displayMenu();
            }
            default -> {
                System.out.println("Unsupported user role.");
                continueSession = false;
            }
        }

        if (!continueSession) {
            System.out.println("Logged out successfully.");
            return false;
        }
        return true;
    }

    /**
     * Main method to start the application
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            HMSUserApp app = HMSUserApp.getInstance();
            app.start();
        } catch (Exception e) {
            System.out.println("Fatal error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the scanner when the application exits
            InputHandler.getScanner().close();
        }
    }
}