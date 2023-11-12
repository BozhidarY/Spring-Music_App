package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlist_id;
    private String playlistName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "song_playlist",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Songs> songPlaylist;

    @JsonCreator
    public Playlist(String playlistName) {
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

    public Long getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(Long playlist_id) {
        this.playlist_id = playlist_id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
