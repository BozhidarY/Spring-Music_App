package com.example.demo.ConsoleViews;

import com.example.demo.ConsoleControllers.SignMenuController;
import com.example.demo.Entities.Admin;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class SignUpMenu {
    private final Logger logger = (Logger) LogManager.getLogger(SignUpMenu.class);
    Scanner scanner = new Scanner(System.in);
    private SignMenuController signMenuController;

    public SignUpMenu(SignMenuController signMenuController) {
        this.signMenuController = signMenuController;
    }

    public void login() {
        System.out.println("Login Form: ");
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();

        Users user = signMenuController.checkIfUserExists(username, password);
        if (user instanceof Artist artist) {
            logger.info("User has loggined: username={}", username);
            signMenuController.openArtistCommunication(artist);
        } else if (user instanceof Client client) {
            logger.info("User has loggined: username={}", username);
            signMenuController.openClientCommunication(client);
        } else if (user instanceof Admin) {
            logger.info("The admin has been loggined: username={}", username);
            signMenuController.openAdminCommunication(Admin.getAdmin());
        } else if (user == null) {
            System.out.println("The user with this credentials doesn't exist. Do you want to register or try again?");
            String choice = scanner.nextLine();
            chooseSignInOption(choice);
        }
    }

    public void register() {
        System.out.println("Register Form: ");
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        if (!signMenuController.checkDublicateUser(username)) {
            System.out.println("Username is taken. Try again or go to login(Register/ Login)");
            String choice = scanner.nextLine();
            chooseSignInOption(choice);
        }else if (signMenuController.validateUserUsername(username) && signMenuController.validateUserPassword(password)) {
            System.out.println("Both username and password dont match the regex. Try again or go to login(Register/ Login)");
            String choice = scanner.nextLine();
            chooseSignInOption(choice);
        }
        else if (signMenuController.validateUserUsername(username)) {
            System.out.println("Username doenst match the regex. Try again or go to login(Register/ Login)");
            String choice = scanner.nextLine();
            chooseSignInOption(choice);
        } else if (signMenuController.validateUserPassword(password)) {
            System.out.println("Password doenst match the regex. Try again or go to login(Register/ Login)");
            String choice = scanner.nextLine();
            chooseSignInOption(choice);
        }  else {
            System.out.println("Do you want to create Client or Artist account.\n (Client/Artist)");
            String choice = scanner.nextLine();
            switch (choice) {
                case "Artist" -> {
                    Artist artist = signMenuController.createArtistUser(username, hashedPassword);
                    System.out.println("You have registered successfully");
                    logger.info("User registered successfully: username={} as {}", username, artist.getUserType());
                    signMenuController.openArtistCommunication(artist);
                }
                case "Client" -> {
                    Client client = signMenuController.createClientUser(username, hashedPassword);
                    System.out.println("You have registered successfully");
                    logger.info("User registered successfully: username={} as {}", username, client.getUserType());
                    signMenuController.openClientCommunication(client);
                }
            }
        }
    }

    public void chooseSignInOption(String choice) {
        if (choice.equals("Login")) {
            login();
        } else if (choice.equals("Register")) {
            register();
        } else {
            System.out.println("Wrong command. Program exiting.");
        }
    }
}
