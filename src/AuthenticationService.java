import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The `AuthenticationService` class is responsible for loading user data from CSV files
 * and authenticating users. It supports both patients and staff and uses a `Map` to store
 * user data for quick lookup.
 */
public class AuthenticationService {
    private Map<String, User> users = new HashMap<>();

    // Paths to the CSV files
    private static final String PATIENT_FILE_PATH = "Data/Patient_List(Sheet1).csv";
    private static final String STAFF_FILE_PATH = "Data/Staff_List(Sheet1).csv";

    /**
     * Constructs an `AuthenticationService` object and loads user data from the specified
     * CSV files for patients and staff.
     */
    public AuthenticationService() {
        loadPatients();
        loadStaff();
    }

    /**
     * Loads patient data from the patient CSV file and populates the `users` map with
     * `Patient` objects.
     */
    private void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) {
                    continue; // Skip rows with insufficient data
                }

                String patientID = fields[0].trim();
                String name = fields[1].trim();
                String dateOfBirth = fields[2].trim();
                String gender = fields[3].trim();
                String bloodType = fields[4].trim();
                String contactEmail = fields[5].trim();
                String contactNumber = fields[6].trim();
                String password = fields[7].trim();

                Patient patient = new Patient(patientID, name, password, gender, contactEmail, contactNumber,
                        dateOfBirth, bloodType);
                users.put(patientID, patient);
            }
        } catch (IOException e) {
            System.err.println("Error loading patient data: " + e.getMessage());
        }
    }

    /**
     * Loads staff data from the staff CSV file and populates the `users` map with appropriate
     * `User` objects based on their roles (e.g., Doctor, Pharmacist, Administrator).
     */
    private void loadStaff() {
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) {
                    continue; // Skip rows with insufficient data
                }

                String staffID = fields[0].trim();
                String name = fields[1].trim();
                String role = fields[2].trim();
                String gender = fields[3].trim();
                int age = Integer.parseInt(fields[4].trim());
                String contactEmail = fields[5].trim();
                String contactNumber = fields[6].trim();
                String password = fields[7].trim();

                User user = null;
                switch (role.toLowerCase()) {
                    case "doctor":
                        user = new Doctor(staffID, name, password, gender, contactEmail, contactNumber, age);
                        break;
                    case "pharmacist":
                        user = new Pharmacist(staffID, name, password, gender, contactEmail, contactNumber, age);
                        break;
                    case "administrator":
                        user = new Admin(staffID, name, password, gender, contactEmail, contactNumber, age);
                        break;
                    default:
                        System.out.println("Unrecognized role for staff ID " + staffID);
                        continue;
                }

                users.put(staffID, user);
            }
        } catch (IOException e) {
            System.err.println("Error loading staff data: " + e.getMessage());
        }
    }

    /**
     * Authenticates a user by their user ID and password.
     *
     * @param userID The unique identifier of the user.
     * @param pass   The password provided by the user.
     * @return The `User` object if authentication is successful, or `null` if authentication fails.
     */
    public User authenticateUser(String userID, String pass) {
        User user = users.get(userID);
        if (user != null && user.authenticatePassword(pass)) {
            return user;
        }
        return null; // Authentication failed
    }
}
