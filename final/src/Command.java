/**
 * The `Command` interface represents a generic command that can be executed.
 * It follows the Command design pattern and is intended for extensibility.
 */
public interface Command {
    /**
     * Executes the command's specific behavior.
     */
    void execute();
}
