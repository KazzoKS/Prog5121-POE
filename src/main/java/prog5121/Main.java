// Entry point: runs the console menu for registration and login
package prog5121;

import java.util.Scanner;

/**
 * Console application for PROG5121 POE Part 1.
 * No GUI / JOptionPane is used, per the task requirements.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = null;
        boolean registeredSuccessfully = false;

        System.out.println("=== Registration and Login (PROG5121 POE Part 1) ===");

        //-----Registration Loop-----
        while (!registeredSuccessfully) {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter a username (must contain '_' and be <= 5 characters): ");
            String username = scanner.nextLine();

            System.out.print("Enter a password (8+ chars, capital, number, special char): ");
            String password = scanner.nextLine();

            System.out.print("Enter South African cell number (e.g. +27838968976): ");
            String cell = scanner.nextLine();

            login = new Login(firstName, lastName, username, password, cell);
            String result = login.registerUser();
            System.out.println(result);

            registeredSuccessfully = login.isRegistered();
            if (!registeredSuccessfully) {
                System.out.println("Please try registering again.\n");
            }
        }

        //-----Login-----
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        boolean loginSuccessful = login.loginUser(enteredUsername, enteredPassword);
        System.out.println(login.returnLoginStatus(loginSuccessful));

        if (loginSuccessful) {
            runQuickChat(scanner);
        }

        scanner.close();
    }

    private static void runQuickChat(Scanner scanner) {
        System.out.println("\nWelcome to QuickChat.");

        int maxMessages = 0;
        while (maxMessages <= 0) {
            System.out.print("How many messages would you like to send? ");
            try {
                maxMessages = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number greater than zero.");
            }
        }

        int messagesEntered = 0;
        boolean quit = false;

        while (!quit) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
                case "1":
                    if (messagesEntered >= maxMessages) {
                        System.out.println("You have already entered the " + maxMessages
                                + " message(s) you chose to send.");
                        break;
                    }
                    messagesEntered++;
                    sendOneMessage(scanner);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    System.out.println("Total messages sent: " + Message.getTotalMessagesSentStatic());
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option, please choose 1, 2 or 3.");
            }
        }
    }

    private static void sendOneMessage(Scanner scanner) {
        String recipient;
        while (true) {
            System.out.print("Enter recipient cell number (with international code): ");
            recipient = scanner.nextLine().trim();
            String result = Message.validateRecipientCell(recipient);
            System.out.println(result);
            if (Login.matchesCellPattern(recipient)) {
                break;
            }
        }

        String messageText;
        while (true) {
            System.out.print("Enter your message (max 250 characters): ");
            messageText = scanner.nextLine();
            String result = Message.validateMessageLength(messageText);
            System.out.println(result);
            if (messageText.length() <= 250) {
                break;
            }
        }

        Message message = new Message(recipient, messageText);
        message.createMessageHash();

        System.out.println("1) Send Message  2) Disregard Message  3) Store Message to send later");
        System.out.print("Choose an option: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 2;
        }

        String outcome = message.sentMessage(choice);
        System.out.println(outcome);

        if (choice == 2) {
            System.out.print("Press 0 to delete the message: ");
            scanner.nextLine();
        }

        if (choice == 1) {
            System.out.println("\nMessage details:");
            System.out.println(message.getFullDetails());
        }
    }
}