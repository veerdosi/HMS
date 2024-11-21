import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `DoctorService` class handles operations related to managing doctors, including
 * loading doctor data from a CSV file and retrieving doctor details.
 */
public class DoctorService {
    private List<Doctor> doctors;

    /**
     * Constructs a `DoctorService` and loads doctors from the specified CSV file.
     *
     * @param staffFilePath The file path to the staff CSV data.
     */
    public DoctorService(String staffFilePath) {
        this.doctors = loadDoctorsFromCsv(staffFilePath);
    }

    /**
     * Loads doctor data from a specified CSV file.
     *
     * @param filePath The file path to the CSV file.
     * @return A list of `Doctor` objects loaded from the file.
     */
    private List<Doctor> loadDoctorsFromCsv(String filePath) {
        List<Doctor> doctorList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7 && fields[2].trim().equalsIgnoreCase("Doctor")) {
                    String userID = fields[0].trim();
                    String name = fields[1].trim();
                    String gender = fields[3].trim();
                    int age = Integer.parseInt(fields[4].trim());
                    String contactEmail = fields[5].trim();
                    String contactNumber = fields[6].trim();
                    String password = fields[7].trim();

                    Doctor doctor = new Doctor(userID, name, password, gender, contactEmail, contactNumber, age);
                    doctorList.add(doctor);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading doctors from CSV: " + e.getMessage());
        }
        return doctorList;
    }

    /**
     * Retrieves the list of all available doctors.
     *
     * @return A list of `Doctor` objects.
     */
    public List<Doctor> getAvailableDoctors() {
        return doctors;
    }

    /**
     * Retrieves a doctor by their unique ID.
     *
     * @param doctorId The ID of the doctor.
     * @return The `Doctor` object if found, or `null` if not.
     */
    public Doctor getDoctorById(String doctorId) {
        return doctors.stream().filter(d -> d.getUserID().equals(doctorId)).findFirst().orElse(null);
    }
}
