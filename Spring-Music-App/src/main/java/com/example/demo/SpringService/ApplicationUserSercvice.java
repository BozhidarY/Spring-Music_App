//package com.example.demo.SpringService;
//
//import com.example.demo.Databases.SpringPostgreSQLRepos.ApplicationUserRepo;
//import com.example.demo.Entities.ApplicationUser;
//import com.example.demo.Entities.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class ApplicationUserSercvice implements UserDetailsService {
//    @Autowired
//    private ApplicationUserRepo applicationUserRepo;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        System.out.println("In the user details service");
////
////        return applicationUserRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not valid"));
//
//        System.out.println("In the user details service");
//        if(!username.equals("Ethan")){
//            throw new UsernameNotFoundException("Not found");
//        }
//        Set<Role> roles = new HashSet<>();
//
//        roles.add(new Role(1, "USER"));
//
//        return new ApplicationUser("Ethan", passwordEncoder.encode("password"), roles);
//
//    }
//
//}
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        System.out.println("In the user details service");
////
////        if(!username.equals("Ethan")){
////            throw new UsernameNotFoundException("Not foudn");
////        }
////        Set<Role> roles = new HashSet<>();
////
////        roles.add(new Role(1, "USER"));
////
////        return new ApplicationUser(1, "Ethan", passwordEncoder.encode("password"), roles);
////    }