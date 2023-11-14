package com.example.demo.DTO;

public class InPlaylistSearchDTO {
    private String playlistChoice;
    private String songChoice;

    public InPlaylistSearchDTO(String playlistChoice, String songChoice)
    {
        this.playlistChoice = playlistChoice;
        this.songChoice = songChoice;
    }

    public InPlaylistSearchDTO(){

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
