import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    protected String userID;
    protected String password;
    protected String contactNumber;
    protected String email;
    protected UserRole role;
    protected String gender;

    private static final String PATIENT_FILE_PATH = "C:/Users/YourUsername/Desktop/HMS/Data/Patient_List(Sheet1).csv";
    private static final String STAFF_FILE_PATH = "C:/Users/YourUsername/Desktop/HMS/Data/Staff_List(Sheet1).csv";


    // Constructor
    public User(String userID, String password, String contactNumber, String email, UserRole role, String gender) {
        this.userID = userID;
        this.password = password;
        this.contactNumber = contactNumber;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        updatePasswordInExcel();
    }

    private void updatePasswordInExcel() {
        String filePath = determineFilePath();
        if (filePath == null) {
            System.err.println("Invalid user ID for file determination.");
            return;
        }

        List<String> lines = new ArrayList<>();
        boolean passwordUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header
            lines.add(line);  // Add header to output list

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                // Check if the current line matches this user's ID
                if (fields[0].trim().equals(this.userID)) {
                    fields[fields.length - 1] = this.password;  // Update the password
                    passwordUpdated = true;
                }

                // Join fields back into a single line and add to lines
                lines.add(String.join(",", fields));
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        // Write all lines back to the file if password was updated
        if (passwordUpdated) {
            try {
                Files.write(Paths.get(filePath), lines);
                System.out.println("Password updated successfully in the file.");
            } catch (IOException e) {
                System.err.println("Error writing to the CSV file: " + e.getMessage());
            }
        } else {
            System.out.println("User ID not found in the file.");
        }
    }

    private String determineFilePath() {
        if (userID.startsWith("P")) {
            return PATIENT_FILE_PATH;
        } else if (userID.startsWith("D") || userID.startsWith("A")) {
            return STAFF_FILE_PATH;
        } else {
            return null;
        }
    }
}
