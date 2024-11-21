import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `PatientService` class provides methods to manage and retrieve patient information.
 * It allows loading patient data from a CSV file and accessing patient details.
 */
public class PatientService {
    private List<Patient> patients; // List of all patients

    /**
     * Constructs a `PatientService` object and initializes the patient list by loading data
     * from the specified CSV file.
     *
     * @param patientFilePath the file path to the CSV file containing patient data.
     */
    public PatientService(String patientFilePath) {
        this.patients = loadPatientsFromCsv(patientFilePath);
    }

    /**
     * Loads patients from the specified CSV file.
     *
     * @param filePath the file path to the CSV file containing patient data.
     * @return a list of `Patient` objects loaded from the file.
     */
    private List<Patient> loadPatientsFromCsv(String filePath) {
        List<Patient> patientList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read and skip the header line
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    String userID = fields[0].trim();
                    String name = fields[1].trim();
                    String dob = fields[2].trim();
                    String gender = fields[3].trim();
                    String bloodType = fields[4].trim();
                    String contactEmail = fields[5].trim();
                    String contactNumber = fields[6].trim();
                    String password = fields[7].trim();

                    Patient patient = new Patient(userID, name, password, gender, contactEmail, contactNumber, dob, bloodType);
                    patientList.add(patient);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading patients from CSV: " + e.getMessage());
        }
        return patientList;
    }

    /**
     * Retrieves the list of all patients.
     *
     * @return a list of `Patient` objects.
     */
    public List<Patient> getAllPatients() {
        return patients;
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param patientId the ID of the patient to retrieve.
     * @return the `Patient` object if found, or `null` if no match is found.
     */
    public Patient getPatientById(String patientId) {
        return patients.stream().filter(p -> p.getUserID().equals(patientId)).findFirst().orElse(null);
    }
}
