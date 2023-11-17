package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
public class Artist extends Users implements UserDetails {
    private long totalViews;

    public Artist(String username, String password) {
        super(username, password);
        this.totalViews = 0;
        setUserType(UserType.ARTIST);
    }

    public Artist() {
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        authoritySet.add(new SimpleGrantedAuthority(getUserType().getAuthority()));
        return authoritySet;
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "totalViews=" + totalViews +
                '}';
    }
}
