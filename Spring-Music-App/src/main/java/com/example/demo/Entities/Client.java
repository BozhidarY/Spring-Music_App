package com.example.demo.Entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Client extends Users{

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private Library library;

    public Client(String username, String password) {
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
    public String toString() {
        return "Client{" +
                super.toString() +
                "library=" + library +
                '}';
    }
}
