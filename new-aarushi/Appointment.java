
import java.util.Date;

public class Appointment {
    private String id;
    private String patientID;
    private String doctorID;
    private Date dateTime;
    private String status;
    private String consultationNotes;
    private String typeOfService;

    public Appointment(String id, String patientID, String doctorID, Date dateTime, String status) {
        this.id = id;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Getter and Setter methods
    public String getId() { return id; }
    public Date getDateTime() { return dateTime; }
    public void setStatus(String status) { this.status = status; }
}
