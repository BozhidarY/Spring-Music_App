package com.example.demo.ConsoleControllers;


import com.example.demo.Utils.Configuration;
import com.example.demo.Databases.ConsoleFIleHandling.*;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Songs;
import com.example.demo.Interfaces.ArtistCommands;
import com.example.demo.Utils.Constants;


import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistController implements ArtistCommands {
    private Artist artist;
    private UserDB userDB;
    private SongDB songDB;

    Configuration config = new Configuration(Constants.APP_PROPERTIES);
    String dataLibraryChoice = config.getDataLibraryChoice();
    LoadSaveProvider libraryProvider = LibraryProviderFactory.createLibraryProvider(dataLibraryChoice);

    public ArtistController(Artist artist, UserDB userDB, SongDB songDB) {
        this.artist = artist;
        this.userDB = userDB;
        this.songDB = songDB;
    }

    public boolean checkIfSongExists(String songName) {
        for (Songs song : songDB.getSongsList()) {
            if (song.getName().equals(songName)) {
                return true;
            }
        }
        return false;
    }

    public void addSongToJsonFile(String songName) throws IOException {
        Songs newSong = new Songs(songName, artist);
        songDB.getSongsList().add(newSong);
        libraryProvider.saveObject(Constants.SONG_JSON_PATH, userDB);
    }

    public List<Artist> showMostListenedArtists() {
        List<Artist> artistList = userDB.getUsersList().stream()
                .filter(users -> users instanceof Artist)
                .map(users -> (Artist) users)
                .collect(Collectors.toList());

        Comparator<Artist> artistListenerComparator = Comparator.comparingLong(Artist::getTotalViews).reversed();
        artistList.sort(artistListenerComparator);
        return artistList;
    }


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
