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

    public HashMap<String, String> showClientCommands(){
        HashMap<String, String> functions = new HashMap<>();
        functions.put("listenMenu", "listen songs and have fun");
        functions.put("editMenu", "manage your library");
        functions.put("import", "import another user's library");

        return functions;
    }
    public HashMap<String, String> showListenFunctions(){
        HashMap<String, String> functions = new HashMap<>();
        functions.put("searchSong", "Listen a song by your choice");
        functions.put("random", "addSong -> listen a random song");
        functions.put("playlist", "listen a song from your playlist");

        return functions;
    }
    public HashMap<String, String> showEditFunctions(){
        HashMap<String, String> functions = new HashMap<>();
        functions.put("addSong", "add a song to playlist");
        functions.put("deleteSong", "delete a song from playlist");
        functions.put("addPlaylist", "add playlist to your library");
        functions.put("deletePlaylist", "delete playlist from your library");

        return functions;
    }
    public Client getClientByUsername(String username){
        return (Client) userRepository.findByUsername(username);
    }
    public Songs getSongByName(String name){
        return songRepo.findSongByName(name);
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

    public void changeViewsAfterListeningSong(Songs song){
        song.setTimesListened(song.getTimesListened() + 1);
        String artistName = song.getArtistName();
        Users user = userRepository.findByUsername(artistName);
        if(user instanceof Artist artist){
            artist.setTotalViews(artist.getTotalViews() + 1);
            userRepository.save(artist);
            songRepo.save(song);
        }

    }
    @Override
    public Songs getRandomSong() {
        Random random = new Random();
        if(getAllSongs().isEmpty()){
            return null;
        }
        int randomIndex = random.nextInt(getAllSongs().size());
        return getAllSongs().get(randomIndex);
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
        playlist.setLibrary(client.getLibrary());
        userRepository.save(client);
    }

    @Override
    public boolean deletePlaylist(String playlistName) {
        Playlist deletedPlaylist = null;
        for (Playlist playlist : client.getLibrary().getLibraryList()) {
            if (playlistName.equals(playlist.getPlaylistName())) {
                deletedPlaylist = playlist;
            }
        }
        if (deletedPlaylist == null) {
            return false;
        }
        client.getLibrary().getLibraryList().remove(deletedPlaylist);
        userRepository.save(client);
        return true;
    }

    @Override
    public boolean addSong(Playlist playlist, String songName) {
        Songs addSong = songRepo.findSongByName(songName);
        if (addSong != null) {
            playlist.getSongPlaylist().add(addSong);
            playlistRepo.save(playlist);
            return true;
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
        if (!deletedSongs.isEmpty()) {
            playlist.getSongPlaylist().removeAll(deletedSongs);
            playlistRepo.save(playlist);
            return true;
        }
        return false;
    }

    @Override
    public boolean importLibrary(String username) {
        Users findUser = userRepository.findByUsername(username);
        if (findUser instanceof Client importClient) {
            client.setLibrary(importClient.getLibrary());
            userRepository.save(client);
            return true;
        }
        return false;
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
