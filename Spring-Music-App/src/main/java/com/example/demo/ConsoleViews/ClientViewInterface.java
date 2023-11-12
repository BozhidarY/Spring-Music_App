package com.example.demo.ConsoleViews;



import com.example.demo.Entities.Playlist;
import com.example.demo.Entities.Songs;

import java.util.List;

public interface ClientViewInterface {

    List<Songs> searchForSongDialog();
    void listenChoosenSongDialog(List<Songs> filteredSongs);
    Playlist checkIfPlaylistExistsDialog();
    void checkIfSongInPlaylistExistsDialog(Playlist choosenPlaylist);
    void deletePlaylistDialog();
    void addSongInPlaylistDialog(Playlist choosenPlaylist);
    void deleteSongDialog(Playlist choosenPlaylist);
    void importLibraryDialog();
    void visualizeSongRemainingTime(Songs song);
}
