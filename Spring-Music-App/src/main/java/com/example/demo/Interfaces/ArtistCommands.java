package com.example.demo.Interfaces;

import com.example.demo.Entities.Artist;

import java.util.List;

public interface ArtistCommands {

    boolean checkIfSongExists(String songName);
    void addSongToJsonFile(String songName);
    List<Artist> showMostListenedArtists();
}
