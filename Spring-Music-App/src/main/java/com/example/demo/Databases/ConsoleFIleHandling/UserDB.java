package com.example.demo.Databases.ConsoleFIleHandling;



import com.example.demo.Entities.Users;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private List<Users> usersList;

    public UserDB() {
        this.usersList = new ArrayList<>();
    }

    public List<Users> getUsersList() {
        return usersList;
    }

}
