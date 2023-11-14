package com.example.demo.Interfaces;

import com.example.demo.Entities.Users;

import java.io.IOException;
import java.util.List;

public interface SignUpMenuInterface {
    List<Users> getAllUsers();
    Users checkDublicateUser(String username);
    Users createClientUser(String username, String password) throws IOException;
    Users createArtistUser(String username, String password) throws IOException;
    Users checkIfUserExists(String username, String password);
}
