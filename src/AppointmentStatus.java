/**
 * The `AppointmentStatus` enum represents the various states an appointment can have
 * in the system. Each status reflects a specific stage in the appointment lifecycle.
 */
public enum AppointmentStatus {

    /**
     * Indicates that the appointment has been requested but not yet processed or confirmed.
     */
    REQUESTED,

    /**
     * Indicates that the appointment has been confirmed by the doctor or relevant authority.
     */
    CONFIRMED,

    /**
     * Indicates that the appointment request has been declined by the doctor or system.
     */
    DECLINED,

    /**
     * Indicates that the appointment has been canceled by the patient, doctor, or system.
     */
    CANCELLED,

    /**
     * Indicates that the appointment has been successfully completed.
     */
    COMPLETED
}
