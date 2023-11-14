package com.example.demo;

import com.example.demo.Databases.SpringPostgreSQLRepos.ApplicationUserRepo;
import com.example.demo.Databases.SpringPostgreSQLRepos.RoleRepo;
import com.example.demo.Entities.ApplicationUser;
import com.example.demo.Entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(RoleRepo roleRepo, ApplicationUserRepo applicationUserRepo, PasswordEncoder passwordEncoder){
//		return args -> {
//			if(roleRepo.findByAuthority("ADMIN").isPresent()) return;
//			com.example.demo.Entities.Role adminRole = roleRepo.save(new Role("ADMIN"));
//			roleRepo.save(new Role("USER"));
//
//			Set<Role> roleSet = new HashSet<>();
//			roleSet.add(adminRole);
//
//			ApplicationUser admin = new ApplicationUser("admin", passwordEncoder.encode("password"), roleSet);
//
//			applicationUserRepo.save(admin);
//		};
//	}
}
