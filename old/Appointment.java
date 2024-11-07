package old;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentID;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private Patient patient;
    private Doctor doctor;
    private TimeSlot timeSlot;

    public Appointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
        this.appointmentID = generateAppointmentID();
        this.dateTime = timeSlot.getStartTime();
        this.status = AppointmentStatus.REQUESTED;
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
        timeSlot.book(); // Book the time slot immediately
    }

    private String generateAppointmentID() {
        // Logic to generate unique ID
        return "APPT-" + System.currentTimeMillis();
    }

    public void reschedule(TimeSlot newTimeSlot) {
        timeSlot.release(); // Release old slot
        this.timeSlot = newTimeSlot;
        this.dateTime = newTimeSlot.getStartTime();
        timeSlot.book();
        status = AppointmentStatus.CONFIRMED;
    }

    public void cancel() {
        timeSlot.release();
        status = AppointmentStatus.CANCELED;
    }

    public void updateStatus(AppointmentStatus newStatus) {
        this.status = newStatus;
    }

    // Getter methods for appointment, patient, and doctor IDs
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getDoctorID() {
        return doctor.getDoctorID(); // Assuming Doctor class has getDoctorID() method
    }

    public String getPatientID() {
        return patient.getPatientID(); // Assuming Patient class has getPatientID() method
    }

    // Getter for dateTime if needed
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
