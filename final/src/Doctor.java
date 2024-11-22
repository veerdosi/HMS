import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The `Doctor` class represents a doctor in the system. It includes
 * information about the doctor's age, availability, and schedule.
 * The class provides methods to manage appointments, availability,
 * and scheduling.
 */
public class Doctor extends User {
    private int age;
    private List<TimeSlot> availability;
    private List<Appointment> schedule;
    private DoctorAvailabilityRepository availabilityRepository;
    DoctorAvailabilityRepository repository;

    /**
     * Constructs a `Doctor` object with the specified details.
     *
     * @param userID        The unique identifier of the doctor.
     * @param name          The name of the doctor.
     * @param password      The doctor's password.
     * @param gender        The doctor's gender.
     * @param contactEmail  The doctor's contact email.
     * @param contactNumber The doctor's contact number.
     * @param age           The age of the doctor.
     */
    public Doctor(String userID, String name, String password, String gender,
            String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.Doctor, gender, contactEmail, contactNumber);
        this.age = age;
        this.availabilityRepository = repository;
        this.availability = new ArrayList<>();
        this.schedule = new ArrayList<>();
    }

    /**
     * Gets the age of the doctor.
     *
     * @return The doctor's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the doctor.
     *
     * @param age The doctor's new age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the availability of the doctor as a list of time slots.
     *
     * @return The doctor's availability.
     */
    public List<TimeSlot> getAvailability() {
        return availability;
    }

    /**
     * Updates the doctor's availability and synchronizes it with the centralized
     * repository.
     *
     * @param availability The new availability for the doctor.
     */
    public void setAvailability(List<TimeSlot> availability) {
        this.availability = availability;
        System.out.println("Availability updated for Doctor: " + this.getName());
        DoctorAvailabilityRepository.getInstance().setDoctorAvailability(getUserID(), availability);
    }

    /**
     * Gets the doctor's schedule of appointments.
     *
     * @return The doctor's schedule.
     */
    public List<Appointment> getSchedule() {
        return schedule;
    }

    /**
     * Sets the doctor's schedule of appointments.
     *
     * @param schedule The new schedule for the doctor.
     */
    public void setSchedule(List<Appointment> schedule) {
        this.schedule = schedule;
    }

    /**
     * Generates default daily availability slots for the doctor and syncs with the
     * repository.
     */
    public void generateDefaultAvailability() {
        availability = AppointmentSlotUtil.generateDailySlots();
        System.out.println("Default availability generated for Doctor: " + getName());
        if (availabilityRepository != null) {
            availabilityRepository.setDoctorAvailability(getUserID(), availability);
        }
    }

    /**
     * Displays the doctor's schedule of appointments.
     */
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

    /**
     * Processes an appointment by accepting or declining it.
     *
     * @param appointment The appointment to process.
     * @param accept      `true` to accept the appointment, `false` to decline.
     */
    public void processAppointment(Appointment appointment, boolean accept) {
        if (appointment == null || appointment.getId() == null) {
            System.out.println("Invalid appointment data.");
            return;
        }
        if (accept) {
            schedule.add(appointment);
        }
        System.out.println("Appointment " + (accept ? "accepted" : "declined") +
                " for patient: " + (appointment.getPatientID() != null ? appointment.getPatientID() : "Unknown"));
    }

    /**
     * Checks if the doctor is available at a specific date and time.
     * Currently only checks if the time matches any available time slot.
     *
     * @param newDateTime The date and time to check.
     * @return `true` if the doctor has that time slot, `false` otherwise.
     */
    public boolean isAvailable(LocalDateTime newDateTime) {
        if (availability == null || availability.isEmpty()) {
            return false;
        }

        // Only check if the time matches a slot, regardless of date
        String timeStr = newDateTime.toLocalTime().toString();
        timeStr = timeStr.substring(0, 5); // Get just HH:mm part

        for (TimeSlot slot : availability) {
            if (slot.getStartTime().equals(timeStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the availability of a specific time slot.
     *
     * @param index       The index of the time slot in the availability list.
     * @param isAvailable `true` to mark the slot as available, `false` to mark it
     *                    as unavailable.
     */
    public void setCustomSlotAvailability(int index, boolean isAvailable) {
        if (availability == null || index < 0 || index >= availability.size()) {
            System.out.println("Invalid slot index.");
            return;
        }
        availability.get(index).setAvailable(isAvailable);
        System.out.println("Updated availability for slot: " + availability.get(index));
        if (availabilityRepository != null) {
            availabilityRepository.setDoctorAvailability(getUserID(), availability);
        }
    }

    /**
     * Frees a specific time slot by making it available.
     *
     * @param time The time of the slot to free.
     * @return `true` if the slot was successfully freed, `false` otherwise.
     */
    public boolean freeSlot(LocalTime time) {
        int index = getSlotIndexForTime(time);
        if (index != -1) {
            availability.get(index).setAvailable(true);
            return true;
        }
        return false;
    }

    /**
     * Books a specific time slot by making it unavailable.
     *
     * @param time The time of the slot to book.
     * @return `true` if the slot was successfully booked, `false` otherwise.
     */
    public boolean bookSlot(LocalTime time) {
        int index = getSlotIndexForTime(time);
        if (index != -1 && availability.get(index).isAvailable()) {
            availability.get(index).setAvailable(false);
            return true;
        }
        return false;
    }

    /**
     * Gets the index of a time slot in the availability list for a specific time.
     *
     * @param time The time to search for.
     * @return The index of the slot, or -1 if not found.
     */
    private int getSlotIndexForTime(LocalTime time) {
        for (int i = 0; i < availability.size(); i++) {
            if (availability.get(i).getStartTime().equals(time)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a string representation of the doctor for debugging.
     *
     * @return A string containing the doctor's details.
     */
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
