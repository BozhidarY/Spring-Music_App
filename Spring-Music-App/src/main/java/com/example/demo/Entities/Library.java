package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    private List<Playlist> libraryList;

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
