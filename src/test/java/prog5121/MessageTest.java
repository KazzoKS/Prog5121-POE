package prog5121;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() throws Exception {
        java.lang.reflect.Method reset = Message.class.getDeclaredMethod("resetStaticStateForTests");
        reset.setAccessible(true);
        reset.invoke(null);
    }

    private Message newFixedIdMessage(String fixedId, String recipient, String text) throws Exception {
        Constructor<Message> c = Message.class.getDeclaredConstructor(
                String.class, String.class, String.class, boolean.class);
        c.setAccessible(true);
        return c.newInstance(fixedId, recipient, text, true);
    }

    @Test
    public void testMessageIDNotMoreThanTenCharacters_ReturnsTrue() throws Exception {
        Message m = newFixedIdMessage("0012345678", "+27718693002", "Hi there");
        assertTrue(m.checkMessageID());
    }

    @Test
    public void testMessageIDMoreThanTenCharacters_ReturnsFalse() throws Exception {
        Message m = newFixedIdMessage("00123456789", "+27718693002", "Hi there");
        assertFalse(m.checkMessageID());
    }

    @Test
    public void testRecipientCellCorrectlyFormatted_ReturnsSuccessMessage() {
        Message m = new Message("+27838968976", "Hi there");
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
        assertTrue(m.isRecipientCellValid());
    }

    @Test
    public void testRecipientCellIncorrectlyFormatted_ReturnsFailureMessage() {
        Message m = new Message("08966553", "Hi there");
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.",
            m.checkRecipientCell()
        );
        assertFalse(m.isRecipientCellValid());
    }

    @Test
    public void testMessageLengthWithinLimit_ReturnsReadyToSend() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", m.checkMessageLength());
        assertTrue(m.isMessageLengthValid());
    }

    @Test
    public void testMessageLengthExceedsLimit_ReturnsExcessMessage() {
        String longMessage = "A".repeat(300);
        Message m = new Message("+27718693002", longMessage);
        assertEquals(
            "Message exceeds 250 characters by 50; please reduce the size.",
            m.checkMessageLength()
        );
        assertFalse(m.isMessageLengthValid());
    }

    @Test
    public void testCreateMessageHash_MatchesExpectedValue() throws Exception {
        Message m = newFixedIdMessage("0098765432", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    @Test
    public void testSendTwoMessages_OneSentOneDiscarded_TotalSentIsOne() {
        Message message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message1.createMessageHash();
        assertEquals("Message successfully sent.", message1.sentMessage(1));

        Message message2 = new Message("08575975889", "Hi Keegan, did you receive the payment?");
        message2.createMessageHash();
        assertEquals("Press 0 to delete the message.", message2.sentMessage(2));

        assertEquals(1, message1.returnTotalMessagesSent());
    }

    @Test
    public void testStoreMessageOption_ReturnsStoredMessage() {
        Message m = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        m.createMessageHash();
        assertEquals("Message successfully stored.", m.sentMessage(3));
    }
}
