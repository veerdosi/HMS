import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorService {
    private List<Doctor> doctors;

    public DoctorService(String staffFilePath) {
        this.doctors = loadDoctorsFromCsv(staffFilePath);
    }

    /**
     * @param filePath
     * @return List<Doctor>
     */
    // Load doctors from the specified CSV file path
    private List<Doctor> loadDoctorsFromCsv(String filePath) {
        List<Doctor> doctorList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header line (skip it)
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7 && fields[2].trim().equalsIgnoreCase("Doctor")) { // Check role column
                    String userID = fields[0].trim();
                    String name = fields[1].trim();
                    String gender = fields[3].trim();
                    int age = Integer.parseInt(fields[4].trim()); // Not sure if an error might pop up here
                    String contactEmail = fields[5].trim();
                    String contactNumber = fields[6].trim();
                    String password = fields[7].trim();

                    // Create and add Doctor object
                    Doctor doctor = new Doctor(userID, name, password, gender, contactEmail, contactNumber, age);
                    doctorList.add(doctor);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading doctors from CSV: " + e.getMessage());
        }
        return doctorList;
    }

    // Get the list of available doctors
    public List<Doctor> getAvailableDoctors() {
        return doctors;
    }

    // Get a doctor by ID (if needed for further interaction)
    public Doctor getDoctorById(String doctorId) {
        return doctors.stream().filter(d -> d.getUserID().equals(doctorId)).findFirst().orElse(null);
    }
}
