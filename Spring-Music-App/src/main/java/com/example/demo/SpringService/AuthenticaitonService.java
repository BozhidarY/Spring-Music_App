//package com.example.demo.SpringService;
//
//import com.example.demo.DTO.LoginResponceDTO;
//import com.example.demo.Databases.SpringPostgreSQLRepos.ApplicationUserRepo;
//import com.example.demo.Databases.SpringPostgreSQLRepos.RoleRepo;
//import com.example.demo.Entities.ApplicationUser;
//import com.example.demo.Entities.Role;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.naming.AuthenticationException;
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//@Transactional
//public class AuthenticaitonService {
//
//    @Autowired
//    private ApplicationUserRepo applicationUserRepo;
//
//    @Autowired
//    private RoleRepo roleRepo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private TokenService tokenService;
//
//    public ApplicationUser registerUser(String username, String password){
//        String encodedPassword = passwordEncoder.encode(password);
//
//        Role userRole = roleRepo.findByAuthority("USER").get();
//
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(userRole);
//
//        return applicationUserRepo.save(new ApplicationUser(username, encodedPassword, roleSet));
//    }
//
//    public LoginResponceDTO loginUser(String username, String password){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username,password)
//        );
//        String token = tokenService.generateJwt(authentication);
//
//        return new LoginResponceDTO(applicationUserRepo.findByUsername(username).get(), token);
//    }
//
//}
