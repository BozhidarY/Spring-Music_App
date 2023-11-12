package com.example.demo.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Songs {

    @Id
    private String name;
//    @ManyToMany(mappedBy = "playlists")
//    private List<Playlist> playlists;

//    private Artist artist;
    private String artistName;
    private String duration;
    private int timesListened;

    public Songs(String name, Artist artist) {
        this.name = name;
        this.artistName = artist.getUsername();
        setDuration("0:05");
        this.timesListened = 0;
    }

    public Songs(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Artist getArtist() {
//        return artist;
//    }
//
//    public void setArtist(Artist artist) {
//        this.artist = artist;
//    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getTimesListened() {
        return timesListened;
    }

    public void setTimesListened(int timesListened) {
        this.timesListened = timesListened;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
//                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'';
    }
}
