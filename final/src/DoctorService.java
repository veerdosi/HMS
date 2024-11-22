import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The `DoctorService` class handles operations related to managing doctors,
 * including
 * loading doctor data from a CSV file, managing schedules, and handling
 * appointments.
 */
public class DoctorService {
    private List<Doctor> doctors;
    private DoctorAvailabilityRepository availabilityRepository;
    private AppointmentOutcomeRecord outcomeRecord;

    /**
     * Constructs a `DoctorService` and loads doctors from the specified CSV file.
     *
     * @param staffFilePath The file path to the staff CSV data.
     */
    public DoctorService(String staffFilePath) {
        this.doctors = loadDoctorsFromCsv(staffFilePath);
        this.availabilityRepository = DoctorAvailabilityRepository.getInstance();
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
        initializeDoctorAvailabilities();
        synchronizeDoctorSchedules();
    }

    /**
     * Loads doctor data from a specified CSV file.
     *
     * @param filePath The file path to the CSV file.
     * @return A list of `Doctor` objects loaded from the file.
     */
    private List<Doctor> loadDoctorsFromCsv(String filePath) {
        List<Doctor> doctorList = new ArrayList<>();
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("Warning: No staff file path provided. Using empty doctor list.");
            return doctorList;
        }

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
                    System.out.println("Loaded doctor: " + name + " (ID: " + userID + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading doctors from CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing doctor age: " + e.getMessage());
        }

        System.out.println("Loaded " + doctorList.size() + " doctors from file.");
        return doctorList;
    }

    /**
     * Initialize default availabilities for all doctors when the service starts
     */
    private void initializeDoctorAvailabilities() {
        for (Doctor doctor : doctors) {
            if (availabilityRepository.getDoctorAvailability(doctor.getUserID()) == null) {
                doctor.generateDefaultAvailability();
                System.out.println("Generated default availability for doctor: " + doctor.getName());
            }
        }
    }

    /**
     * Synchronize doctor schedules with the appointment records
     */
    private void synchronizeDoctorSchedules() {
        for (Doctor doctor : doctors) {
            List<Appointment> doctorAppointments = outcomeRecord.getAppointmentsByDoctor(doctor.getUserID());
            for (Appointment appointment : doctorAppointments) {
                if (appointment.getStatus() != AppointmentStatus.CANCELLED &&
                        appointment.getStatus() != AppointmentStatus.DECLINED) {
                    doctor.getSchedule().add(appointment);
                    doctor.bookSlot(appointment.getDateTime().toLocalTime());
                }
            }
            System.out.println("Synchronized " + doctorAppointments.size() +
                    " appointments for doctor: " + doctor.getName());
        }
    }

    /**
     * Retrieves the list of all available doctors.
     *
     * @return A list of `Doctor` objects.
     */
    public List<Doctor> getAvailableDoctors() {
        return new ArrayList<>(doctors); // Return a copy to prevent external modifications
    }

    /**
     * Retrieves a doctor by their unique ID.
     *
     * @param doctorId The ID of the doctor.
     * @return The `Doctor` object if found, or `null` if not.
     */
    public Doctor getDoctorById(String doctorId) {
        if (doctorId == null || doctorId.isEmpty()) {
            return null;
        }
        return doctors.stream()
                .filter(d -> d.getUserID().equals(doctorId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if a doctor is available at a specific date and time
     *
     * @param doctorId The ID of the doctor
     * @param dateTime The date and time to check
     * @return true if the doctor is available, false otherwise
     */
    public boolean isDoctorAvailable(String doctorId, LocalDateTime dateTime) {
        if (doctorId == null || dateTime == null) {
            return false;
        }

        Doctor doctor = getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found with ID: " + doctorId);
            return false;
        }

        boolean isAvailable = doctor.isAvailable(dateTime);
        if (!isAvailable) {
            System.out.println("Doctor " + doctor.getName() + " is not available at " + dateTime);
        }
        return isAvailable;
    }

    /**
     * Updates a doctor's schedule with a new appointment
     *
     * @param doctorId    The ID of the doctor
     * @param appointment The appointment to add to the schedule
     * @return true if successfully added, false otherwise
     */
    public boolean addAppointmentToSchedule(String doctorId, Appointment appointment) {
        if (doctorId == null || appointment == null) {
            System.out.println("Invalid doctor ID or appointment");
            return false;
        }

        Doctor doctor = getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found with ID: " + doctorId);
            return false;
        }

        if (!doctor.isAvailable(appointment.getDateTime())) {
            System.out.println("Doctor " + doctor.getName() +
                    " is not available at " + appointment.getDateTime());
            return false;
        }

        // Check for existing appointments at the same time
        boolean hasConflict = doctor.getSchedule().stream()
                .anyMatch(existingApp -> existingApp.getDateTime().equals(appointment.getDateTime()));
        if (hasConflict) {
            System.out.println("Appointment conflict detected for doctor " + doctor.getName());
            return false;
        }

        doctor.getSchedule().add(appointment);
        doctor.bookSlot(appointment.getDateTime().toLocalTime());
        System.out.println("Appointment added to schedule for doctor: " + doctor.getName());
        return true;
    }

    /**
     * Removes an appointment from a doctor's schedule
     *
     * @param doctorId      The ID of the doctor
     * @param appointmentId The ID of the appointment to remove
     * @return true if successfully removed, false otherwise
     */
    public boolean removeAppointmentFromSchedule(String doctorId, String appointmentId) {
        if (doctorId == null || appointmentId == null) {
            System.out.println("Invalid doctor ID or appointment ID");
            return false;
        }

        Doctor doctor = getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found with ID: " + doctorId);
            return false;
        }

        boolean removed = doctor.getSchedule()
                .removeIf(app -> {
                    if (app.getId().equals(appointmentId)) {
                        doctor.freeSlot(app.getDateTime().toLocalTime());
                        System.out.println("Appointment removed from schedule for doctor: " + doctor.getName());
                        return true;
                    }
                    return false;
                });

        if (!removed) {
            System.out.println("Appointment not found in doctor's schedule");
        }
        return removed;
    }

    /**
     * Updates the availability of a specific doctor
     *
     * @param doctorId     The ID of the doctor
     * @param availability The new list of time slots
     * @return true if successfully updated, false otherwise
     */
    public boolean updateDoctorAvailability(String doctorId, List<TimeSlot> availability) {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found with ID: " + doctorId);
            return false;
        }

        doctor.setAvailability(availability);
        availabilityRepository.setDoctorAvailability(doctorId, availability);
        System.out.println("Updated availability for doctor: " + doctor.getName());
        return true;
    }

    /**
     * Gets all appointments for a specific doctor
     *
     * @param doctorId The ID of the doctor
     * @return List of appointments for the doctor
     */
    public List<Appointment> getDoctorAppointments(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found with ID: " + doctorId);
            return new ArrayList<>();
        }
        return new ArrayList<>(doctor.getSchedule());
    }
}