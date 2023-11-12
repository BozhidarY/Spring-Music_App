package com.example.demo.Interfaces;

import com.example.demo.Entities.Users;

import java.util.List;

public interface SignUpMenuInterface {

    List<Users> getAllUsers();
    boolean checkDublicateUser(String username);
    Users createClientUser(String username, String password);
    Users createArtistUser(String username, String password);
    Users checkIfUserExists(String username, String password);
}
