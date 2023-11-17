package com.example.demo.SpringService;

//import com.example.demo.Entities.Client;
import com.example.demo.DTO.LoginResponceDTO;
import com.example.demo.Entities.Artist;
import com.example.demo.Entities.Client;
import com.example.demo.Entities.UserType;
import com.example.demo.Entities.Users;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Interfaces.SignUpMenuInterface;
import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SignUpMenuService implements SignUpMenuInterface {

    private final UserRepository userRepository;
    private final RedirectView redirectView;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public SignUpMenuService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.redirectView = new RedirectView();
    }

    public List<Users> getAllUsers(){
        List<Users> listOfUsers = userRepository.findAll();
        return listOfUsers;
    }

    @Override
    public Users checkDublicateUser(String username) {
        return userRepository.findByUsername(username);
    }
    public Users createClientUser(String username, String password) {
        Set<UserType> roles = new HashSet<>();
        roles.add(UserType.CLIENT);
        return userRepository.save(new Client(username, password, roles));
    }
    public Users createArtistUser(String username, String password) {
        Artist artist = new Artist(username, password);
        userRepository.save(artist);
        return artist;
    }

    @Override
    public Users checkIfUserExists(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public ResponseEntity<RedirectView> openClientCommunication(Users user){
        String dashboardUrl = "/client/" + user.getUsername() + "/dashboard";
        redirectView.setUrl(dashboardUrl);
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }

    public ResponseEntity<RedirectView> openArtistCommunication(Users user){
        String dashboardUrl = "/artist/" + user.getUsername() + "/dashboard";
        redirectView.setUrl(dashboardUrl);
        return new ResponseEntity<>(redirectView, HttpStatus.OK);
    }

    public LoginResponceDTO loginUser(String username, String password) throws JOSEException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        Set<String> roles = new HashSet<>();
        roles.add(userRepository.findByUsername(username).getUserType().getAuthority());
        String token = tokenService.generateJwt(authentication);
//        String token = tokenService.generateJwtToken(username, roles, 3600000);

        return new LoginResponceDTO(userRepository.findByUsername(username), token);
    }
}
