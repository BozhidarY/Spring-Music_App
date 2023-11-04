package com.example.demo.Entities;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER)
    private List<Playlist> libraryPlaylists;

    private String libraryName;

    public Library(String libraryName) {
        this.libraryPlaylists = new ArrayList<>();
        this.libraryName = libraryName;
    }

    public Library(){

    }

    public List<Playlist> getLibraryList() {
        return libraryPlaylists;
    }

    public void setLibraryList(List<Playlist> libraryList) {
        this.libraryPlaylists = libraryList;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
}
