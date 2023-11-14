package com.example.demo.SpringControllers;

import com.example.demo.DTO.ChoiceDTO;
import com.example.demo.DTO.LoginRegistrationDTO;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.UserType;
import com.example.demo.Entities.Users;
import com.example.demo.SpringService.SignMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(path = "/v1")
public class SignUpMenuController {

    private final SignMenuService signMenuService;

    @Autowired
    public SignUpMenuController(SignMenuService signMenuService) {
        this.signMenuService = signMenuService;
    }

//    @GetMapping("/users")
//    public List<Users> getUsers(){
//       return userService.getUsers();
//    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity<Users> getUserByName(@PathVariable("username") String username){
//        Users users = userService.getUserByUsername(username);
//        if(users == null){
//            System.out.println("Error");
//        }
//        return new ResponseEntity<Users>(users, HttpStatus.OK);
//    }
    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRegistrationDTO loginRegistrationDTO) {
        String username = loginRegistrationDTO.getUsername();
        String password = loginRegistrationDTO.getPassword();
        UserType userType = loginRegistrationDTO.getUserType();

        if(signMenuService.checkDublicateUser(username)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is taken");
        }
        if(userType == UserType.CLIENT){
            Users newUser = signMenuService.createClientUser(username, password);
            return signMenuService.openClientCommunication(newUser);
        }
        else if(userType == UserType.ARTIST){
            Users newUser = signMenuService.createArtistUser(username, password);
            return signMenuService.openArtistCommunication(newUser);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Input");
        }
    }
    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRegistrationDTO loginRegistrationDTO){
        String username = loginRegistrationDTO.getUsername();
        String password = loginRegistrationDTO.getPassword();

        Users existingUser = signMenuService.checkIfUserExists(username,password);
        if(existingUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else if(existingUser instanceof Client){
            return signMenuService.openClientCommunication(existingUser);
        }
        else if(existingUser instanceof Artist){
            return signMenuService.openArtistCommunication(existingUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }

    @PostMapping("/users/{username}/clientDashboard")
    public ResponseEntity<?> clientDashboard(@PathVariable String username, @RequestBody ChoiceDTO choiceDTO){
        String choice = choiceDTO.getChoice();
        RedirectView redirectView = new RedirectView();

        if(choice.equals("Listen")){
            String dashboardUrl = "/v1/users/" + username + "/clientDashboard/listen";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equals("Random")){
            String dashboardUrl = "/v1/users/" + username + "/clientDashboard/random";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(choice.equals("Playlist")){
            String dashboardUrl = "/v1/users/" + username + "/clientDashboard/playlist";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
    }
}
