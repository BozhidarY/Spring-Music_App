package com.example.demo.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Users") // Define the table name here
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Users {

    @Id
    private String username;
    private String password;
    private UserType userType;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public Users() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
