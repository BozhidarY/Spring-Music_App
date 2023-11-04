package com.example.demo.Service;

//import com.example.demo.Entities.Client;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.Users;
import com.example.demo.Repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers(){
        List<Users> listOfUsers = userRepository.findAll();
        return listOfUsers;
    }

    public boolean checkForDublicates(String username){
        for(Users user: getAllUsers()){
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

//    public Users getUserByUsername(String username){
//        Predicate<Users> byName = u -> u.getUsername().equals(username);
//        return filterProducts(byName);
//    }

//    public Users filterProducts(Predicate<Users> strategy){
//        return getUsers().stream().filter(strategy).findFirst().orElse(null);
//    }

    public Users addUser(Users newUsers){
        newUsers.setUsername("newName");
        return newUsers;
    }
    public Users createClientUser(String username, String password) {
        if(isUsernameTaken(username)){
            return null;
        }
        else{
            Client client = new Client(username, password);
            userRepository.save(client);
            return client;
        }
    }
    public Users createArtistUser(String username, String password) {
        if(isUsernameTaken(username)){
            return null;
        }
        else{
            Artist artist = new Artist(username, password);
            userRepository.save(artist);
            return artist;
        }
    }

    public Users loginUser(String username, String password){
        List<Users> usersList = userRepository.findAll();
        for(Users user:usersList){
            if(user.getUsername().equals(username) && password.equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    public boolean isUsernameTaken(String username){
        Users existinguser = userRepository.findByUsername(username);
        return existinguser != null;
    }
}
