package com.example.demo.SpringControllers;

import com.example.demo.DTO.LoginFormDTO;
import com.example.demo.DTO.LoginResponceDTO;
import com.example.demo.DTO.RegisterLoginFormDTO;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.UserType;
import com.example.demo.Entities.Users;
import com.example.demo.SpringService.SignUpMenuService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class SignUpMenuController {

    private final SignUpMenuService signUpMenuService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpMenuController(SignUpMenuService signUpMenuService, PasswordEncoder passwordEncoder) {
        this.signUpMenuService = signUpMenuService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterLoginFormDTO registerLoginFormDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current user authorities: " + authentication.getAuthorities());

        String username = registerLoginFormDTO.getUsername();
        String password = registerLoginFormDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        UserType userType = registerLoginFormDTO.getUserType();

        if(signUpMenuService.checkDublicateUser(username) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is taken");
        }
        if(userType == UserType.CLIENT){
            Users newUser = signUpMenuService.createClientUser(username, encodedPassword);
            return signUpMenuService.openClientCommunication(newUser);
        }
        else if(userType == UserType.ARTIST){
            Users newUser = signUpMenuService.createArtistUser(username, encodedPassword);
            return signUpMenuService.openArtistCommunication(newUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Input");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody RegisterLoginFormDTO registerLoginFormDTO){
        String username = registerLoginFormDTO.getUsername();
        String password = registerLoginFormDTO.getPassword();

        Users existingUser = signUpMenuService.checkIfUserExists(username,password);
        if(existingUser instanceof Client){
            return signUpMenuService.openClientCommunication(existingUser);
        }
        else if(existingUser instanceof Artist){
            return signUpMenuService.openArtistCommunication(existingUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PostMapping("/loginToken")
    public LoginResponceDTO loginUser(@RequestBody LoginFormDTO loginFormDTO) throws JOSEException {
        return signUpMenuService.loginUser(loginFormDTO.getUsername(), loginFormDTO.getPassword());
    }
}
