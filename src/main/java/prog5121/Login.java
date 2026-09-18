package prog5121;

import java.util.regex.Pattern;

/**
 * Login class for the PROG5121 POE Part 1 (Registration and Login feature).
 *
 * Implements the required Boolean/String methods:
 *  - checkUserName()
 *  - checkPasswordComplexity()
 *  - checkCellPhoneNumber()
 *  - registerUser()
 *  - loginUser()
 *  - returnLoginStatus()
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;
    private boolean registered = false;

    // Regex for the cell phone check: a '+' followed by a 1-3 digit
    // international country code, followed by a 9 digit number
    // (e.g. +27838968976 = "+27" country code + "838968976" number).
    // Adapted from the standard international phone-number pattern
    // referenced in the POE task sheet.
    private static final Pattern CELL_PATTERN = Pattern.compile("^\\+\\d{1,3}\\d{9}$");

    public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // ---------- Validation methods ----------

    /**
     * Username must contain an underscore and be no more than five
     * characters long.
     */
    // Validates that username is no more than 5 characters and contains an underscore
    public boolean checkUserName() {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    /**
     * Password must be at least 8 characters long and contain a capital
     * letter, a number, and a special character.
     */
    // Checks password has 8+ characters, a capital letter, a digit, and a special character
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Cell phone number must contain the international country code
     * followed by the number, which is no more than ten characters long.
     */
    // Validates South African cell number format using regex
    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber != null && CELL_PATTERN.matcher(cellPhoneNumber).matches();
    }

    // ---------- Registration ----------

    /**
     * Runs all three checks and returns the appropriate message.
     * Registration only succeeds once username, password AND cell
     * phone number are all correctly formatted.
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital "
                    + "letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an "
                    + "international code; please correct the number and try again.";
        }

        registered = true;
        return "Username successfully captured. Password successfully captured. "
                + "Cell number successfully captured. User " + username
                + " has been registered successfully.";
    }

    // ---------- Login ----------

    /**
     * Verifies that the entered username and password match the ones
     * stored when the user registered.
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return registered
                && username != null && username.equals(enteredUsername)
                && password != null && password.equals(enteredPassword);
    }

    /**
     * Returns the correct message for a successful or failed login,
     * based on the result of loginUser().
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        }
        return "Username or password incorrect, please try again.";
    }

    // ---------- Getters (useful for Main / tests) ----------

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public boolean isRegistered() {
        return registered;
    }
}
