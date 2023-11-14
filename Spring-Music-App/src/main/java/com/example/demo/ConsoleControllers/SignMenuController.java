package com.example.demo.ConsoleControllers;


import com.example.demo.Configuration;
import com.example.demo.Databases.ConsoleFIleHandling.*;
import com.example.demo.Entities.Admin;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Users;
import com.example.demo.Interfaces.SignUpMenuInterface;
import com.example.demo.Utils.Constants;
import com.example.demo.Validation.Validators;
import com.example.demo.ConsoleViews.AdminView;
import com.example.demo.ConsoleViews.ArtistView;
import com.example.demo.ConsoleViews.ClientView;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.List;


public class SignMenuController implements SignUpMenuInterface {
    Validators validators = new Validators();
    private UserDB userDB;
    private UserDB deletedUsers;
    private SongDB songDB;
    Configuration config = new Configuration(Constants.APP_PROPERTIES);
    String dataLibraryChoice = config.getDataLibraryChoice();
    LoadSaveProvider libraryProvider = LibraryProviderFactory.createLibraryProvider(dataLibraryChoice);

    public SignMenuController(UserDB userDB, UserDB deletedUsers, SongDB songDB) {
        this.userDB = userDB;
        this.deletedUsers = deletedUsers;
        this.songDB = songDB;
    }

    public List<Users> getAllUsers(){
        return userDB.getUsersList();
    }

    public Users checkIfUserExists(String username, String password) {
        for (Users user : getAllUsers()) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (user.getUsername().equals(username) && result.verified) {
                return user;
            }
        }
        if (username.equals(Admin.getAdmin().getUsername()) && password.equals(Admin.getAdmin().getPassword())) {
            return Admin.getAdmin();
        }
        return null;
    }

    public Users checkDublicateUser(String username) {
        for (Users user : getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void openArtistCommunication(Artist artist) throws IOException {
        ArtistController artistController = new ArtistController(artist, userDB, songDB);
        ArtistView artistView = new ArtistView(artistController);
        artistView.openArtistCommunication();
    }

    public void openClientCommunication(Client client) throws IOException {
        ClientController clientController = new ClientController(client, userDB, songDB);
        ClientView clientView = new ClientView(clientController);
        clientView.openClientCommunication();
    }

    public void openAdminCommunication(Admin admin) throws IOException {
        AdminController adminController = new AdminController(admin, userDB, deletedUsers);
        AdminView adminView = new AdminView(adminController);
        adminView.openAdminCommunicationMenu();

    }

    public Client createClientUser(String username, String password) throws IOException {
        Client client = new Client(username, password);
        userDB.getUsersList().add(client);
        lybraryProvider.saveObject(Constants.USERS_JSON_PATH, userDB);
        return client;
    }

    public Artist createArtistUser(String username, String password) throws IOException {
        Artist artist = new Artist(username, password);
        userDB.getUsersList().add(artist);
        lybraryProvider.saveObject(Constants.USERS_JSON_PATH, userDB);
        return artist;
    }

    public boolean validateUserUsername(String username) {
        if (!validators.validateUsername(username)) {
            return true;
        }
        return false;
    }

    public boolean validateUserPassword(String password) {
        if (!validators.validatePassword(password)) {
            return true;
        }
        return false;
    }

    public UserDB getUserDB() {
        return userDB;
    }

    public void setUserDB(UserDB userDB) {
        this.userDB = userDB;
    }

    public UserDB getDeletedUsers() {
        return deletedUsers;
    }

    public void setDeletedUsers(UserDB deletedUsers) {
        this.deletedUsers = deletedUsers;
    }

    public SongDB getSongData() {
        return songDB;
    }

    public void setSongData(SongDB songDB) {
        this.songDB = songDB;
    }
}
