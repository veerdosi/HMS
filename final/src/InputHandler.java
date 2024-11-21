import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Gets integer input within a range
     * 
     * @param min minimum value allowed
     * @param max maximum value allowed
     * @return validated integer input
     */
    public static int getIntInput(int min, int max) {
        return getIntInput("Enter your choice: ", min, max);
    }

    /**
     * Gets integer input within a range with a custom prompt
     * 
     * @param prompt message to display to user
     * @param min    minimum value allowed
     * @param max    maximum value allowed
     * @return validated integer input
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
     * Gets string input with validation
     * 
     * @param prompt message to display to user
     * @return validated string input
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
     * Gets string input with custom validation
     * 
     * @param prompt       message to display to user
     * @param validator    function to validate input
     * @param errorMessage message to display on validation failure
     * @return validated string input
     */
    public static String getStringInput(String prompt, java.util.function.Predicate<String> validator,
            String errorMessage) {
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
     * Gets yes/no input from user
     * 
     * @param prompt message to display to user
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
            System.out.println("Please enter 'yes' or 'no'");
        }
    }

    /**
     * Gets the scanner instance
     * 
     * @return the scanner being used by InputHandler
     */
    public static Scanner getScanner() {
        return scanner;
    }

    /**
     * Closes the scanner
     */
    public static void closeScanner() {
        scanner.close();
    }
}