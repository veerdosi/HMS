import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Helper {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    /**
     * @param dateString
     * @return LocalDate
     */
    // Method to parse a date from a string
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + dateString);
            return null;
        }
    }

    // Method to parse time from a string (date will default to the current date)
    public static LocalTime parseTime(String timeString) {
        try {
            return LocalTime.parse(timeString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing time: " + timeString);
            return null;
        }
    }

    // Method to parse both date and time from a string
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date-time: " + dateTimeString);
            return null;
        }
    }

    // Method to format a LocalDate object as a date string
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    // Method to format a LocalTime object as a time string
    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    // Method to format a LocalDateTime object as a date-time string
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    // Method to parse a date string and an hour into a LocalDateTime object
    public static LocalDateTime parseDateAndHour(String dateString, int hour) {
        LocalDate date = parseDate(dateString);
        if (date == null) {
            return null; // Return null if the date is invalid
        }
        return date.atTime(hour, 0); // Combine date with the given hour, setting minutes/seconds to 0
    }

    @SuppressWarnings("resource")
  public static String readString(String prompt) {
    System.out.print(prompt);
    return new java.util.Scanner(System.in).nextLine();
  }

  public static int readInt(String prompt) {
    int input = 0;
    boolean valid = false;
    while (!valid) {
      try {
        input = Integer.parseInt(readString(prompt));
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("*** Please enter an integer ***");
      }
    }
    return input;
  }

  public static double readDouble(String prompt) {
    double input = 0;
    boolean valid = false;
    while (!valid) {
      try {
        input = Double.parseDouble(readString(prompt));
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("*** Please enter a double ***");
      }
    }
    return input;
  }

  public static float readFloat(String prompt) {
    float input = 0;
    boolean valid = false;
    while (!valid) {
      try {
        input = Float.parseFloat(readString(prompt));
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("*** Please enter a float ***");
      }
    }
    return input;
  }

  public static long readLong(String prompt) {
    long input = 0;
    boolean valid = false;
    while (!valid) {
      try {
        input = Long.parseLong(readString(prompt));
        valid = true;
      } catch (NumberFormatException e) {
        e.printStackTrace();
        System.out.println("*** Please enter a long ***");
      }
    }
    return input;
  }

  public static char readChar(String prompt) {
    char input = 0;
    boolean valid = false;
    while (!valid) {
      String temp = readString(prompt);
      if (temp.length() != 1) {
        System.out.println("*** Please enter a character ***");
      } else {
        input = temp.charAt(0);
        valid = true;
      }
    }
    return input;
  }

  public static boolean readBoolean(String prompt) {
    boolean valid = false;
    while (!valid) {
      String input = readString(prompt);
      if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")
          || input.equalsIgnoreCase("true") || input.equalsIgnoreCase("t")) {
        return true;
      } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")
          || input.equalsIgnoreCase("false") || input.equalsIgnoreCase("f")) {
        return false;
      } else {
        System.out.println("*** Please enter Yes/No or True/False ***");
      }
    }
    return false;
  }
}
