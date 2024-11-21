import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User implements IPasswordUpdate {
    protected String userID;
    protected String name;
    protected String password;
    protected UserRole role;
    protected String gender;
    protected String contactEmail;
    protected String contactNumber;
    private static final String PATIENT_FILE_PATH = "Data/Patient_List(Sheet1).csv";
    private static final String STAFF_FILE_PATH = "Data/Staff_List(Sheet1).csv";
    private static final String DEFAULT_PASSWORD = "password"; // every new user is assigned this bef 1st login

    // Constructor
    public User(String userID, String name, String password, UserRole role, String gender, String contactEmail,
            String contactNumber) {
        this.userID = userID;
        this.name = name;
        this.password = DEFAULT_PASSWORD;
        this.role = role;
        this.gender = gender;
        this.contactEmail = contactEmail;
        this.contactNumber = contactNumber;
    }

    /**
     * @return UserRole
     */
    public UserRole getRole() {
        return role;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean authenticatePassword(String password) {
        if (password.equals(DEFAULT_PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please reset your password (First Login).\n New Password: ");
            String newPass = scanner.nextLine();
            changePassword(password, newPass);
            scanner.close();
        }
        return this.password.equals(password);
    }

    public void changePassword(String oldPass, String newPassword) {
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
            lines.add(line); // Add header to output list

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                // Check if the current line matches this user's ID
                if (fields[0].trim().equals(this.userID)) {
                    fields[fields.length - 1] = this.password; // Update the password
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
