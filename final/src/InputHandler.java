import java.util.Scanner;
import java.util.function.Predicate;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Gets the shared Scanner instance.
     * 
     * @return the shared Scanner
     */
    public static Scanner getScanner() {
        return scanner;
    }

    /**
     * Closes the shared Scanner instance. This should only be called once, typically
     * at the end of the program.
     */
    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Gets an integer input from the user within a specified range.
     * 
     * @param min Minimum value allowed
     * @param max Maximum value allowed
     * @return A validated integer input
     */
    public static int getIntInput(int min, int max) {
        return getIntInput("Enter your choice: ", min, max);
    }

    /**
     * Gets an integer input from the user within a specified range with a custom
     * prompt.
     * 
     * @param prompt Message to display to the user
     * @param min    Minimum value allowed
     * @param max    Maximum value allowed
     * @return A validated integer input
     */
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer
                    if (input >= min && input <= max) {
                        return input;
                    }
                } else {
                    scanner.nextLine(); // Clear invalid input
                }
                System.out.printf("Please enter a number between %d and %d%n", min, max);
            } catch (Exception e) {
                scanner.nextLine(); // Clear the buffer in case of any error
                System.out.printf("Please enter a number between %d and %d%n", min, max);
            }
        }
    }

    /**
     * Gets a string input from the user with a prompt. Ensures the input is not
     * empty.
     * 
     * @param prompt Message to display to the user
     * @return A non-empty string input
     */
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Gets a string input with a custom validator.
     * 
     * @param prompt       Message to display to the user
     * @param validator    A function to validate the input
     * @param errorMessage Message to display when validation fails
     * @return A validated string input
     */
    public static String getStringInput(String prompt, Predicate<String> validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && validator.test(input)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    /**
     * Gets a yes/no input from the user.
     * 
     * @param prompt Message to display to the user
     * @return true for yes, false for no
     */
    public static boolean getYesNoInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt + " (yes/no): ").toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            }
            System.out.println("Please enter 'yes' or 'no'.");
        }
    }
}
