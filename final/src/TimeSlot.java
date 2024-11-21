/**
 * The `TimeSlot` class represents a specific time period for scheduling appointments.
 * It includes a start time, an end time, and the availability of the time slot.
 */
public class TimeSlot {
    private String startTime; // e.g., "09:00"
    private String endTime; // e.g., "10:00"
    private boolean isAvailable;

    /**
     * Constructs a `TimeSlot` with the specified start and end times.
     * The slot is marked as available by default.
     *
     * @param startTime The start time of the slot (e.g., "09:00").
     * @param endTime   The end time of the slot (e.g., "10:00").
     */
    public TimeSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true; // Default to available
    }

    /**
     * @return The start time of the slot.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return The end time of the slot.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @return `true` if the slot is available, otherwise `false`.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability of the time slot.
     *
     * @param available `true` to mark the slot as available, `false` otherwise.
     */
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * Provides a string representation of the time slot, including its
     * availability status.
     *
     * @return A string in the format "startTime-endTime (Available/Unavailable)".
     */
    @Override
    public String toString() {
        return startTime + "-" + endTime + (isAvailable ? " (Available)" : " (Unavailable)");
    }
}
