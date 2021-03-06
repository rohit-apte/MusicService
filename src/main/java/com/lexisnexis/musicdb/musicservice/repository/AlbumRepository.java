package com.lexisnexis.musicdb.musicservice.repository;

import com.lexisnexis.musicdb.musicservice.dto.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
