package com.example.demo.Interfaces;

import com.example.demo.Entities.Playlist;
import com.example.demo.Entities.Songs;
import com.example.demo.Entities.Users;

import java.util.HashMap;
import java.util.List;

public interface ClientCommands {
//    void setView(ClientViewInterface clientViewInterface);

    List<Users> getAllUsers();
    List<Songs> getAllSongs();
    List<Songs> filterSongsBySubstring(String substring);

    Songs getSongByChoice(List<Songs> filteredSongs, String songChoice);

    Songs getRandomSong();

    void changeArtistDataViews();

    Playlist searchPlaylistFromLibrary(String playlistChoice);

    Songs searchSongInPlaylist(Playlist playlist, String choiceSong);

    void addPlaylist(String playlistName);

    Playlist deletePlaylist(String playlistName);

    boolean addSong(Playlist playlist, String songName);

    boolean deleteSong(Playlist playlist, String songName);

    boolean importLibrary(String username);

    HashMap<String, Integer> favouriteArtist();
}
