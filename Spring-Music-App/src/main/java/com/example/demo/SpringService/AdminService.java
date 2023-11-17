package com.example.demo.SpringService;

import com.example.demo.Databases.SpringPostgreSQLRepos.DeletedUsersRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.LibraryRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.PlaylistRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Users;
import com.example.demo.Interfaces.AdminCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class AdminService implements AdminCommands {
    private final UserRepository userRepository;
    private final DeletedUsersRepo deletedUsersRepo;
    private LibraryRepo libraryRepo;
    private PlaylistRepo playlistRepo;
    private HashMap<String, String> deletedUsers;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AdminService(UserRepository userRepository, DeletedUsersRepo deletedUsersRepo, HashMap<String, String> deletedUsers, LibraryRepo libraryRepo, PlaylistRepo playlistRepo, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.deletedUsersRepo = deletedUsersRepo;
        this.libraryRepo = libraryRepo;
        this.playlistRepo = playlistRepo;
        this.authenticationManager = authenticationManager;
        this.deletedUsers = new HashMap<>();
    }

    public HashMap<String, String> showAdminCommands(){
        HashMap<String, String> functions = new HashMap<>();
        functions.put("deleteAccount", "Deletes account from the database by provided username and password");
        functions.put("showArtistChart", "Recovers account from the database by provided username and password");

        return functions;
    }

    public boolean deleteUserAccount(String userName, String password) {
        Users user = userRepository.findByUsernameAndPassword(userName, password);
        if(user != null && user instanceof Client client) {
            userRepository.delete(client);
            libraryRepo.delete(client.getLibrary());
            playlistRepo.deleteAll(client.getLibrary().getLibraryList());
            return true;
        }
        return false;
    }

    public boolean recoverUserAccount(String username, String password) {
        Users user = userRepository.findByUsernameAndPassword(username, password);
        if(user != null){
            deletedUsersRepo.delete(user);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
