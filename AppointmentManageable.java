import java.util.List;

public interface AppointmentManageable {
    void acceptAppointment(Appointment appointment);

    void declineAppointment(Appointment appointment);

    void setAvailability(List<TimeSlot> slots);
}
