package com.example.demo.SpringControllers;

import com.example.demo.DTO.ChoiceDTO;
import com.example.demo.DTO.PlaylistDTO;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Playlist;
import com.example.demo.Entities.Songs;
import com.example.demo.SpringService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/users/{username}/clientDashboard/listen")
    public List<Songs> getAllSongs(){
        return clientService.getAllSongs();
    }

    @PostMapping("/users/{username}/clientDashboard/listen")
    public ResponseEntity<?> listenDialog(@RequestBody ChoiceDTO choiceDTO){
        String choice = choiceDTO.getChoice();

        Songs song = clientService.getSongByChoiceTest(choice);
        if(song == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Song not found");
        }
        else{
            clientService.updateSongDatabase(song);
            return ResponseEntity.status(HttpStatus.OK).body("You listened to " + song.getName());
        }
    }

    @GetMapping("/users/{username}/clientDashboard/random")
    public ResponseEntity<?> randomDialog(){
        Songs song = clientService.getRandomSong();
        if(song == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No songs in the database");
        }
        else{
            clientService.updateSongDatabase(song);
            return ResponseEntity.status(HttpStatus.OK).body("You listened to " + song.getName());
        }
    }

    @PostMapping("/users/{username}/clientDashboard/playlist")
    public ResponseEntity<?> playlistDialog(@RequestBody PlaylistDTO playlistDTO){
        String playlistName = playlistDTO.getPlaylistChoice();

        Playlist playlist = clientService.searchPlaylistFromLibrary(playlistName);
        if(playlist == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Playlist not found");
        }
        else{
            String songName = playlistDTO.getSongChoice();
            Songs song = clientService.searchSongInPlaylist(playlist, songName);
            if(song == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Song not found");
            }
            else{
                clientService.updateSongDatabase(song);
                return ResponseEntity.status(HttpStatus.OK).body("You listened to " + song.getName());
            }
        }
    }

    @PostMapping("/users/{username}/clientDashboard/addplaylist")
    public ResponseEntity<?> addplaylist(@PathVariable String username, @RequestBody ChoiceDTO playlistName){
        Client client = clientService.getClientByUsername(username);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        String newPlaylistName = playlistName.getChoice();
        clientService.setClient(client);
        clientService.addPlaylist(newPlaylistName);
        return ResponseEntity.status(HttpStatus.OK).body("PLaylist added");
    }

    @PostMapping("/users/{username}/clientDashboard/deletePlaylist")
    public ResponseEntity<?> deletePlaylist(@PathVariable String username, @RequestBody ChoiceDTO playlistName){
        Client client = clientService.getClientByUsername(username);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        String deletePlaylist = playlistName.getChoice();
        clientService.setClient(client);
        Playlist deletedplaylist = clientService.deletePlaylist(deletePlaylist);
        if(deletedplaylist == null){
            return ResponseEntity.status(HttpStatus.OK).body("No playlist with that name");
        }
        else {
            clientService.getPlaylistRepo().delete(deletedplaylist);
            return ResponseEntity.status(HttpStatus.OK).body("PLaylist deleted");
        }
    }

    @PostMapping("/users/{username}/clientDashboard/addSongToPlaylist")
    public ResponseEntity<?> AddSongToPlaylist(@PathVariable String username, @RequestBody PlaylistDTO playlistDTO){
        Client client = clientService.getClientByUsername(username);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        String deletePlaylist = playlistDTO.getPlaylistChoice();
        clientService.setClient(client);
        Playlist choosenPlaylist = clientService.searchPlaylistFromLibrary(deletePlaylist);
        if(choosenPlaylist == null){
            return ResponseEntity.status(HttpStatus.OK).body("No playlist with that name");
        }
        else {
            String songChoice = playlistDTO.getSongChoice();
            clientService.addSong(choosenPlaylist, songChoice);
            return ResponseEntity.status(HttpStatus.OK).body("Song added");
        }
    }

    @PostMapping("/users/{username}/clientDashboard/removeSongFromPlaylist")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable String username, @RequestBody PlaylistDTO playlistDTO){
        Client client = clientService.getClientByUsername(username);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        String deletePlaylist = playlistDTO.getPlaylistChoice();
        clientService.setClient(client);
        Playlist choosenPlaylist = clientService.searchPlaylistFromLibrary(deletePlaylist);
        if(choosenPlaylist == null){
            return ResponseEntity.status(HttpStatus.OK).body("No playlist with that name");
        }
        else {
            String songChoice = playlistDTO.getSongChoice();
            clientService.deleteSong(choosenPlaylist, songChoice);
            return ResponseEntity.status(HttpStatus.OK).body("Song added");
        }
    }

    @PostMapping("/users/{username}/clientDashboard/import")
    public ResponseEntity<?> importLibrary(@PathVariable String username, @RequestBody ChoiceDTO choiceDTO){
        Client client = clientService.getClientByUsername(username);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        String libraryToImport = choiceDTO.getChoice();
        clientService.setClient(client);

        if(!clientService.importLibrary(libraryToImport)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with that name");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("Library imported");
        }
    }
}
