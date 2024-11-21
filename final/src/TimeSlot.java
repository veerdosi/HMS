public class TimeSlot {
    private String startTime; // e.g., "09:00"
    private String endTime; // e.g., "10:00"
    private boolean isAvailable;

    public TimeSlot(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true; // Default to available
    }

    /**
     * @return String
     */
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return startTime + "-" + endTime + (isAvailable ? " (Available)" : " (Unavailable)");
    }
}