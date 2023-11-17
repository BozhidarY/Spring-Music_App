package com.example.demo.Databases.SpringPostgreSQLRepos;

import com.example.demo.Entities.UserType;
import com.example.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findByUsernameAndPassword(String username, String password);

    Users findByUserType(UserType userType);
    boolean existsByUsername(String username);
}
