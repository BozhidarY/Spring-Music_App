package com.example.demo.Databases.SpringPostgreSQLRepos;

import com.example.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedUsersRepo extends JpaRepository<Users, Long> {

}
