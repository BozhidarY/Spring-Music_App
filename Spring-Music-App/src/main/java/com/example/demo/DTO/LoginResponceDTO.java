package com.example.demo.DTO;

import com.example.demo.Entities.Users;

public class LoginResponceDTO {
    private Users applicationUser;
    private String jwt;

    public LoginResponceDTO(Users applicationUser, String jwt){
        super();
        this.applicationUser = applicationUser;
        this.jwt = jwt;
    }

    public LoginResponceDTO(){
        super();
    }

    public Users getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(Users applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
