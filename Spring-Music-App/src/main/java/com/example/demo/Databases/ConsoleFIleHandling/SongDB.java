package com.example.demo.Databases.ConsoleFIleHandling;


import com.example.demo.Entities.Songs;

import java.util.ArrayList;
import java.util.List;

public class SongDB {
    private List<Songs> songsList;

    public SongDB() {
        this.songsList = new ArrayList<>();
    }

    public List<Songs> getSongsList() {
        return songsList;
    }

    @Override
    public String toString() {
        return "{" + songsList + "}";
    }
}
