package HMSpkg;
import java.util.Scanner;

public class HMSUserApp {
    private static HMSUserApp instance = null;
    private AuthenticationService authService;
    private Scanner scanner;

    // Private constructor prevents instantiation from other classes
    private HMSUserApp() {
        authService = new AuthenticationService();
        scanner = new Scanner(System.in);
    }

    // Public method to provide access to the single instance
    public static HMSUserApp getInstance() {
        if (instance == null) {
            instance = new HMSUserApp();
        }
        return instance;
    }

    public void login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = authService.authenticateUser(userId, password);

        if (user != null) {
            displayUserMenu(user);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void displayUserMenu(User user) {
        switch (user.getRole()) {
            case DOCTOR:
                new DoctorMenu().display();
                break;
            case PATIENT:
                new PatientMenu().display();
                break;
            case ADMIN:
                new AdminMenu().display();
                break;
            case PHARMACIST:
                new PharmacistMenu().display();
                break;
        }
    }

    public static void main(String[] args) {
        HMSUserApp app = HMSUserApp.getInstance();
        System.out.println("Hospital Management Software - Version 1.0");
        app.login();
    }
}