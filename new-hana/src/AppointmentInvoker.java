public class AppointmentInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void process() {
        if (command != null) {
            command.execute();
        }
    }
}
