package com.example.demo;

import com.example.demo.ConsoleControllers
        .SignMenuController;
import com.example.demo.Databases.ConsoleFIleHandling.*;
import com.example.demo.ConsoleViews.SignUpMenu;
import com.example.demo.Entities.Constants;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        Configuration config = new Configuration("C:\\Users\\b1j2d\\Desktop\\Spring-Music_App\\Spring-Music-App\\src\\main\\resources\\application.properties");
        String dataLibraryChoice = config.getDataLibraryChoice();

        Scanner scanner = new Scanner(System.in);
        LoadSaveProvider libraryProvider = null;

        if("Gson".equals(dataLibraryChoice)) {
            libraryProvider = new GsonProvider();
        }
        else if("Jackson".equals(dataLibraryChoice)) {
            libraryProvider = new JacksonProvider();
        }
        else{
            System.out.println("Incorect info");
        }

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

        libraryProvider.saveObject(Constants.USERS_JSON_PATH, userDB);
        libraryProvider.saveObject(Constants.DELETEDUSERS_JSON_PATH, deletedUsers);
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, songDB );
    }
}
