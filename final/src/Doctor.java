import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private int age;
    private List<TimeSlot> availability; // List of time slots representing availability
    private List<Appointment> schedule; // List of scheduled appointments
    private DoctorAvailabilityRepository availabilityRepository; // Centralized repository
    DoctorAvailabilityRepository repository;

    // Constructor
    public Doctor(String userID, String name, String password, String gender,
            String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.DOCTOR, gender, contactEmail, contactNumber);
        this.age = age;
        this.availabilityRepository = repository; // Sync repository
        this.availability = new ArrayList<>(); // Initialize availability
        this.schedule = new ArrayList<>(); // Initialize schedule
    }

    /**
     * @return int
     */
    // Getter methods
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<TimeSlot> availability) {
        this.availability = availability;
        System.out.println("Availability updated for Doctor: " + this.getName());
        // Sync with centralized repository
        DoctorAvailabilityRepository.getInstance().setDoctorAvailability(getUserID(), availability);
    }

    public List<Appointment> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Appointment> schedule) {
        this.schedule = schedule;
    }

    // Generate default availability slots
    public void generateDefaultAvailability() {
        availability = AppointmentSlotUtil.generateDailySlots(); // Utility method to generate slots
        System.out.println("Default availability generated for Doctor: " + getName());
        // Sync with centralized repository
        if (availabilityRepository != null) {
            availabilityRepository.setDoctorAvailability(getUserID(), availability);
        }
    }

    // View schedule
    public void viewSchedule() {
        System.out.println("\n--- Schedule for Doctor: " + getName() + " ---");
        if (schedule == null || schedule.isEmpty()) {
            System.out.println("No scheduled appointments.");
        } else {
            for (int i = 0; i < schedule.size(); i++) {
                System.out.println(i + ": " + schedule.get(i));
            }
        }
    }

    // Process appointment (accept or decline)
    public void processAppointment(Appointment appointment, boolean accept) {
        if (appointment == null || appointment.getId() == null) {
            System.out.println("Invalid appointment data.");
            return;
        }
        if (accept) {
            schedule.add(appointment); // Add to schedule if accepted
        }
        System.out.println("Appointment " + (accept ? "accepted" : "declined") + " for patient: "
                + (appointment.getPatientID() != null ? appointment.getPatientID() : "Unknown"));
    }

    // Check if the doctor is available at a specific time
    public boolean isAvailable(LocalDateTime newDateTime) {
        if (availability == null || availability.isEmpty()) {
            return false; // No availability defined
        }
        for (TimeSlot slot : availability) {
            if (slot.isAvailable() && slot.getStartTime().equals(newDateTime.toLocalTime())) {
                return true; // Matching available slot found
            }
        }
        return false; // No matching time slot
    }

    // Set custom slot availability
    public void setCustomSlotAvailability(int index, boolean isAvailable) {
        if (availability == null || index < 0 || index >= availability.size()) {
            System.out.println("Invalid slot index.");
            return;
        }
        availability.get(index).setAvailable(isAvailable);
        System.out.println("Updated availability for slot: " + availability.get(index));
        // Sync with centralized repository
        if (availabilityRepository != null) {
            availabilityRepository.setDoctorAvailability(getUserID(), availability);
        }
    }

    public boolean freeSlot(LocalTime time) {
        int index = getSlotIndexForTime(time);
        if (index != -1) {
            availability.get(index).setAvailable(true);
            return true;
        }
        return false; // Slot not found
    }

    public boolean bookSlot(LocalTime time) {
        int index = getSlotIndexForTime(time);
        if (index != -1 && availability.get(index).isAvailable()) {
            availability.get(index).setAvailable(false);
            return true;
        }
        return false; // Slot not available or not found
    }

    // Private method to get slot index for a specific time
    private int getSlotIndexForTime(LocalTime time) {
        for (int i = 0; i < availability.size(); i++) {
            if (availability.get(i).getStartTime().equals(time)) {
                return i;
            }
        }
        return -1; // No matching slot
    }

    // Override toString for debugging
    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + getName() + '\'' +
                ", age=" + age +
                ", contactEmail='" + getContactEmail() + '\'' +
                ", contactNumber='" + getContactNumber() + '\'' +
                '}';
    }
}