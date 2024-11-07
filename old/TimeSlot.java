package old;

import java.time.LocalDateTime;

public class TimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAvailable;
    private String doctorID;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime, String doctorID) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true;
        this.doctorID = doctorID;
    }

    public boolean isAvailableForBooking() {
        return isAvailable;
    }

    public void book() {
        isAvailable = false;
    }

    public void release() {
        isAvailable = true;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getDoctorID() {
        return doctorID;
    }
}
