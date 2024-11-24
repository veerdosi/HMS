import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * Constructs a `Doctor` object with the specified details.
     */
    public Doctor(String userID, String name, String password, String gender,
            String contactEmail, String contactNumber, int age) {
        super(userID, name, password, UserRole.Doctor, gender, contactEmail, contactNumber);
        this.age = age;
        this.availabilityRepository = DoctorAvailabilityRepository.getInstance();
        this.availability = new ArrayList<>();
        this.schedule = new ArrayList<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<TimeSlot> getAvailability() {
        DoctorAvailability docAvailability = availabilityRepository.getDoctorAvailability(getUserID());
        if (docAvailability != null) {
            return docAvailability.getSlots();
        }
        return new ArrayList<>();
    }

    public void setAvailability(List<TimeSlot> availability) {
        this.availability = new ArrayList<>(availability);
        availabilityRepository.setDoctorAvailability(getUserID(), this.availability);
        System.out.println("Availability updated for Doctor: " + this.getName());
    }

    public List<Appointment> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Appointment> schedule) {
        this.schedule = schedule;
    }

    public void generateDefaultAvailability() {
        // Clear any existing availability
        this.availability = new ArrayList<>();

        // Get tomorrow's date at midnight
        LocalDateTime startDate = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<TimeSlot> slots = new ArrayList<>();

        // Generate slots for the next 14 days
        for (int day = 0; day < 14; day++) {
            LocalDateTime currentDate = startDate.plusDays(day);

            // Morning slots: 9 AM to 12 PM
            LocalDateTime morningStart = currentDate.withHour(9).withMinute(0);
            TimeSlot slot1 = new TimeSlot(morningStart, morningStart.plusHours(1));
            TimeSlot slot2 = new TimeSlot(morningStart.plusHours(1), morningStart.plusHours(2));
            TimeSlot slot3 = new TimeSlot(morningStart.plusHours(2), morningStart.plusHours(3));
            slots.add(slot1);
            slots.add(slot2);
            slots.add(slot3);

            // Afternoon slots: 2 PM to 5 PM
            LocalDateTime afternoonStart = currentDate.withHour(14).withMinute(0);
            TimeSlot slot4 = new TimeSlot(afternoonStart, afternoonStart.plusHours(1));
            TimeSlot slot5 = new TimeSlot(afternoonStart.plusHours(1), afternoonStart.plusHours(2));
            TimeSlot slot6 = new TimeSlot(afternoonStart.plusHours(2), afternoonStart.plusHours(3));
            slots.add(slot4);
            slots.add(slot5);
            slots.add(slot6);
        }

        System.out.println("Generated " + slots.size() + " slots for the next 14 days.");

        try {
            // Update repository first
            availabilityRepository.setDoctorAvailability(getUserID(), slots);
            // If successful, update local availability
            this.availability = new ArrayList<>(slots);
            System.out.println("Default availability generated for Doctor: " + getName());
        } catch (Exception e) {
            System.out.println("Error setting availability: " + e.getMessage());
            // Clear availability if repository update fails
            this.availability = new ArrayList<>();
        }
    }

    /**
     * Checks if the doctor is available at a specific date and time.
     */
    public boolean isAvailable(LocalDateTime dateTime) {
        DoctorAvailability docAvailability = availabilityRepository.getDoctorAvailability(getUserID());
        if (docAvailability == null) {
            return false;
        }

        // Get all available slots for this doctor
        List<TimeSlot> availableSlots = docAvailability.getSlots();
        if (availableSlots == null || availableSlots.isEmpty()) {
            return false;
        }

        // Check if the datetime is at least 24 hours in advance
        LocalDateTime minimumBookingTime = LocalDateTime.now().plusHours(24);
        if (dateTime.isBefore(minimumBookingTime)) {
            System.out.println("Appointments must be booked at least 24 hours in advance.");
            return false;
        }

        // First check if there's already an appointment at this time
        boolean hasExistingAppointment = schedule.stream()
                .anyMatch(apt -> apt.getDateTime().equals(dateTime) &&
                        apt.getStatus() != AppointmentStatus.CANCELLED &&
                        apt.getStatus() != AppointmentStatus.DECLINED);

        if (hasExistingAppointment) {
            return false;
        }

        // Then check if there's an available slot at this time
        return availableSlots.stream()
                .filter(TimeSlot::isAvailable)
                .anyMatch(slot -> {
                    LocalDateTime slotStart = slot.getStartDateTime();
                    return slotStart.toLocalDate().equals(dateTime.toLocalDate()) &&
                            slotStart.toLocalTime().equals(dateTime.toLocalTime());
                });
    }

    /**
     * Books a specific time slot.
     */
    public boolean bookSlot(LocalDateTime dateTime) {
        DoctorAvailability docAvailability = availabilityRepository.getDoctorAvailability(getUserID());
        if (docAvailability == null) {
            return false;
        }

        List<TimeSlot> slots = new ArrayList<>(docAvailability.getSlots());
        for (TimeSlot slot : slots) {
            if (slot.getStartDateTime().equals(dateTime)) {
                if (!slot.isAvailable()) {
                    return false;
                }
                slot.setAvailable(false);
                availabilityRepository.setDoctorAvailability(getUserID(), slots);
                return true;
            }
        }
        return false;
    }

    /**
     * Frees a specific time slot.
     */
    public boolean freeSlot(LocalDateTime dateTime) {
        DoctorAvailability docAvailability = availabilityRepository.getDoctorAvailability(getUserID());
        if (docAvailability == null) {
            return false;
        }

        List<TimeSlot> slots = new ArrayList<>(docAvailability.getSlots());
        for (TimeSlot slot : slots) {
            if (slot.getStartDateTime().equals(dateTime)) {
                slot.setAvailable(true);
                availabilityRepository.setDoctorAvailability(getUserID(), slots);
                return true;
            }
        }
        return false;
    }

    /**
     * Displays the doctor's schedule of appointments.
     */
    public void viewSchedule() {
        System.out.println("\n--- Schedule for Doctor: " + getName() + " ---");
        if (schedule == null || schedule.isEmpty()) {
            System.out.println("No scheduled appointments.");
        } else {
            System.out.println("+---------------+-------------+-------------------------+------------+");
            System.out.println("| Appointment ID| Patient ID  | Date & Time            | Status     |");
            System.out.println("+---------------+-------------+-------------------------+------------+");

            for (Appointment apt : schedule) {
                System.out.printf("| %-13s | %-11s | %-21s | %-10s |\n",
                        apt.getId(),
                        apt.getPatientID(),
                        apt.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                        apt.getStatus());
            }
            System.out.println("+---------------+-------------+-------------------------+------------+");
        }
    }

    /**
     * Updates the availability of a specific time slot.
     */
    public void setCustomSlotAvailability(int index, boolean isAvailable) {
        DoctorAvailability docAvailability = availabilityRepository.getDoctorAvailability(getUserID());
        if (docAvailability == null || index < 0 || index >= docAvailability.getSlots().size()) {
            System.out.println("Invalid slot index or no availability found.");
            return;
        }

        List<TimeSlot> slots = new ArrayList<>(docAvailability.getSlots());
        slots.get(index).setAvailable(isAvailable);
        availabilityRepository.setDoctorAvailability(getUserID(), slots);
        this.availability = slots;
        System.out.println("Updated availability for slot: " + slots.get(index));
    }

    /**
     * Processes an appointment by accepting or declining it.
     */
    public void processAppointment(Appointment appointment, boolean accept) {
        if (appointment == null || appointment.getId() == null) {
            System.out.println("Invalid appointment data.");
            return;
        }

        if (accept) {
            LocalDateTime appointmentDateTime = appointment.getDateTime();

            // Get the specific slot for this appointment
            DoctorAvailability docAvailability = availabilityRepository.getDoctorAvailability(getUserID());
            if (docAvailability == null) {
                System.out.println("No availability found for doctor.");
                return;
            }

            List<TimeSlot> slots = docAvailability.getSlots();
            TimeSlot targetSlot = slots.stream()
                    .filter(slot -> slot.getStartDateTime().equals(appointmentDateTime))
                    .findFirst()
                    .orElse(null);

            if (targetSlot == null) {
                System.out.println("Time slot not found.");
                return;
            }

            // Check for any conflicting appointments (excluding the current one)
            boolean hasConflict = schedule.stream()
                    .filter(apt -> !apt.getId().equals(appointment.getId())) // Exclude current appointment
                    .anyMatch(apt -> apt.getDateTime().equals(appointmentDateTime) &&
                            apt.getStatus() != AppointmentStatus.CANCELLED &&
                            apt.getStatus() != AppointmentStatus.DECLINED);

            if (hasConflict) {
                System.out.println("Another appointment already exists at this time.");
                return;
            }

            // Add to schedule - no need to check availability since this is a pending
            // appointment
            schedule.add(appointment);
            appointment.setStatus(AppointmentStatus.CONFIRMED);
            System.out.println("Appointment accepted successfully.");
        } else {
            // If declining, free the slot and update status
            freeSlot(appointment.getDateTime());
            appointment.setStatus(AppointmentStatus.DECLINED);
            System.out.println("Appointment declined successfully.");
        }
    }

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