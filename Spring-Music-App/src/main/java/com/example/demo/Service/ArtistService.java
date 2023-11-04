package com.example.demo.Service;

import com.example.demo.Entities.Artist;
import com.example.demo.Repos.ArtistRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ArtistService {
    private ArtistRepo artistRepo;
    public ArtistService(ArtistRepo artistRepo) {
        this.artistRepo = artistRepo;
    }

    public List<Artist> clientTest(){
        List<Artist> artistList = new ArrayList<>();
        Artist artist = new Artist("client1", "123");
        artistRepo.save(artist);
        artistList.add(artist);
        return artistList;

    }
}
