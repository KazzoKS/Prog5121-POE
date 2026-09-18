package prog5121;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests using the exact test data and expected system responses
 * given in the PROG5121 POE Part 1 task sheet.
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        // Base user re-created before each test with the "valid" data set.
        login = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
    }

    // ---------- assertTrue / assertFalse tests (Boolean checks) ----------

    @Test
    public void testUsernameCorrectlyFormatted_ReturnsTrue() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(u.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted_ReturnsFalse() {
        Login u = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(u.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexityRequirements_ReturnsTrue() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(u.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements_ReturnsFalse() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertFalse(u.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneNumberCorrectlyFormatted_ReturnsTrue() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(u.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted_ReturnsFalse() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(u.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful_ReturnsTrue() {
        login.registerUser();
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed_ReturnsFalse() {
        login.registerUser();
        assertFalse(login.loginUser("kyl_1", "wrongPassword1!"));
    }

    // ---------- assertEquals tests (String messages) ----------

    @Test
    public void testRegisterUser_UsernameIncorrectlyFormatted_Message() {
        Login u = new Login("Kyle", "Smith", "kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
            "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.",
            u.registerUser()
        );
    }

    @Test
    public void testRegisterUser_PasswordCorrectlyFormatted_Message() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        // With a valid username, password and cell number, registration succeeds.
        assertTrue(u.registerUser().startsWith("Username successfully captured."));
    }

    @Test
    public void testRegisterUser_PasswordDoesNotMeetComplexity_Message() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals(
            "Password is not correctly formatted; please ensure that the password "
                + "contains at least eight characters, a capital letter, a number, "
                + "and a special character.",
            u.registerUser()
        );
    }

    @Test
    public void testRegisterUser_CellCorrectlyFormatted_Message() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(u.registerUser().contains("Cell number successfully captured."));
    }

    @Test
    public void testRegisterUser_CellIncorrectlyFormatted_Message() {
        Login u = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals(
            "Cell number is incorrectly formatted or does not contain an "
                + "international code; please correct the number and try again.",
            u.registerUser()
        );
    }

    @Test
    public void testReturnLoginStatus_Successful_WelcomeMessage() {
        login.registerUser();
        boolean successful = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals(
            "Welcome Kyle, Smith it is great to see you.",
            login.returnLoginStatus(successful)
        );
    }

    @Test
    public void testReturnLoginStatus_Failed_ErrorMessage() {
        login.registerUser();
        boolean successful = login.loginUser("kyl_1", "wrongPassword1!");
        assertEquals(
            "Username or password incorrect, please try again.",
            login.returnLoginStatus(successful)
        );
    }
}
