package com.lexisnexis.musicdb.musicservice.repository;

import com.lexisnexis.musicdb.musicservice.dto.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
