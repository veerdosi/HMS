
/**
 * The `AppointmentService` class handles operations related to managing
 * appointments.
 * It provides functionalities such as scheduling, canceling, rescheduling, and
 * modifying
 * appointment details. The service integrates with the DoctorService,
 * PatientService,
 * and the AppointmentOutcomeRecord.
 */

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {
    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentOutcomeRecord outcomeRecord;
    private DoctorAvailabilityRepository availabilityRepository;
    private int appointmentCounter = 1;

    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.outcomeRecord = AppointmentOutcomeRecord.getInstance();
        this.availabilityRepository = DoctorAvailabilityRepository.getInstance();
    }

    private String generateAppointmentID() {
        return "A" + (appointmentCounter++);
    }

    public void scheduleAppointment(Patient patient, String doctorId, LocalDateTime dateTime) {
        // Input validation
        if (patient == null || doctorId == null || dateTime == null) {
            System.out.println("Error: Invalid input parameters.");
            return;
        }

        // Validate appointment time is within clinic hours
        if (!AppointmentSlotUtil.isWithinClinicHours(dateTime)) {
            System.out.println("Error: Appointment time must be within clinic hours.");
            return;
        }

        // Get the doctor
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Error: Doctor not found.");
            return;
        }

        // Check if slot exists in doctor's availability
        if (!availabilityRepository.slotExists(doctorId, dateTime)) {
            System.out.println("Error: Selected time slot does not exist.");
            return;
        }

        // Check if slot is available
        if (!availabilityRepository.isSlotAvailable(doctorId, dateTime)) {
            System.out.println("Error: Selected time slot is not available.");
            return;
        }

        try {
            // Create the appointment
            Appointment appointment = new Appointment(generateAppointmentID(), patient.getUserID(), doctorId, dateTime);

            // Mark the slot as unavailable
            availabilityRepository.updateSlotAvailability(doctorId, dateTime, false);

            // Add to the outcome record
            outcomeRecord.addOutcome(appointment);

            System.out.println("\nAppointment scheduled successfully!");
            System.out.println("-----------------------------");
            System.out.println("Appointment ID: " + appointment.getId());
            System.out.println("Doctor: " + doctor.getName());
            System.out.println("Date/Time: " + AppointmentSlotUtil.formatDateTime(dateTime));
            System.out.println("-----------------------------");
        } catch (Exception e) {
            // If anything fails, make sure to free the slot
            availabilityRepository.updateSlotAvailability(doctorId, dateTime, true);
            System.out.println("Error creating appointment: " + e.getMessage());
        }
    }

    public void cancelAppointment(String appointmentId) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Error: Appointment not found.");
            return;
        }

        Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());
        if (doctor != null) {
            // Free the time slot using the full datetime
            doctor.freeSlot(appointment.getDateTime());
            doctor.getSchedule().removeIf(app -> app.getId().equals(appointmentId));
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        System.out.println("Appointment with ID " + appointmentId + " has been canceled.");
    }

    public void processAppointment(String appointmentId, boolean accept) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Error: Appointment not found.");
            return;
        }

        Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());
        if (doctor == null) {
            System.out.println("Error: Doctor not found.");
            return;
        }

        // Check if the time slot exists
        if (!availabilityRepository.slotExists(doctor.getUserID(), appointment.getDateTime())) {
            System.out.println("Error: Time slot not found in schedule.");
            return;
        }

        // Check for conflicting appointments (excluding the current one)
        boolean hasConflict = doctor.getSchedule().stream()
                .anyMatch(apt -> !apt.getId().equals(appointmentId) && // Exclude current appointment
                        apt.getDateTime().equals(appointment.getDateTime()) &&
                        apt.getStatus() != AppointmentStatus.CANCELLED &&
                        apt.getStatus() != AppointmentStatus.DECLINED);

        if (hasConflict) {
            System.out.println("Error: Another appointment already exists at this time.");
            return;
        }

        if (accept) {
            // Update appointment status
            appointment.setStatus(AppointmentStatus.CONFIRMED);

            // Add to doctor's schedule
            doctor.getSchedule().add(appointment);

            // The slot is already marked as unavailable from scheduling, no need to book
            // again
            System.out.println("Appointment confirmed successfully.");
        } else {
            // Update appointment status
            appointment.setStatus(AppointmentStatus.DECLINED);

            // Free the slot in availability
            availabilityRepository.updateSlotAvailability(doctor.getUserID(), appointment.getDateTime(), true);

            System.out.println("Appointment declined successfully.");
        }
    }

    public boolean rescheduleAppointment(String appointmentID, LocalDateTime newDateTime) {
        // Input validation
        if (!AppointmentSlotUtil.isWithinClinicHours(newDateTime)) {
            System.out.println("Error: New appointment time must be within clinic hours.");
            return false;
        }

        Appointment appointment = outcomeRecord.getAppointmentById(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found for ID: " + appointmentID);
            return false;
        }

        Doctor doctor = doctorService.getDoctorById(appointment.getDoctorID());
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return false;
        }

        // Check if the new time is available
        if (!doctor.isAvailable(newDateTime)) {
            System.out.println("The doctor is not available at the requested time.");
            return false;
        }

        LocalDateTime oldDateTime = appointment.getDateTime();

        // Try to book the new slot first
        if (!doctor.bookSlot(newDateTime)) {
            System.out.println("Failed to book the new time slot.");
            return false;
        }

        try {
            // Free the old slot
            doctor.freeSlot(oldDateTime);

            // Update the appointment
            appointment.setDateTime(newDateTime);
            System.out.println("Appointment rescheduled successfully to " +
                    AppointmentSlotUtil.formatDateTime(newDateTime));
            return true;
        } catch (Exception e) {
            // If anything goes wrong, try to restore the original state
            doctor.freeSlot(newDateTime);
            doctor.bookSlot(oldDateTime);
            System.out.println("Error during rescheduling: " + e.getMessage());
            return false;
        }
    }

    // The following methods remain unchanged as they don't deal with scheduling
    // logic
    public void addConsultationNotes(String appointmentId, String notes) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setConsultationNotes(notes);
            System.out.println("Consultation notes added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    public void setTypeOfService(String appointmentId, TypeOfService serviceType) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.setTypeOfService(serviceType);
            System.out.println("Type of service set to " + serviceType + " for appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    public void addPrescription(String appointmentId, Prescription prescription) {
        Appointment appointment = outcomeRecord.getAppointmentById(appointmentId);
        if (appointment != null) {
            appointment.addPrescription(prescription);
            System.out.println("Prescription added to appointment ID: " + appointmentId);
        } else {
            System.out.println("Appointment not found for ID: " + appointmentId);
        }
    }

    public Appointment getAppointmentById(String appointmentId) {
        return outcomeRecord.getAppointmentById(appointmentId);
    }
}