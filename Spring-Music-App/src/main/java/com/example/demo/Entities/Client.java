package com.example.demo.Entities;

import jakarta.persistence.*;

@Entity
public class Client extends Users{

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id")
    private Library library;

    public Client(String username, String password) {
        super(username, password);
        setUserType(UserType.CLIENT);
        this.library = new Library(getUsername() + " library");
        library.getLibraryList().add(new Playlist("1", "defaultPlaylist"));
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
