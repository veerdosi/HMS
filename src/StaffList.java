import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `StaffList` class manages a centralized list of staff members for a hospital management system.
 * It provides functionality to add, remove, update, and retrieve staff details. Data is loaded
 * from and written to a file to ensure persistence. This class follows the Singleton design pattern.
 */
public class StaffList {
    private static StaffList instance;
    private List<User> staff;
    private String filePath;

    /**
     * Private constructor to enforce the Singleton design pattern.
     *
     * @param filePath the path to the file where staff data is stored.
     */
    private StaffList(String filePath) {
        this.filePath = filePath;
        this.staff = new ArrayList<>();
        loadStaffFromFile();
    }

    /**
     * Provides the Singleton instance of the `StaffList`.
     * Initializes the instance with the specified file path if it does not already exist.
     *
     * @param filePath the path to the file where staff data is stored.
     * @return the Singleton instance of the `StaffList`.
     */
    public static StaffList getInstance(String filePath) {
        if (instance == null) {
            instance = new StaffList(filePath);
        }
        return instance;
    }

    /**
     * Updates the specified field of a staff member identified by their ID.
     *
     * @param staffID       the ID of the staff member to update.
     * @param fieldToUpdate the field to update (e.g., "name", "email", "age").
     * @param newValue      the new value to set for the specified field.
     * @return `true` if the update was successful, otherwise `false`.
     */
    public boolean updateStaff(String staffID, String fieldToUpdate, String newValue) {
        User staffMember = getStaffById(staffID);
        if (staffMember == null) {
            System.out.println("Staff member with ID " + staffID + " not found.");
            return false;
        }

        switch (fieldToUpdate.toLowerCase()) {
            case "name":
                staffMember.setName(newValue);
                break;
            case "email":
                staffMember.setContactEmail(newValue);
                break;
            case "contactnumber":
                staffMember.setContactNumber(newValue);
                break;
            case "age":
                if (staffMember instanceof Doctor) {
                    ((Doctor) staffMember).setAge(Integer.parseInt(newValue));
                } else if (staffMember instanceof Pharmacist) {
                    ((Pharmacist) staffMember).setAge(Integer.parseInt(newValue));
                } else if (staffMember instanceof Admin) {
                    ((Admin) staffMember).setAge(Integer.parseInt(newValue));
                } else {
                    System.out.println("Age cannot be updated for this role.");
                    return false;
                }
                break;
            default:
                System.out.println("Invalid field. Update failed.");
                return false;
        }

        writeStaffToFile();
        System.out.println("Staff member updated successfully: " + staffMember);
        return true;
    }

    /**
     * Loads staff data from the file into memory.
     * Each staff record in the file is parsed and added to the in-memory list.
     */
    private void loadStaffFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read and skip header line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 8) { // Ensure valid record
                    String staffID = fields[0].trim();
                    String name = fields[1].trim();
                    String role = fields[2].trim();
                    String gender = fields[3].trim();
                    int age = Integer.parseInt(fields[4].trim());
                    String contactEmail = fields[5].trim();
                    String contactNumber = fields[6].trim();
                    String password = fields[7].trim();

                    User staffMember;
                    switch (role.toLowerCase()) {
                        case "doctor":
                            staffMember = new Doctor(staffID, name, password, gender, contactEmail, contactNumber, age);
                            break;
                        case "pharmacist":
                            staffMember = new Pharmacist(staffID, name, password, gender, contactEmail, contactNumber, age);
                            break;
                        case "administrator":
                            staffMember = new Admin(staffID, name, password, gender, contactEmail, contactNumber, age);
                            break;
                        default:
                            System.err.println("Unknown role for staff ID: " + staffID);
                            continue;
                    }
                    staff.add(staffMember);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading staff data: " + e.getMessage());
        }
    }

    /**
     * Writes the current staff data to the file for persistence.
     * Each staff member's details are written in CSV format.
     */
    public void writeStaffToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("Staff ID,Name,Role,Gender,Age,Contact Email,Contact Number,Password");
            bw.newLine();

            for (User staffMember : staff) {
                String line = String.format("%s,%s,%s,%s,%d,%s,%s,%s",
                        staffMember.getUserID(),
                        staffMember.getName(),
                        staffMember.getRole(),
                        staffMember.getGender(),
                        getAgeIfApplicable(staffMember),
                        staffMember.getContactEmail(),
                        staffMember.getContactNumber(),
                        "password"); // Default password placeholder
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing staff data: " + e.getMessage());
        }
    }

    /**
     * Retrieves the age of a staff member if applicable.
     *
     * @param staffMember the staff member whose age is to be retrieved.
     * @return the age if applicable, otherwise 0.
     */
    private int getAgeIfApplicable(User staffMember) {
        if (staffMember instanceof Doctor) {
            return ((Doctor) staffMember).getAge();
        } else if (staffMember instanceof Pharmacist) {
            return ((Pharmacist) staffMember).getAge();
        } else if (staffMember instanceof Admin) {
            return ((Admin) staffMember).getAge();
        }
        return 0;
    }

    /**
     * Retrieves a staff member by their ID.
     *
     * @param staffID the ID of the staff member to retrieve.
     * @return the `User` object representing the staff member, or `null` if not found.
     */
    public User getStaffById(String staffID) {
        return staff.stream()
                .filter(staffMember -> staffMember.getUserID().equals(staffID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Filters staff based on the specified criteria.
     *
     * @param filterType  the type of filter to apply (e.g., "role", "gender", "age").
     * @param filterValue the value to filter by.
     * @return a list of staff members matching the filter criteria.
     */
    public List<User> filterStaff(String filterType, String filterValue) {
        return staff.stream()
                .filter(staffMember -> {
                    switch (filterType.toLowerCase()) {
                        case "role":
                            return staffMember.getRole().toString().equalsIgnoreCase(filterValue);
                        case "gender":
                            return staffMember.getGender().equalsIgnoreCase(filterValue);
                        case "age":
                            return Integer.toString(getAgeIfApplicable(staffMember)).equals(filterValue);
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all staff members.
     *
     * @return a list of `User` objects representing all staff members.
     */
    public List<User> getStaff() {
        return new ArrayList<>(staff);
    }

    /**
     * Adds a new staff member to the system.
     *
     * @param staffMember the staff member to add.
     */
    public void addStaff(User staffMember) {
        staff.add(staffMember);
        writeStaffToFile();
    }

    /**
     * Removes a staff member by their ID.
     *
     * @param staffID the ID of the staff member to remove.
     * @return `true` if the staff member was successfully removed, otherwise `false`.
     */
    public boolean removeStaff(String staffID) {
        boolean removed = staff.removeIf(staffMember -> staffMember.getUserID().equals(staffID));
        if (removed) {
            writeStaffToFile();
        }
        return removed;
    }
}
