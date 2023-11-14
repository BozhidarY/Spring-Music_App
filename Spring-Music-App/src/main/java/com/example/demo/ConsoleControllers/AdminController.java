package com.example.demo.ConsoleControllers;


import com.example.demo.Utils.Configuration;
import com.example.demo.Databases.ConsoleFIleHandling.LibraryProviderFactory;
import com.example.demo.Databases.ConsoleFIleHandling.LoadSaveProvider;
import com.example.demo.Databases.ConsoleFIleHandling.UserDB;
import com.example.demo.Entities.Admin;
import com.example.demo.Entities.Users;
import com.example.demo.Interfaces.AdminCommands;
import com.example.demo.Utils.Constants;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminController implements AdminCommands {
    private Admin admin;
    private UserDB userDB;
    private UserDB deletedUsers;

    Configuration config = new Configuration(Constants.APP_PROPERTIES);
    String dataLibraryChoice = config.getDataLibraryChoice();
    LoadSaveProvider libraryProvider = LibraryProviderFactory.createLibraryProvider(dataLibraryChoice);

    public AdminController(Admin admin, UserDB userDB, UserDB deletedUsers) {
        this.admin = admin;
        this.userDB = userDB;
        this.deletedUsers = deletedUsers;
    }


    public boolean isDublicateUsername(String userName) {
        for (Users user: deletedUsers.getUsersList()) {
            if (user.getUsername().equals(userName)) {
                return false;
            }
        }
        return true;
    }

    public boolean isDublicateUsernameDel(String userName) {
        for (Users user : userDB.getUsersList()) {
            if (user.getUsername().equals(userName)) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteUserAccount(String userName, String password) throws IOException {
        List<Users> removedAccounts = new ArrayList<>();
        for (Users user : userDB.getUsersList()) {
            System.out.println(user);
            if (user.getUsername().equals(userName) && user.getPassword().equals("password")) {
                if (!isDublicateUsername(userName)) {
                    return false;
                } else {
                    deletedUsers.getUsersList().add(user);
                    removedAccounts.add(user);
                }
            }
        }
        userDB.getUsersList().removeAll(removedAccounts);
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, deletedUsers);
        return true;
    }

    public boolean recoverUserAccount(String nameAcc, String password) throws IOException {
        for (Users user : deletedUsers.getUsersList()) {
            if (nameAcc.equals(user.getUsername()) && user.getPassword().equals("password") && isDublicateUsernameDel(nameAcc)) {
                userDB.getUsersList().add(user);
                deletedUsers.getUsersList().remove(user);
                libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
                libraryProvider.saveObject(Constants.SONG_JSON_PATH, deletedUsers);
                return true;
            }
        }
        return false;
    }

    public Admin getAdmin() {
        return admin;
    }

}
