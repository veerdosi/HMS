import java.util.Scanner;

public class HMSUserApp {
    private static HMSUserApp instance = null;
    private AuthenticationService authService;
    private AppointmentServiceFacade facade;
    private Scanner scanner;
    private boolean running;

    // Private constructor prevents instantiation from other classes
    private HMSUserApp() {
        authService = new AuthenticationService();
        scanner = new Scanner(System.in);
        running = true;  // Set running to true initially

        // Define the file paths directly within the constructor
        String patientFilePath = "C:/Users/LENOVO/Desktop/HMS/Data/Patient_List(Sheet1).csv";
        String staffFilePath = "C:/Users/LENOVO/Desktop/HMS/Data/Staff_List(Sheet1).csv";

        // Initialize the AppointmentServiceFacade with the file paths
        facade = AppointmentServiceFacade.getInstance(patientFilePath, staffFilePath);  // Correctly initialize facade
    }

    // Public method to provide access to the single instance
    public static HMSUserApp getInstance() {
        if (instance == null) {
            instance = new HMSUserApp();
        }
        return instance;
    }

    public void start() {
        while (running) {  // Check the running flag to continue or stop
            login();
        }
        System.out.println("Exiting the program. Thank you for accessing Hospital Management Software!");
    }

    public void login() {
        System.out.println("(1) Log in");
        System.out.println("(2) Exit the program");
        System.out.println("Enter your choice:");
        String choice = scanner.nextLine();

        if (choice.equals("2")) {
            running = false;  // Set running to false to exit the main loop
            return;
        } else if (!choice.equals("1")) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = authService.authenticateUser(userId, password);

        if (user != null) {
            displayUserMenu(user);
        } else {
            System.out.println("Invalid credentials. Please try again.");
            login(); // Prompt user again
        }
    }

    private void displayUserMenu(User user) {
        boolean loggedIn = true;

        while (loggedIn){
            switch (user.getRole()) {
                case DOCTOR:
                    DoctorMenu doctorMenu = new DoctorMenu((Doctor) user);
                    loggedIn = doctorMenu.display();  // display() returns false if logout is chosen
                    break;
                case PATIENT:
                    PatientMenu patientMenu = new PatientMenu((Patient) user);
                    loggedIn = patientMenu.display();
                    break;
                case ADMIN:
                    AdminMenu adminMenu = new AdminMenu((Admin) user);
                    loggedIn = adminMenu.display();
                    break;
                case PHARMACIST:
                    PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) user);
                    loggedIn = pharmacistMenu.display();
                    break;
                default:
                    System.out.println("Unknown role.");
                    loggedIn = false;
            }
        }
        System.out.println("Logged out successfully. Returning to login screen...");
    }

    public static void main(String[] args) {
        HMSUserApp app = HMSUserApp.getInstance();
        System.out.println("Hospital Management Software - Version 1.0");
        app.start(); // Start the main application loop
    }
}