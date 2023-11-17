package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
public class Client extends Users implements UserDetails {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private Library library;


    public Client(String username, String password) {
        super(username, password);
        setUserType(UserType.CLIENT);
        this.library = new Library(getUsername() + " library");
        library.getLibraryList().add(new Playlist("defaultPlaylist"));
    }

    public Client(String username, String password, Set<UserType> authorities) {
        super(username, password);
        setUserType(UserType.CLIENT);
        this.library = new Library(getUsername() + " library");
        library.getLibraryList().add(new Playlist("defaultPlaylist"));
    }

    public Client(){

    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
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
        return "Client{" +
                "library=" + library +
                '}';
    }
}
