import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffList {
    private static StaffList instance;
    private List<User> staff;
    private String filePath;

    // Private constructor to enforce Singleton
    private StaffList(String filePath) {
        this.filePath = filePath;
        this.staff = new ArrayList<>();
        loadStaffFromFile();
    }

    /**
     * @param filePath
     * @return StaffList
     */
    // Singleton access method
    public static StaffList getInstance(String filePath) {
        if (instance == null) {
            instance = new StaffList(filePath);
        }
        return instance;
    }

    // Update Staff by Field
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

        writeStaffToFile(); // Persist changes to file
        System.out.println("Staff member updated successfully: " + staffMember);
        return true;
    }

    // Load staff data from file
    private void loadStaffFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read and skip header line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 8) { // Ensure there are enough fields for a valid record
                    String staffID = fields[0].trim();
                    String name = fields[1].trim();
                    String role = fields[2].trim();
                    String gender = fields[3].trim();
                    int age = Integer.parseInt(fields[4].trim());
                    String contactEmail = fields[5].trim();
                    String contactNumber = fields[6].trim();
                    // Password is read but not stored (admins cannot modify it)
                    String password = fields[7].trim();

                    // Create User object based on the role
                    User staffMember;
                    switch (role.toLowerCase()) {
                        case "doctor":
                            staffMember = new Doctor(staffID, name, password, gender, contactEmail, contactNumber, age);
                            break;
                        case "pharmacist":
                            staffMember = new Pharmacist(staffID, name, password, gender, contactEmail, contactNumber,
                                    age);
                            break;
                        case "administrator":
                            staffMember = new Admin(staffID, name, password, gender, contactEmail, contactNumber, age);
                            break;
                        default:
                            System.err.println("Unknown role for staff ID: " + staffID);
                            continue;
                    }
                    // Add staff member to the in-memory list
                    staff.add(staffMember);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading staff data: " + e.getMessage());
        }
    }

    // Write staff data to file
    public void writeStaffToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write header line
            bw.write("Staff ID,Name,Role,Gender,Age,Contact Email,Contact Number,Password");
            bw.newLine();

            // Write each staff member in the correct format
            for (User staffMember : staff) {
                String line = String.format("%s,%s,%s,%s,%d,%s,%s,%s",
                        staffMember.getUserID(),
                        staffMember.getName(),
                        staffMember.getRole(),
                        staffMember.getGender(),
                        getAgeIfApplicable(staffMember), // Retrieves age for applicable roles
                        staffMember.getContactEmail(),
                        staffMember.getContactNumber(),
                        "password"); // Default password, as passwords cannot be changed by admins
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing staff data: " + e.getMessage());
        }
    }

    // Helper to get age for roles that have it
    private int getAgeIfApplicable(User staffMember) {
        if (staffMember instanceof Doctor) {
            return ((Doctor) staffMember).getAge();
        } else if (staffMember instanceof Pharmacist) {
            return ((Pharmacist) staffMember).getAge();
        } else if (staffMember instanceof Admin) {
            return ((Admin) staffMember).getAge();
        }
        return 0; // Default for roles without age
    }

    // Retrieve a staff member by ID
    public User getStaffById(String staffID) {
        return staff.stream()
                .filter(staffMember -> staffMember.getUserID().equals(staffID))
                .findFirst()
                .orElse(null);
    }

    // Filter Staff based on criteria
    public List<User> filterStaff(String filterType, String filterValue) {
        return staff.stream()
                .filter(staffMember -> {
                    switch (filterType.toLowerCase()) {
                        case "role":
                            return staffMember.getRole().toString().equalsIgnoreCase(filterValue);
                        case "gender":
                            return staffMember.getGender().equalsIgnoreCase(filterValue);
                        case "age":
                            if (staffMember instanceof Doctor) {
                                return Integer.toString(((Doctor) staffMember).getAge()).equals(filterValue);
                            } else if (staffMember instanceof Pharmacist) {
                                return Integer.toString(((Pharmacist) staffMember).getAge()).equals(filterValue);
                            } else if (staffMember instanceof Admin) {
                                return Integer.toString(((Admin) staffMember).getAge()).equals(filterValue);
                            } else {
                                return false;
                            }
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // Retrieve staff list
    public List<User> getStaff() {
        return new ArrayList<>(staff); // Return a copy to prevent external modification
    }

    // Add staff (internal use only)
    public void addStaff(User staffMember) {
        staff.add(staffMember);
        writeStaffToFile();
    }

    // Remove staff by ID (internal use only)
    public boolean removeStaff(String staffID) {
        boolean removed = staff.removeIf(staffMember -> staffMember.getUserID().equals(staffID));
        if (removed) {
            writeStaffToFile();
        }
        return removed;
    }
}
