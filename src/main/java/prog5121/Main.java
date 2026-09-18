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

        // ----- Registration loop -----
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

        // ----- Login -----
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        boolean loginSuccessful = login.loginUser(enteredUsername, enteredPassword);
        System.out.println(login.returnLoginStatus(loginSuccessful));

        scanner.close();
    }
}
