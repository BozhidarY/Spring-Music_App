package com.example.demo.Databases.SpringPostgreSQLRepos;

import com.example.demo.Entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepo extends JpaRepository<Library, Long> {
}
