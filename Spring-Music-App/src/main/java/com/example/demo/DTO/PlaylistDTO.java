package com.example.demo.DTO;

import com.example.demo.Entities.Playlist;

public class PlaylistDTO {
    private String playlistChoice;
    private String songChoice;

    public PlaylistDTO(String playlistChoice, String songChoice)
    {
        this.playlistChoice = playlistChoice;
        this.songChoice = songChoice;
    }

    public PlaylistDTO(){

    }

    public String getPlaylistChoice() {
        return playlistChoice;
    }

    public void setPlaylistChoice(String playlistChoice) {
        this.playlistChoice = playlistChoice;
    }

    public String getSongChoice() {
        return songChoice;
    }

    public void setSongChoice(String songChoice) {
        this.songChoice = songChoice;
    }
}
