package com.lexisnexis.musicdb.musicservice.service;

import com.lexisnexis.musicdb.musicservice.dto.Album;
import com.lexisnexis.musicdb.musicservice.dto.Artist;
import com.lexisnexis.musicdb.musicservice.model.AlbumDetails;
import com.lexisnexis.musicdb.musicservice.model.ArtistDetails;

import java.util.List;

public interface MusicService {

    ArtistDetails addArtist(Artist artist);

    ArtistDetails getArtist(Long id);

    List<ArtistDetails> getAllArtists(Integer pageNo, Integer pageSize, String sortBy, String sortDirection, String filterBy, String filterValue);

    AlbumDetails addAlbum(Album album, Long artistId);

    List<AlbumDetails> getAlbums(Long artistId, Integer pageNo, Integer pageSize, String sortBy, String sortDirection, String filterBy, String filterValue);

    ArtistDetails updateArtist(Artist artist, Long id);
}
