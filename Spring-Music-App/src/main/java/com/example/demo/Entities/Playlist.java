package com.example.demo.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "playlist")
public class Playlist {
    @Id
    private String playlist_id;
    private String playlistName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "library_id")
    private Library library;

    @OneToMany(mappedBy = "playlist")
    private List<Songs> songPlaylist;

    public Playlist(String playlist_id, String playlistName) {
        this.playlist_id = playlist_id;
        this.playlistName = playlistName;
        this.songPlaylist = new ArrayList<>();
    }

    public Playlist(){

    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<Songs> getSongPlaylist() {
        return songPlaylist;
    }

    public void setSongPlaylist(List<Songs> songPlaylist) {
        this.songPlaylist = songPlaylist;
    }
}
