package com.example.demo.SpringService;


import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Songs;
import com.example.demo.Databases.SpringPostgreSQLRepos.SongRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Interfaces.ArtistCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService implements ArtistCommands {
    private final UserRepository userRepository;
    private Artist artist;
    private final SongRepo songRepo;
    @Autowired
    public ArtistService(UserRepository userRepository, Artist artist, SongRepo songRepo) {
        this.userRepository = userRepository;
        this.artist = artist;
        this.songRepo = songRepo;
    }

    public Artist getClientByUsername(String username){
        return (Artist) userRepository.findByUsername(username);
    }

    public boolean checkIfSongExists(String songName) {
        for (Songs song : songRepo.findAll()) {
            if (song.getName().equals(songName)) {
                return true;
            }
        }
        return false;
    }

    public void addSongToJsonFile(String songName) {
        Songs newSong = new Songs(songName, artist);
        songRepo.save(newSong);
    }

    public List<Artist> showMostListenedArtists() {
        List<Artist> artistList = userRepository.findAll().stream()
                .filter(users -> users instanceof Artist)
                .map(users -> (Artist) users)
                .collect(Collectors.toList());

        Comparator<Artist> artistListenerComparator = Comparator.comparingLong(Artist::getTotalViews).reversed();
        artistList.sort(artistListenerComparator);
        return artistList;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public SongRepo getSongRepo() {
        return songRepo;
    }
}
