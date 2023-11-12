package com.example.demo.SpringService;

import com.example.demo.Entities.*;
import com.example.demo.Databases.SpringPostgreSQLRepos.LibraryRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.PlaylistRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.SongRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Interfaces.ClientCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService implements ClientCommands {

    private Client client;
    private UserRepository userRepository;
    private SongRepo songRepo;
    private LibraryRepo libraryRepo;
    private PlaylistRepo playlistRepo;


    @Autowired
    public ClientService(UserRepository userRepository, Client client, SongRepo songRepo, LibraryRepo libraryRepo, PlaylistRepo playlistRepo) {
        this.userRepository = userRepository;
        this.client = client;
        this.songRepo = songRepo;
        this.libraryRepo = libraryRepo;
        this.playlistRepo = playlistRepo;
    }

    public Client getClientByUsername(String username){
        return (Client) userRepository.findByUsername(username);
    }

    @Override
    public List<Songs> getAllSongs(){
        return songRepo.findAll();
    }

    @Override
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public List<Songs> filterSongsBySubstring(String substring) {
        return getAllSongs().stream()
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

    public Songs getSongByChoiceTest(String songChoice) {
        for (Songs song : getAllSongs()) {
            if (song.getName().equals(songChoice)) {
                return song;
            }
        }
        return null;
    }

    @Override
    public Songs getRandomSong() {
        Random random = new Random();
        int randomIndex = random.nextInt(getAllSongs().size());
        Songs currentSong = getAllSongs().get(randomIndex);
        return currentSong;
    }
    @Override
    public void changeArtistDataViews() {
        for (Users user : getAllUsers()) {
            if (user instanceof Artist artist) {
                long counter = 0;
                for (Songs song : getAllSongs()) {
                    if (artist.getUsername().equals(song.getArtistName())) {
                        counter += song.getTimesListened();
                    }
                }
                artist.setTotalViews(counter);
            }
        }
        userRepository.saveAll(getAllUsers());
        songRepo.saveAll(getAllSongs());
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
    public void addPlaylist(String playlistName) {
        Playlist playlist = new Playlist(playlistName);
        client.getLibrary().getLibraryList().add(playlist);
        playlist.setLibrary(client.getLibrary()); // Set the library for the playlist
        userRepository.save(client);
    }

    @Override
    public Playlist deletePlaylist(String playlistName) {
        List<Playlist> removedPlaylists = new ArrayList<>();
        Playlist deletedPlaylist = null;
        for (Playlist playlist : client.getLibrary().getLibraryList()) {
            if (playlistName.equals(playlist.getPlaylistName())) {
                deletedPlaylist = playlist;
            }
        }
        if (deletedPlaylist == null) {
            return null;
        } else {
//            client.getLibrary().getLibraryList().remove(deletedPlaylist);
//            playlistRepo.deleteById(deletedPlaylist.getPlaylist_id());
            return deletedPlaylist;
        }
    }

    @Override
    public boolean addSong(Playlist playlist, String songName) {
        for (Songs song : getAllSongs()) {
            if (song.getName().equals(songName)) {
                playlist.getSongPlaylist().add(song);
                playlistRepo.save(playlist);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean deleteSong(Playlist playlist, String songName) {
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
            playlistRepo.save(playlist);
            return true;
        }
    }

    @Override
    public boolean importLibrary(String username) {
        for (Users user : getAllUsers()) {
            if (user instanceof Client importClient && user.getUsername().equals(username)) {
                client.setLibrary(importClient.getLibrary());
                userRepository.save(client);
                return true;
            }
        }
        return false;
    }

    @Override
    public HashMap<String, Integer> favouriteArtist() {
        HashMap<String, Integer> favouriteArtist = new HashMap<>();
        for (Users user : getAllUsers()) {
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

    public int convertSongDuration(Songs songs) {
        String[] parts = songs.getDuration().split(":");
        int duration = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
        return duration;
    }

    public void visualizeRemainingTime(Songs songs, Scanner scanner) {
        int durationInSeconds = convertSongDuration(songs);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (durationInSeconds * 1000L);

        while (System.currentTimeMillis() < endTime) {
            long remainingTime = (endTime - System.currentTimeMillis()) / 1000;
            System.out.print("\rTime remaining: " + remainingTime + " seconds");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nSong has ended");
    }

    public void visualizeSongRemainingTime(Songs song) {
//        songRemainingTime.visualizeRemainingTime(song, scanner);
        song.setTimesListened(song.getTimesListened() + 1);
    }
    public void updateSongDatabase(Songs song){
        song.setTimesListened(song.getTimesListened() + 1);
        changeArtistDataViews();
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SongRepo getSongRepo() {
        return songRepo;
    }

    public void setSongRepo(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    public LibraryRepo getLibraryRepo() {
        return libraryRepo;
    }

    public void setLibraryRepo(LibraryRepo libraryRepo) {
        this.libraryRepo = libraryRepo;

    }

    public PlaylistRepo getPlaylistRepo() {
        return playlistRepo;
    }

    public void setPlaylistRepo(PlaylistRepo playlistRepo) {
        this.playlistRepo = playlistRepo;
    }
}
