/**
 * The `DoctorCommand` interface represents a command that a doctor can execute.
 * It is intended to follow the Command design pattern for encapsulating actions
 * as objects.
 */
public interface DoctorCommand {
    /**
     * Executes the command's specific behavior.
     */
    void execute();
}
