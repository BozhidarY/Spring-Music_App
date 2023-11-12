package com.example.demo.SpringService;

//import com.example.demo.Entities.Client;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Users;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Interfaces.SignUpMenuInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Service
public class SignMenuService implements SignUpMenuInterface {

    private final UserRepository userRepository;
    private final RedirectView redirectView;

    public SignMenuService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.redirectView = new RedirectView();
    }

    public List<Users> getAllUsers(){
        List<Users> listOfUsers = userRepository.findAll();
        return listOfUsers;
    }

    public boolean checkDublicateUser(String username){
        for(Users user: getAllUsers()){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

//    public Users getUserByUsername(String username){
//        Predicate<Users> byName = u -> u.getUsername().equals(username);
//        return filterProducts(byName);
//    }

//    public Users filterProducts(Predicate<Users> strategy){
//        return getUsers().stream().filter(strategy).findFirst().orElse(null);
//    }

    public Users createClientUser(String username, String password) {
        if(checkDublicateUser(username)){
            return null;
        }
        else{
            Client client = new Client(username, password);
            userRepository.save(client);
            return client;
        }
    }
    public Users createArtistUser(String username, String password) {
        if(checkDublicateUser(username)){
            return null;
        }
        else{
            Artist artist = new Artist(username, password);
            userRepository.save(artist);
            return artist;
        }
    }

    public Users checkIfUserExists(String username, String password){
        List<Users> usersList = userRepository.findAll();
        for(Users user:usersList){
            if(user.getUsername().equals(username) && password.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    public ResponseEntity<RedirectView> openClientCommunication(Users user){
        String dashboardUrl = "/v1/users/" + user.getUsername() + "/clientDashboard";
        redirectView.setUrl(dashboardUrl);
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }

    public ResponseEntity<RedirectView> openArtistCommunication(Users user){
        String dashboardUrl = "/v1/users/" + user.getUsername() + "/artistDashboard";
        redirectView.setUrl(dashboardUrl);
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }



}
