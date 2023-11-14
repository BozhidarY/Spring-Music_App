package com.example.demo.Databases.SpringPostgreSQLRepos;

import com.example.demo.Entities.ApplicationUser;
//import com.example.demo.SpringService.ApplicationUserSercvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findByUsername(String username);
}
