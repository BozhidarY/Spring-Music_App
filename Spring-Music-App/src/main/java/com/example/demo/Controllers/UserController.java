package com.example.demo.Controllers;

import com.example.demo.DTO.LoginRegistrationDTO;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.UserType;
import com.example.demo.Entities.Users;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(path = "/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        RedirectView redirectView = new RedirectView();

        if(!userService.checkForDublicates(username)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is taken");
        }
        if(userType == UserType.CLIENT){
            Users newUser = userService.createClientUser(username, password);
            String dashboardUrl = "/v1/users/" + username + "/clientDashboard";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(userType == UserType.ARTIST){
            Users newUser = userService.createArtistUser(username, password);
            String dashboardUrl = "/v1/users/" + username + "/artistDashboard";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Input");
        }
    }
    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRegistrationDTO loginRegistrationDTO){
        String username = loginRegistrationDTO.getUsername();
        String password = loginRegistrationDTO.getPassword();
        RedirectView redirectView = new RedirectView();
        Users existingUser = userService.loginUser(username,password);
        if(existingUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        else if(existingUser instanceof Client){
            String dashboardUrl = "/v1/users/" + username + "/clientDashboard";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        else if(existingUser instanceof Artist){
            String dashboardUrl = "/v1/users/" + username + "/artistDashboard";
            redirectView.setUrl(dashboardUrl);
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }
//    @GetMapping("/users/{username}/dashboard")
//    public List<Users> dashboard(){
//        return userService.getUsers();
//    }
}
