package com.example.demo.SpringControllers;

import com.example.demo.DTO.NameSearchDTO;
import com.example.demo.DTO.InPlaylistSearchDTO;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Playlist;
import com.example.demo.Entities.Songs;
import com.example.demo.SpringService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/client")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("{username}/dashboard")
    public HashMap<String, String> showCommands(){
        return clientService.showClientCommands();
    }
    @PostMapping("/{username}/dashboard")
    public ResponseEntity<?> chooseCommandMenu(@PathVariable String username, @RequestBody NameSearchDTO commandMenuChoice){
        String choice = commandMenuChoice.getChoice();
        RedirectView redirectView = new RedirectView();

        if(choice.equalsIgnoreCase("listenMenu")){
            String dashboardUrl = "/client/" + username + "/dashboard/listenMenu";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equalsIgnoreCase("editMenu")){
            String dashboardUrl = "/client/" + username + "/dashboard/editMenu";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
    }

    @GetMapping("/{username}/dashboard/listenMenu")
    public HashMap<String, String > showListenCommands(){
        return clientService.showListenFunctions();
    }

    @PostMapping("/{username}/dashboard/listenMenu")
    public ResponseEntity<?> chooseListenMenuFunction(@PathVariable String username, @RequestBody NameSearchDTO listenMenuChoice){
        String choice = listenMenuChoice.getChoice();
        RedirectView redirectView = new RedirectView();

        if(choice.equalsIgnoreCase("searchSong")){
            String dashboardUrl = "/client/" + username + "/dashboard/listenMenu/searchSong";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equalsIgnoreCase("Random")){
            String dashboardUrl = "/client/" + username + "/dashboard/listenMenu/random";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equalsIgnoreCase("Playlist")){
            String dashboardUrl = "/client/" + username + "/dashboard/listenMenu/playlist";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
    }

    @GetMapping("/{username}/dashboard/listenMenu/searchSong")
    public List<Songs> getAllSongs(){
        return clientService.getAllSongs();
    }

    @PostMapping("/{username}/dashboard/listenMenu/searchSong")
    public ResponseEntity<?> listenDialog(@RequestBody NameSearchDTO nameSearchDTO){
        String songChoice = nameSearchDTO.getChoice();

        Songs choosenSong = clientService.getSongByName(songChoice);
        if(choosenSong != null){
            clientService.changeViewsAfterListeningSong(choosenSong);
            return ResponseEntity.status(HttpStatus.OK).body("You listened to " + choosenSong.getName());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
    }

    @GetMapping("/{username}/dashboard/listenMenu/random")
    public ResponseEntity<?> randomDialog(){
        Songs randomSong = clientService.getRandomSong();
        if(randomSong != null){
            clientService.changeViewsAfterListeningSong(randomSong);
            return ResponseEntity.status(HttpStatus.OK).body("You listened to " + randomSong.getName());

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No songs in the database");
    }

    @PostMapping("/{username}/dashboard/listenMenu/playlist")
    public ResponseEntity<?> playlistDialog(@PathVariable String username, @RequestBody InPlaylistSearchDTO inPlaylistSearchDTO){
        Client client = clientService.getClientByUsername(username);
        clientService.setClient(client);

        String playlistName = inPlaylistSearchDTO.getPlaylistChoice();

        Playlist playlist = clientService.searchPlaylistFromLibrary(playlistName);
        if(playlist != null){
            String songName = inPlaylistSearchDTO.getSongChoice();
            Songs song = clientService.searchSongInPlaylist(playlist, songName);
            if(song != null){
                clientService.changeViewsAfterListeningSong(song);
                return ResponseEntity.status(HttpStatus.OK).body("You listened to " + song.getName());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
    }

    @GetMapping("/{username}/dashboard/editMenu")
    public HashMap<String, String > showEditCommands(){
        return clientService.showEditFunctions();
    }

    @PostMapping("/{username}/dashboard/editMenu")
    public ResponseEntity<?> chooseEditMenuFunction(@PathVariable String username, @RequestBody NameSearchDTO listenMenuChoice){
        String choice = listenMenuChoice.getChoice();
        RedirectView redirectView = new RedirectView();

        if(choice.equalsIgnoreCase("addPlaylist")){
            String dashboardUrl = "/client/" + username + "/dashboard/editMenu/addPlaylist";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equalsIgnoreCase("removePlaylist")){
            String dashboardUrl = "/client/" + username + "/dashboard/editMenu/removePlaylist";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equalsIgnoreCase("addSong")){
            String dashboardUrl = "/client/" + username + "/dashboard/editMenu/addSong";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equalsIgnoreCase("deleteSong")){
            String dashboardUrl = "/client/" + username + "/dashboard/editMenu/removeSong";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }

    @PostMapping("/{username}/dashboard/editMenu/addSong")
    public ResponseEntity<?> AddSongToPlaylist(@PathVariable String username, @RequestBody InPlaylistSearchDTO inPlaylistSearchDTO){
        Client client = clientService.getClientByUsername(username);
        clientService.setClient(client);

        String deletePlaylist = inPlaylistSearchDTO.getPlaylistChoice();
        Playlist choosenPlaylist = clientService.searchPlaylistFromLibrary(deletePlaylist);
        if(choosenPlaylist != null){
            String songChoice = inPlaylistSearchDTO.getSongChoice();
            clientService.addSong(choosenPlaylist, songChoice);
            return ResponseEntity.status(HttpStatus.OK).body("Song added");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No playlist with that name");
    }

    @PostMapping("/{username}/dashboard/editMenu/removeSong")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable String username, @RequestBody InPlaylistSearchDTO inPlaylistSearchDTO){
        Client client = clientService.getClientByUsername(username);
        clientService.setClient(client);

        String deletePlaylist = inPlaylistSearchDTO.getPlaylistChoice();
        Playlist choosenPlaylist = clientService.searchPlaylistFromLibrary(deletePlaylist);
        if(choosenPlaylist != null){
            String songChoice = inPlaylistSearchDTO.getSongChoice();
            clientService.deleteSong(choosenPlaylist, songChoice);
            return ResponseEntity.status(HttpStatus.OK).body("Song added");

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No playlist with that name");
    }

    @PostMapping("/{username}/dashboard/editMenu/addPlaylist")
    public ResponseEntity<?> addplaylist(@PathVariable String username, @RequestBody NameSearchDTO playlistName){
        Client client = clientService.getClientByUsername(username);
        clientService.setClient(client);

        String newPlaylistName = playlistName.getChoice();
        clientService.addPlaylist(newPlaylistName);
        return ResponseEntity.status(HttpStatus.OK).body("Playlist added");
    }

    @PostMapping("/{username}/dashboard/edit/deletePlaylist")
    public ResponseEntity<?> deletePlaylist(@PathVariable String username, @RequestBody NameSearchDTO playlistName){
        Client client = clientService.getClientByUsername(username);
        clientService.setClient(client);

        String deletePlaylistName = playlistName.getChoice();
        Playlist deletedPlaylist = clientService.deletePlaylist(deletePlaylistName);
        if(deletedPlaylist != null){
            clientService.getPlaylistRepo().delete(deletedPlaylist);
            return ResponseEntity.status(HttpStatus.OK).body("Playlist deleted");
        }
        return ResponseEntity.status(HttpStatus.OK).body("No playlist with that name");
    }

    @PostMapping("/{username}/dashboard/import")
    public ResponseEntity<?> importLibrary(@PathVariable String username, @RequestBody NameSearchDTO nameSearchDTO){
        Client client = clientService.getClientByUsername(username);
        clientService.setClient(client);

        String libraryToImport = nameSearchDTO.getChoice();
        if(!clientService.importLibrary(libraryToImport)){
            return ResponseEntity.status(HttpStatus.OK).body("Library imported");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user with that name");
    }
}
