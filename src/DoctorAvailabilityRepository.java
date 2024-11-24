import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The `DoctorAvailabilityRepository` class serves as a centralized repository
 * for storing and managing doctor availabilities. It follows the Singleton
 * design pattern
 * and handles datetime-based scheduling.
 */
public class DoctorAvailabilityRepository {
    private static DoctorAvailabilityRepository instance;
    private Map<String, DoctorAvailability> doctorAvailabilityMap;

    private DoctorAvailabilityRepository() {
        this.doctorAvailabilityMap = new HashMap<>();
    }

    /**
     * @return DoctorAvailabilityRepository
     */
    public static synchronized DoctorAvailabilityRepository getInstance() {
        if (instance == null) {
            instance = new DoctorAvailabilityRepository();
        }
        return instance;
    }

    private void validateTimeSlots(List<TimeSlot> slots) {
        LocalDateTime now = LocalDateTime.now();

        // Check for slots in the past
        if (slots.stream().anyMatch(slot -> slot.getStartDateTime().isBefore(now))) {
            throw new IllegalArgumentException("Cannot set availability for past time slots");
        }

        // Sort slots by start time for easier validation
        slots.sort((a, b) -> a.getStartDateTime().compareTo(b.getStartDateTime()));

        // Check for overlapping slots
        for (int i = 0; i < slots.size() - 1; i++) {
            TimeSlot current = slots.get(i);
            TimeSlot next = slots.get(i + 1);

            if (current.getEndDateTime().isAfter(next.getStartDateTime())) {
                throw new IllegalArgumentException("Time slots cannot overlap");
            }
        }
    }

    public void setDoctorAvailability(String doctorId, List<TimeSlot> availability) {
        if (doctorId == null || availability == null) {
            throw new IllegalArgumentException("Doctor ID and availability cannot be null");
        }

        // Create a defensive copy of the list
        List<TimeSlot> availabilityCopy = new ArrayList<>(availability);
        validateTimeSlots(availabilityCopy);
        doctorAvailabilityMap.put(doctorId, new DoctorAvailability(doctorId, availabilityCopy));
    }

    public boolean bookSlot(String doctorId, LocalDateTime dateTime) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return false;
        }

        List<TimeSlot> slots = new ArrayList<>(availability.getSlots());
        TimeSlot targetSlot = slots.stream()
                .filter(slot -> slot.getStartDateTime().equals(dateTime))
                .findFirst()
                .orElse(null);

        if (targetSlot == null) {
            return false;
        }

        // Set the slot as unavailable
        targetSlot.setAvailable(false);

        // Update the availability in the map
        doctorAvailabilityMap.put(doctorId, new DoctorAvailability(doctorId, slots));
        return true;
    }

    public boolean freeSlot(String doctorId, LocalDateTime dateTime) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return false;
        }

        List<TimeSlot> slots = new ArrayList<>(availability.getSlots());
        TimeSlot targetSlot = slots.stream()
                .filter(slot -> slot.getStartDateTime().equals(dateTime))
                .findFirst()
                .orElse(null);

        if (targetSlot == null) {
            return false;
        }

        targetSlot.setAvailable(true);
        doctorAvailabilityMap.put(doctorId, new DoctorAvailability(doctorId, slots));
        return true;
    }

    public DoctorAvailability getDoctorAvailability(String doctorId) {
        return doctorAvailabilityMap.get(doctorId);
    }

    public List<TimeSlot> getAvailableSlots(String doctorId, LocalDateTime after) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return List.of();
        }
        return availability.getAvailableSlots(after);
    }

    public boolean isSlotAvailable(String doctorId, LocalDateTime dateTime) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return false;
        }

        return availability.getSlots().stream()
                .filter(TimeSlot::isAvailable) // Only consider available slots
                .anyMatch(slot -> slot.getStartDateTime().equals(dateTime));
    }

    public List<String> getAvailableDoctors(LocalDateTime dateTime) {
        return doctorAvailabilityMap.entrySet().stream()
                .filter(entry -> isSlotAvailable(entry.getKey(), dateTime))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<DoctorAvailability> getAllDoctorAvailabilities() {
        return new ArrayList<>(doctorAvailabilityMap.values());
    }

    public void printAllDoctorAvailabilities() {
        System.out.println("\n--- All Doctor Availabilities ---");
        if (doctorAvailabilityMap.isEmpty()) {
            System.out.println("No availabilities found.");
        } else {
            doctorAvailabilityMap.values().forEach(availability -> {
                System.out.println(availability);
                System.out.println("--------------------");
            });
        }
    }

    /**
     * Gets all available time slots for a specific doctor, showing both available
     * and booked slots
     * 
     * @param doctorId The ID of the doctor
     * @return List of TimeSlot objects
     */
    public List<TimeSlot> getAllSlots(String doctorId) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(availability.getSlots());
    }

    /**
     * Checks if a given slot exists in the doctor's schedule, regardless of
     * availability
     * 
     * @param doctorId The ID of the doctor
     * @param dateTime The datetime to check
     * @return true if the slot exists, false otherwise
     */
    public boolean slotExists(String doctorId, LocalDateTime dateTime) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return false;
        }

        return availability.getSlots().stream()
                .anyMatch(slot -> slot.getStartDateTime().equals(dateTime));
    }

    /**
     * Updates the availability of a specific time slot without checking current
     * status
     * 
     * @param doctorId    The ID of the doctor
     * @param dateTime    The datetime of the slot to update
     * @param isAvailable The new availability status
     * @return true if the update was successful, false otherwise
     */
    public boolean updateSlotAvailability(String doctorId, LocalDateTime dateTime, boolean isAvailable) {
        DoctorAvailability availability = getDoctorAvailability(doctorId);
        if (availability == null) {
            return false;
        }

        List<TimeSlot> slots = new ArrayList<>(availability.getSlots());
        TimeSlot targetSlot = slots.stream()
                .filter(slot -> slot.getStartDateTime().equals(dateTime))
                .findFirst()
                .orElse(null);

        if (targetSlot == null) {
            return false;
        }

        targetSlot.setAvailable(isAvailable);
        doctorAvailabilityMap.put(doctorId, new DoctorAvailability(doctorId, slots));
        return true;
    }
}