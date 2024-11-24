import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The `TimeSlot` class represents a specific time period for scheduling
 * appointments.
 * It includes a start datetime, an end datetime, and the availability of the
 * time slot.
 */
public class TimeSlot {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean isAvailable;

    /**
     * Constructs a `TimeSlot` with the specified start and end datetimes.
     * The slot is marked as available by default.
     *
     * @param startDateTime The start date and time of the slot
     * @param endDateTime   The end date and time of the slot
     */
    public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.isAvailable = true;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * Checks if this time slot overlaps with another time slot
     *
     * @param other The other time slot to check against
     * @return true if the slots overlap, false otherwise
     */
    public boolean overlaps(TimeSlot other) {
        return !this.startDateTime.isAfter(other.endDateTime) &&
                !this.endDateTime.isBefore(other.startDateTime);
    }

    /**
     * Checks if a specific datetime falls within this time slot
     *
     * @param dateTime The datetime to check
     * @return true if the datetime is within this slot, false otherwise
     */
    public boolean contains(LocalDateTime dateTime) {
        return !dateTime.isBefore(startDateTime) && !dateTime.isAfter(endDateTime);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s to %s%s",
                startDateTime.format(formatter),
                endDateTime.format(formatter),
                isAvailable ? " (Available)" : " (Booked)");
    }
}