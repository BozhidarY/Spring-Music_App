package com.example.demo.Databases.SpringPostgreSQLRepos;

import com.example.demo.Entities.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepo extends JpaRepository<Songs, Long> {
    Songs findSongByName(String name);
}
