package com.example.demo;

import com.example.demo.ConsoleControllers
        .SignMenuController;
import com.example.demo.Databases.ConsoleFIleHandling.*;
import com.example.demo.ConsoleViews.SignUpMenu;
import com.example.demo.Utils.Constants;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration("src/main/resources/application.properties");
        String dataLibraryChoice = config.getDataLibraryChoice();
        LoadSaveProvider libraryProvider = LibraryProviderFactory.createLibraryProvider(dataLibraryChoice);

        Scanner scanner = new Scanner(System.in);

        SongDB songDB = libraryProvider.loadObject(Constants.SONG_JSON_PATH, SongDB.class );
        UserDB userDB = libraryProvider.loadObject(Constants.USERS_JSON_PATH, UserDB.class);
        UserDB deletedUsers = libraryProvider.loadObject(Constants.DELETEDUSERS_JSON_PATH, UserDB.class);

        SignMenuController signMenuController = new SignMenuController(userDB, deletedUsers, songDB);
        SignUpMenu signUpMenu = new SignUpMenu(signMenuController);

        System.out.println("Login/Register");
        String choice = scanner.nextLine();
        switch (choice) {
            case "Login" -> signUpMenu.login();
            case "Register" -> signUpMenu.register();
            default -> System.out.println("Wrong input");
        }
    }
}
