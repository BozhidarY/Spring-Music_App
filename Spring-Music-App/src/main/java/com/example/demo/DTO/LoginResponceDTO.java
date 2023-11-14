package com.example.demo.DTO;

import com.example.demo.Entities.ApplicationUser;

public class LoginResponceDTO {
    private ApplicationUser applicationUser;
    private String jwt;

    public LoginResponceDTO(ApplicationUser applicationUser, String jwt){
        super();
        this.applicationUser = applicationUser;
        this.jwt = jwt;
    }

    public LoginResponceDTO(){
        super();
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
