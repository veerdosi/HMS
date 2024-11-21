import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The `User` class represents a user in the system with attributes such as user ID,
 * name, role, gender, and contact information. It also manages password-related
 * operations, including first login password reset and updating the password in the
 * corresponding CSV file.
 */
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
    private static final String DEFAULT_PASSWORD = "password"; // Default password for new users

    /**
     * Constructs a `User` object with the specified attributes. A new user is
     * initialized with a default password.
     *
     * @param userID        The unique identifier for the user.
     * @param name          The name of the user.
     * @param password      The user's password (initially set to a default value).
     * @param role          The role of the user (e.g., Patient, Doctor, Admin).
     * @param gender        The gender of the user.
     * @param contactEmail  The user's contact email.
     * @param contactNumber The user's contact number.
     */
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
     * Gets the role of the user.
     *
     * @return The user's role.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return The user ID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the gender of the user.
     *
     * @return The user's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the user's contact email.
     *
     * @return The contact email.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Gets the user's contact number.
     *
     * @return The contact number.
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Sets the user's contact email.
     *
     * @param contactEmail The new contact email to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Sets the user's contact number.
     *
     * @param contactNumber The new contact number to set.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The new role to set.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Authenticates the user using the provided password. If the default password
     * is used, the user is prompted to reset their password.
     *
     * @param password The password to authenticate.
     * @return `true` if authentication is successful, `false` otherwise.
     */
    public boolean authenticatePassword(String password) {
        if (this.password.equals(password)){
            if(password.equals(DEFAULT_PASSWORD)){
            System.out.println("");
            System.out.println("First Login: Please reset your password!\n");
            String newPass = InputHandler.getStringInput("New Password: ");
            changePassword(password, newPass);
            }
        }
        return this.password.equals(password);
    }

    /**
     * Changes the user's password and updates the corresponding record in the CSV
     * file.
     *
     * @param oldPass     The current password (not used in this implementation).
     * @param newPassword The new password to set.
     */
    public void changePassword(String oldPass, String newPassword) {
        this.password = newPassword;
        updatePasswordInExcel();
    }

    /**
     * Updates the user's password in the corresponding CSV file (patients or staff).
     */
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

        // Write all lines back to the file if the password was updated
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

    /**
     * Determines the file path based on the user ID.
     *
     * @return The file path to the CSV file (patients or staff), or `null` if the
     *         user ID is invalid.
     */
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
