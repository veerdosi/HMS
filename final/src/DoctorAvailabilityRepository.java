import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `DoctorAvailabilityRepository` class serves as a centralized repository for storing and managing
 * doctor availabilities. It follows the Singleton design pattern to ensure a single instance of
 * the repository is shared across the application.
 */
public class DoctorAvailabilityRepository {
    private static DoctorAvailabilityRepository instance; // Singleton instance
    private Map<String, DoctorAvailability> doctorAvailabilityMap; // Map of doctor availabilities

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the repository.
     */
    private DoctorAvailabilityRepository() {
        this.doctorAvailabilityMap = new HashMap<>();
    }

    /**
     * Retrieves the single instance of the `DoctorAvailabilityRepository`.
     * 
     * @return The Singleton instance of the repository.
     */
    public static synchronized DoctorAvailabilityRepository getInstance() {
        if (instance == null) {
            instance = new DoctorAvailabilityRepository();
        }
        return instance;
    }

    /**
     * Adds or updates a doctor's availability in the repository.
     *
     * @param doctorId    The ID of the doctor.
     * @param availability The list of available time slots for the doctor.
     */
    public void setDoctorAvailability(String doctorId, List<TimeSlot> availability) {
        if (doctorId == null || availability == null) {
            System.out.println("Invalid doctor ID or availability.");
            return;
        }
        doctorAvailabilityMap.put(doctorId, new DoctorAvailability(doctorId, availability));
        System.out.println("Availability updated for Doctor ID: " + doctorId);
    }

    /**
     * Retrieves the availability for a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return The `DoctorAvailability` object for the specified doctor, or `null` if not found.
     */
    public DoctorAvailability getDoctorAvailability(String doctorId) {
        return doctorAvailabilityMap.getOrDefault(doctorId, null);
    }

    /**
     * Retrieves the availability of all doctors.
     *
     * @return A list of all `DoctorAvailability` objects in the repository.
     */
    public List<DoctorAvailability> getAllDoctorAvailabilities() {
        return doctorAvailabilityMap.values().stream().toList();
    }

    /**
     * Prints all doctor availabilities in the repository to the console.
     */
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
