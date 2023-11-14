package com.example.demo.SpringControllers;

import com.example.demo.DTO.ArtistChart;
import com.example.demo.DTO.ChoiceDTO;
import com.example.demo.Entities.Artist;
import com.example.demo.SpringService.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/users/{username}/artistDashboard/addsong")
    public ResponseEntity<?> listenDialog(@PathVariable String username, @RequestBody ChoiceDTO choiceDTO){
        String newSongNameChoice = choiceDTO.getChoice();
        Artist artist = artistService.getClientByUsername(username);
        artistService.setArtist(artist);

        if(!artistService.checkIfSongExists(newSongNameChoice)){
            artistService.addSongToJsonFile(newSongNameChoice);
            return ResponseEntity.status(HttpStatus.OK).body("Song was added in the database");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This song already exists");
        }
    }

    @GetMapping("/users/{username}/artistDashboard/showChart")
    public ResponseEntity<List<ArtistChart>> showChart(){
        List<Artist> artistList = artistService.showMostListenedArtists();
        List<ArtistChart> artistCharts = artistList.stream()
                .map(artist -> new ArtistChart(artist.getUsername(),artist.getTotalViews()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(artistCharts);
    }
}
