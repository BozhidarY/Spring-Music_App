package com.example.demo.SpringService;

import com.example.demo.Databases.SpringPostgreSQLRepos.DeletedUsersRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Entities.Users;
import com.example.demo.Interfaces.AdminCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class AdminService implements AdminCommands {
    private final UserRepository userRepository;
    private final DeletedUsersRepo deletedUsersRepo;
    private HashMap<String, String> deletedUsers;

    @Autowired
    public AdminService(UserRepository userRepository, DeletedUsersRepo deletedUsersRepo, HashMap<String, String> deletedUsers) {
        this.userRepository = userRepository;
        this.deletedUsersRepo = deletedUsersRepo;
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
        if(user != null) {
            userRepository.delete(user);
            deletedUsersRepo.save(user);
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
