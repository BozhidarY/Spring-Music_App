//package com.example.demo.Config;
//
//import com.example.demo.Entities.Users;
//import com.example.demo.Repos.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//@Configuration
//public class UserConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository){
//        return args -> {
//            Users user = new Users("boj", "123");
//
//            userRepository.saveAll(List.of(user));
//        };
//    }
//
//
//}
