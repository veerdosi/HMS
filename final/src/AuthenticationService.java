import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private Map<String, User> users = new HashMap<>();

    // Paths to the CSV files
    private static final String PATIENT_FILE_PATH = "C:/Users/LENOVO/Desktop/HMS/Data/Patient_List(Sheet1).csv";
    private static final String STAFF_FILE_PATH = "C:/Users/LENOVO/Desktop/HMS/Data/Staff_List(Sheet1).csv";

    public AuthenticationService() {
        loadPatients();
        loadStaff();
    }

    private void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) continue;

                String patientID = fields[0].trim();
                String name = fields[1].trim();
                String dateOfBirth = fields[2].trim();
                String gender = fields[3].trim();
                String bloodType = fields[4].trim();
                String contactEmail = fields[5].trim();
                String contactNumber = fields[6].trim();
                String password = fields[7].trim();

                Patient patient = new Patient(patientID, name, password, gender, contactEmail, contactNumber, dateOfBirth, bloodType);
                users.put(patientID, patient);
            }
        } catch (IOException e) {
            System.err.println("Error loading patient data: " + e.getMessage());
        }
    }

    private void loadStaff() {
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) continue;

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
                        user = new Admin(staffID, name,  password, gender, contactEmail, contactNumber, age);
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

    public User authenticateUser(String userID, String password) {
        User user = users.get(userID);
        if (user != null && user.authenticatePassword(password)) {
            return user;
        }
        return null;  // Authentication failed
    }
}