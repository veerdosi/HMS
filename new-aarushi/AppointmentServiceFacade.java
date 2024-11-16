
import java.util.List;

public class AppointmentServiceFacade {
    private List<Appointment> appointments;

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void updateAppointmentStatus(String appointmentID, String status) {
        for (Appointment appointment : appointments) {
            if (appointment.getId().equals(appointmentID)) {
                appointment.setStatus(status);
                break;
            }
        }
    }
}
