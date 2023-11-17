package com.example.demo.Interfaces;

import com.example.demo.Entities.Playlist;
import com.example.demo.Entities.Songs;
import com.example.demo.Entities.Users;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ClientCommands {
//    void setView(ClientViewInterface clientViewInterface);

    List<Users> getAllUsers();
    List<Songs> getAllSongs();
    List<Songs> filterSongsBySubstring(String substring);

    Songs getSongByChoice(List<Songs> filteredSongs, String songChoice);

    Songs getRandomSong();

    void changeArtistDataViews() throws IOException;

    Playlist searchPlaylistFromLibrary(String playlistChoice);

    Songs searchSongInPlaylist(Playlist playlist, String choiceSong);

    void addPlaylist(String playlistName) throws IOException;

    boolean deletePlaylist(String playlistName) throws IOException;

    boolean addSong(Playlist playlist, String songName) throws IOException;

    boolean deleteSong(Playlist playlist, String songName) throws IOException;

    boolean importLibrary(String username) throws IOException;

}
