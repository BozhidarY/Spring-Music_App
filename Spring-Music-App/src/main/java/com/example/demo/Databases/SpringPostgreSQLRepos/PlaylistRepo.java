package com.example.demo.Databases.SpringPostgreSQLRepos;

import com.example.demo.Entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepo extends JpaRepository<Playlist, Long> {
    void deleteById(Long id);

}
