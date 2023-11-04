package com.example.demo.Service;

import com.example.demo.Entities.Client;
import com.example.demo.Entities.Users;
import com.example.demo.Repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private UserRepository userRepository;

    public ClientService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public List<Client> clientTest(){
        List<Client> artistList = new ArrayList<>();
        Client client = new Client("client2", "1243");
        userRepository.save(client);
        artistList.add(client);
        return artistList;
    }

    public Users createUser(String username, String password) {
        if(isUsernameTaken(username)){
            return null;
        }
//        else{
//            Client client = new Client(username, password);
//            userRepository.save(client);
//            return client;
//        }
        return null;
    }

    public boolean loginUser(String username, String password){
        List<Users> usersList = userRepository.findAll();
        for(Users user:usersList){
            if(user.getUsername().equals(username) && password.equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameTaken(String username){
        Users existinguser = userRepository.findByUsername(username);
        return existinguser != null;
    }
}
