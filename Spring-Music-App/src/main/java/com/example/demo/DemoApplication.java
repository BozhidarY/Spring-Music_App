package com.example.demo;

//import com.example.demo.Databases.SpringPostgreSQLRepos.ApplicationUserRepo;
//import com.example.demo.Databases.SpringPostgreSQLRepos.RoleRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.UserRepository;
import com.example.demo.Entities.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner adminInsert(UserRepository userRepository){
		return args -> {
			Users admin = userRepository.findByUserType(UserType.ADMIN);
			if(admin == null){
				Admin admin1 = Admin.getAdmin();
				userRepository.save(admin1);
			}
		};
	}
}
