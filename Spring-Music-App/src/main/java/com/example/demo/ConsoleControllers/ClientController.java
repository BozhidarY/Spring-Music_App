package com.example.demo.ConsoleControllers;

import com.example.demo.Utils.Configuration;
import com.example.demo.Databases.ConsoleFIleHandling.*;
import com.example.demo.Entities.*;
import com.example.demo.Interfaces.ClientCommands;
import com.example.demo.Utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ClientController implements ClientCommands {
    private Client client;
    private UserDB userDB;
    private SongDB songDB;

    Configuration config = new Configuration(Constants.APP_PROPERTIES);
    String dataLibraryChoice = config.getDataLibraryChoice();
    LoadSaveProvider libraryProvider = LibraryProviderFactory.createLibraryProvider(dataLibraryChoice);

    public ClientController(Client client, UserDB userDB, SongDB songDB) {
        this.client = client;
        this.userDB = userDB;
        this.songDB = songDB;
    }

    public List<Songs> getAllSongs(){
        return songDB.getSongsList();
    }

    public List<Users> getAllUsers(){
        return userDB.getUsersList();
    }


    @Override
    public List<Songs> filterSongsBySubstring(String substring) {
        return songDB.getSongsList().stream()
                .filter(songs -> songs.getName().contains(substring))
                .collect(Collectors.toList());
    }

    @Override
    public Songs getSongByChoice(List<Songs> filteredSongs, String songChoice) {
        for (Songs song : filteredSongs) {
            if (song.getName().equals(songChoice)) {
                return song;
            }
        }
        return null;
    }

    @Override
    public Songs getRandomSong() {
        Random random = new Random();
        int randomIndex = random.nextInt(songDB.getSongsList().size());
        Songs currentSong = songDB.getSongsList().get(randomIndex);
        return currentSong;
    }
    @Override
    public void changeArtistDataViews() throws IOException {
        for (Users user : userDB.getUsersList()) {
            if (user instanceof Artist artist) {
                long counter = 0;
                for (Songs song : songDB.getSongsList()) {
                    if (artist.getUsername().equals(song.getArtistName())) {
                        counter += song.getTimesListened();
                    }
                }
                artist.setTotalViews(counter);
            }
        }
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, songDB);
    }
    @Override
    public Playlist searchPlaylistFromLibrary(String playlistChoice) {
        for (Playlist playlist : client.getLibrary().getLibraryList()) {
            if (playlistChoice.equals(playlist.getPlaylistName())) {
                return playlist;
            }
        }
        return null;
    }
    @Override
    public Songs searchSongInPlaylist(Playlist playlist, String choiceSong) {
        for (Songs song : playlist.getSongPlaylist()) {
            if (song.getName().equals(choiceSong)) {
                return song;
            }
        }
        return null;
    }
    @Override
    public void addPlaylist(String playlistName) throws IOException {
        Playlist playlist = new Playlist(playlistName);
        client.getLibrary().getLibraryList().add(playlist);
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
    }

    @Override
    public Playlist deletePlaylist(String playlistName) throws IOException {
        Playlist deletedPlaylist = null;
        for (Playlist playlist : client.getLibrary().getLibraryList()) {
            if (playlistName.equals(playlist.getPlaylistName())) {
                deletedPlaylist = playlist;
            }
        }
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
        return deletedPlaylist;

    }

    @Override
    public boolean addSong(Playlist playlist, String songName) throws IOException {
        for (Songs song : songDB.getSongsList()) {
            if (song.getName().equals(songName)) {
                playlist.getSongPlaylist().add(song);
                libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean deleteSong(Playlist playlist, String songName) throws IOException {
        List<Songs> deletedSongs = new ArrayList<>();
        for (Songs song : playlist.getSongPlaylist()) {
            if (song.getName().equals(songName)) {
                deletedSongs.add(song);
            }
        }
        if (deletedSongs.isEmpty()) {
            return false;
        } else {
            System.out.println("Success");
            playlist.getSongPlaylist().removeAll(deletedSongs);
            libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
            return true;
        }
    }

    @Override
    public boolean importLibrary(String username) throws IOException {
        for (Users user : userDB.getUsersList()) {
            if (user instanceof Client importClient && user.getUsername().equals(username)) {
                client.setLibrary(importClient.getLibrary());
                libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
                return true;
            }
        }
        return false;
    }

    public HashMap<String, Integer> favouriteArtist() {
        HashMap<String, Integer> favouriteArtist = new HashMap<>();
        for (Users user : userDB.getUsersList()) {
            if (user instanceof Client client) {
                for (Playlist playlist : client.getLibrary().getLibraryList()) {
                    for (Songs song : playlist.getSongPlaylist()) {
                        favouriteArtist.put(song.getArtistName(), favouriteArtist.getOrDefault(song.getArtistName(), 0) + song.getTimesListened());
                    }
                }
            }
        }
        return favouriteArtist;

    }
    public Client getClient() {
        return client;
    }
}


