package com.example.demo.SpringControllers;

import com.example.demo.DTO.ArtistChartBodyDTO;
import com.example.demo.DTO.NameSearchDTO;
import com.example.demo.Entities.Artist;
import com.example.demo.SpringService.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/artist")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }


    @GetMapping("/{username}/dashboard")
    public HashMap<String, String> showArtistCommands(){
        return artistService.showArtistCommands();
    }
    @PostMapping("/{username}/dashboard/addsong")
    public ResponseEntity<?> listenDialog(@PathVariable String username, @RequestBody NameSearchDTO nameSearchDTO){
        Artist artist = artistService.getArtistByUsername(username);
        if(artist == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No profile with that name");
        }
        artistService.setArtist(artist);

        String newSongNameChoice = nameSearchDTO.getChoice();
        if(!artistService.checkIfSongExists(newSongNameChoice)){
            artistService.addSongToJsonFile(newSongNameChoice);
            return ResponseEntity.status(HttpStatus.OK).body("Song was added in the database");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This song already exists");
    }

    @GetMapping("/{username}/dashboard/showArtistChart")
    public ResponseEntity<List<ArtistChartBodyDTO>> showChart(){
        List<Artist> artistList = artistService.showMostListenedArtists();
        List<ArtistChartBodyDTO> artistChartBodyDTOS = artistList.stream()
                .map(artist -> new ArtistChartBodyDTO(artist.getUsername(),artist.getTotalViews()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(artistChartBodyDTOS);
    }
}
