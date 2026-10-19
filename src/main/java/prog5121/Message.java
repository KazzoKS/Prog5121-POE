package prog5121;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {

    private String messageID;
    private String recipientCell;
    private String messageText;
    private String messageHash;
    private String status;
    private final int messageNumber;

    private static int nextMessageNumber = 0;
    private static int totalMessagesSent = 0;
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    public Message(String recipientCell, String messageText) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageNumber = nextMessageNumber;
        nextMessageNumber++;
    }

    Message(String fixedMessageID, String recipientCell, String messageText, boolean isTestConstructor) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageID = fixedMessageID;
        this.messageNumber = nextMessageNumber;
        nextMessageNumber++;
    }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        return validateRecipientCell(recipientCell);
    }

    public boolean isRecipientCellValid() {
        return Login.matchesCellPattern(recipientCell);
    }

    public static String validateRecipientCell(String cell) {
        if (Login.matchesCellPattern(cell)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        return validateMessageLength(messageText);
    }

    public boolean isMessageLengthValid() {
        return messageText != null && messageText.length() <= 250;
    }

    public static String validateMessageLength(String text) {
        if (text != null && text.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = (text == null ? 0 : text.length()) - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // Builds the message hash required by the task sheet (ID prefix: message number : first+last word)
    public String createMessageHash() {
        String idPrefix = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        this.messageHash = (idPrefix + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    // Handles the user's choice to send, disregard, or store a message
    public String sentMessage(int choice) {
        if (messageHash == null) {
            createMessageHash();
        }
        switch (choice) {
            case 1:
                status = "SENT";
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                status = "DISREGARDED";
                return "Press 0 to delete the message.";
            case 3:
                status = "STORED";
                storedMessages.add(this);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.getFullDetails()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public int returnTotalMessagesSent() {
        return totalMessagesSent;
    }

    public static int getTotalMessagesSentStatic() {
        return totalMessagesSent;
    }

    public String storeMessage() {
        String json = "{\"messageID\":\"" + messageID + "\","
                + "\"messageHash\":\"" + messageHash + "\","
                + "\"recipient\":\"" + recipientCell + "\","
                + "\"message\":\"" + messageText.replace("\"", "\\\"") + "\"}";
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json + System.lineSeparator());
        } catch (IOException e) {
            return "Could not store message: " + e.getMessage();
        }
        return "Message successfully stored.";
    }

    public String getFullDetails() {
        return "Message ID: " + messageID
                + System.lineSeparator() + "Message Hash: " + messageHash
                + System.lineSeparator() + "Recipient: " + recipientCell
                + System.lineSeparator() + "Message: " + messageText;
    }

    public String getMessageID() { return messageID; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }

    static void resetStaticStateForTests() {
        nextMessageNumber = 0;
        totalMessagesSent = 0;
        sentMessages.clear();
        storedMessages.clear();
    }
}