package com.example.demo.Entities;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
    CLIENT,
    ADMIN,
    ARTIST;

    @Override
    public String getAuthority() {
        return name();
    }
}
