package old;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AppointmentManager {
    private List<Appointment> appointments; // List to store appointments

    public AppointmentManager() {
        this.appointments = new ArrayList<>(); // Initialize the appointments list
    }

    // Method to add an appointment
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("Added appointment: " + appointment.getAppointmentID());
    }

    // Method to cancel an appointment by ID
    public boolean cancelAppointment(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.cancel();
                appointments.remove(appointment);
                System.out.println("Canceled appointment: " + appointmentID);
                return true; // Appointment canceled successfully
            }
        }
        System.out.println("Appointment not found: " + appointmentID);
        return false; // Appointment not found
    }

    // Method to reschedule an appointment
    public boolean rescheduleAppointment(String appointmentID, TimeSlot newTimeSlot) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.getTimeSlot().release(); // Release old time slot
                appointment.reschedule(newTimeSlot);
                System.out.println("Appointment rescheduled.");
                return true;
            }
        }
        System.out.println("Appointment not found.");
        return false;
    }

    // Method to retrieve available slots for a doctor on a specific date
    public List<TimeSlot> getAvailableSlots(String doctorID, Date date) {
        List<TimeSlot> availableSlots = new ArrayList<>();
        // Logic to retrieve available time slots for a doctor on a specific date
        // You can filter the time slots based on appointments for the specified date
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getDateTime().equals(date)) {
                // Add logic to check if time slots overlap
            }
        }
        return availableSlots; // Return the list of available time slots
    }

    // Method to get a doctor's schedule
    public List<Appointment> getDoctorSchedule(String doctorID) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID)) {
                doctorAppointments.add(appointment);
            }
        }
        return doctorAppointments; // Return the list of appointments for the doctor
    }

    // Method to get all appointments
    public List<Appointment> getAllAppointments() {
        return appointments; // Return the list of all appointments
    }

    // Method to get a patient's appointments
    public List<Appointment> getPatientAppointments(String patientID) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments; // Return the list of appointments for the patient
    }
}
