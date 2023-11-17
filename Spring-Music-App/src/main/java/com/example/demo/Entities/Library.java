package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "library_playlist",
            joinColumns = @JoinColumn(name = "library_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<Playlist> libraryList;

    @Column(nullable = false)
    @NotEmpty(message = "Can not have empty strings")
    private String libraryName;
    @JsonCreator
    public Library(String libraryName) {
        this.libraryList = new ArrayList<>();
        this.libraryName = libraryName;
    }

    public Library(){

    }

    public List<Playlist> getLibraryList() {
        return libraryList;
    }

    public void setLibraryList(List<Playlist> libraryList) {
        this.libraryList = libraryList;
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
}
