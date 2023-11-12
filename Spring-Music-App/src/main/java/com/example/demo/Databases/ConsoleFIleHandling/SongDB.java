package com.example.demo.Databases.ConsoleFIleHandling;


import com.example.demo.Entities.Songs;

import java.util.ArrayList;
import java.util.List;

public class SongDB {
    private List<Songs> songs;

    public SongDB() {
        this.songs = new ArrayList<>();
    }

    public List<Songs> getSongsList() {
        return songs;
    }

    @Override
    public String toString() {
        return "{" + songs + "}";
    }
}
