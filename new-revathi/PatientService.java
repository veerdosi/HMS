import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patients;

    public PatientService(String patientFilePath) {
        this.patients = loadPatientsFromCsv(patientFilePath);
    }

    // Load patients from the specified CSV file path
    private List<Patient> loadPatientsFromCsv(String filePath) {
        List<Patient> patientList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header line (skip it)
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7) { // Ensure there are enough fields for a valid record
                    String userID = fields[0].trim();
                    String name = fields[1].trim();
                    String dob = fields[2].trim();
                    String gender = fields[3].trim();
                    String bloodType = fields[4].trim();
                    String contactEmail = fields[5].trim();
                    String contactNumber = fields[6].trim();
                    String password = fields[7].trim();

                    // Create and add Patient object
                    Patient patient = new Patient(userID, name, password, gender, contactEmail, contactNumber, dob, bloodType);
                    patientList.add(patient);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading patients from CSV: " + e.getMessage());
        }
        return patientList;
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    // Get a patient by ID
    public Patient getPatientById(String patientId) {
        return patients.stream().filter(p -> p.getUserID().equals(patientId)).findFirst().orElse(null);
    }
}
