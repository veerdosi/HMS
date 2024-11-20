import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorAvailabilityRepository {
    private static DoctorAvailabilityRepository instance; // Singleton instance
    private Map<String, DoctorAvailability> doctorAvailabilityMap; // Centralized repository

    // Private constructor to prevent direct instantiation
    private DoctorAvailabilityRepository() {
        this.doctorAvailabilityMap = new HashMap<>();
    }

    // Public method to get the single instance
    public static synchronized DoctorAvailabilityRepository getInstance() {
        if (instance == null) {
            instance = new DoctorAvailabilityRepository();
        }
        return instance;
    }

    // Add or update a doctor's availability
    public void setDoctorAvailability(String doctorId, List<TimeSlot> availability) {
        if (doctorId == null || availability == null) {
            System.out.println("Invalid doctor ID or availability.");
            return;
        }
        doctorAvailabilityMap.put(doctorId, new DoctorAvailability(doctorId, availability));
        System.out.println("Availability updated for Doctor ID: " + doctorId);
    }

    // Retrieve availability for a specific doctor
    public DoctorAvailability getDoctorAvailability(String doctorId) {
        return doctorAvailabilityMap.getOrDefault(doctorId, null);
    }

    // Retrieve availability for all doctors
    public List<DoctorAvailability> getAllDoctorAvailabilities() {
        return doctorAvailabilityMap.values().stream().toList();
    }

    // Print all doctor availabilities
    public void printAllDoctorAvailabilities() {
        System.out.println("\n--- All Doctor Availabilities ---");
        if (doctorAvailabilityMap.isEmpty()) {
            System.out.println("No availabilities found.");
        } else {
            for (DoctorAvailability availability : doctorAvailabilityMap.values()) {
                System.out.println(availability);
            }
        }
    }
}