package com.example.demo.SpringService;

//import com.example.demo.Databases.SpringPostgreSQLRepos.ApplicationUserRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
//import com.example.demo.Entities.ApplicationUser;
//import com.example.demo.Entities.Role;
import com.example.demo.Entities.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserSercvice implements UserDetailsService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public ApplicationUserSercvice(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return userRepository.findByUsername(username);
    }
}