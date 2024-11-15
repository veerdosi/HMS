package HMSpkg;
public enum UserRole {
    DOCTOR,
    PATIENT,
    ADMIN,
    PHARMACIST;

    // Method to check if the user is a doctor
    public static boolean isDoctor(UserRole role) {
        return role == DOCTOR;
    }

    // Method to check if the user is an admin
    public static boolean isAdmin(UserRole role) {
        return role == ADMIN;
    }
}
