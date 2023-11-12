package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Playlist> libraryPlaylists;

    private String libraryName;
    @JsonCreator
    public Library(@JsonProperty("libraryName") String libraryName) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Playlist> getLibraryPlaylists() {
        return libraryPlaylists;
    }

    public void setLibraryPlaylists(List<Playlist> libraryPlaylists) {
        this.libraryPlaylists = libraryPlaylists;
    }
}
