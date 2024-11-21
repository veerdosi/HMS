/**
 * The HMSUserApp class is a Java program that represents a Hospital Management Software application
 * with user authentication and role-based menu functionalities.
 */
public class HMSUserApp {
    // These lines are declaring instance variables in the `HMSUserApp` class:
    private static HMSUserApp instance = null;
    private AuthenticationService authService;
    private AppointmentServiceFacade facade;
    private MedicineInventory medicineInventory;
    private boolean running;

    // The `private HMSUserApp()` constructor in the `HMSUserApp` class is a private constructor method
    // that initializes the instance of the `HMSUserApp` class.
    private HMSUserApp() {
        authService = new AuthenticationService();
        running = true;

        String patientFilePath = "Data/Patient_List(Sheet1).csv";
        String staffFilePath = "Data/Staff_List(Sheet1).csv";
        String medicineFilePath = "Data/Medicine_List(Sheet1).csv";

        facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);
        medicineInventory = MedicineInventory.getInstance(medicineFilePath);
    }

   /**
    * The function getInstance() returns the singleton instance of the HMSUserApp class, creating it if
    * it doesn't already exist.
    * 
    * @return An instance of the HMSUserApp class is being returned.
    */
    public static HMSUserApp getInstance() {
        if (instance == null) {
            instance = new HMSUserApp();
        }
        return instance;
    }

    /**
     * The `start` function in Java initiates the Hospital Management Software and displays a login
     * menu until the program is stopped.
     */
    public void start() {
        System.out.println("Welcome to Hospital Management Software - Version 1.0");
        System.out.println("------------------------------------------------");

        while (running) {
            displayLoginMenu();
        }

        System.out.println("Thank you for using Hospital Management Software!");
    }

    /**
     * The `displayLoginMenu` method presents a menu for the user to either log in or exit the program
     * based on their choice.
     */
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

    /**
     * The `login` function attempts to authenticate a user with provided credentials and handles the
     * user session accordingly.
     */
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

    /**
     * The `handleUserSession` method manages user sessions based on their roles by displaying specific
     * menus for doctors, patients, admins, and pharmacists.
     * 
     * @param user The `user` parameter in the `handleUserSession` method represents the current user
     * who is interacting with the system. The method determines the user's role and then displays the
     * corresponding menu based on that role. The user's role could be a Doctor, Patient, Admin,
     * Pharmacist, or any
     */
    private void handleUserSession(User user) {
        boolean keepRunning = true;

        while (keepRunning) {
            switch (user.getRole()) {
                case DOCTOR:
                    DoctorMenu doctorMenu = new DoctorMenu((Doctor) user, facade);
                    keepRunning = doctorMenu.displayMenu();
                    break;

                case PATIENT:
                    PatientMenu patientMenu = new PatientMenu((Patient) user);
                    keepRunning = patientMenu.displayMenu();
                    break;

                case ADMIN:
                    AdminMenu adminMenu = new AdminMenu((Admin) user);
                    keepRunning = adminMenu.display();
                    break;

                case PHARMACIST:
                    PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) user);
                    keepRunning = pharmacistMenu.displayMenu();
                    break;

                default:
                    System.out.println("Unsupported user role.");
                    keepRunning = false;
                    break;
            }
        }
    }

    // The `public static void main(String[] args)` method in the `HMSUserApp` class is the entry
    // point of the Java program. Here's what it does:
    public static void main(String[] args) {
        try {
            HMSUserApp app = HMSUserApp.getInstance();
            app.start();
        } catch (Exception e) {
            System.out.println("Fatal error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            InputHandler.closeScanner();
        }
    }
}
