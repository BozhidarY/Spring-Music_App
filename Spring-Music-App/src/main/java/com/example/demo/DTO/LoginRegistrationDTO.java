package com.example.demo.DTO;

import com.example.demo.Entities.UserType;

public class LoginRegistrationDTO {

    private String username;
    private String password;
    private UserType userType;

    public LoginRegistrationDTO(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public LoginRegistrationDTO(){

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
        return "UserDTO{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", userType=" + userType +
                '}';
    }
}
