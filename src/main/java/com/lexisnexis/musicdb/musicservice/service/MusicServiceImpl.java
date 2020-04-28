package com.lexisnexis.musicdb.musicservice.service;


import com.lexisnexis.musicdb.musicservice.dto.Album;
import com.lexisnexis.musicdb.musicservice.dto.Artist;
import com.lexisnexis.musicdb.musicservice.model.AlbumDetails;
import com.lexisnexis.musicdb.musicservice.model.ArtistDetails;
import com.lexisnexis.musicdb.musicservice.repository.AlbumRepository;
import com.lexisnexis.musicdb.musicservice.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for Artist Service Application is written in this class.
 */
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private AlbumRepository albumRepository;

    /**
     * Takes Artist details as input and add that Artist to database and also makes an entry to history table.
     *
     * @param Artist - input Artist details
     * @return - Saved Artist Object.
     */
    public ArtistDetails addArtist(Artist Artist) {
        Artist savedArtist = artistRepo.save(Artist);
        ArtistDetails artistDetails = null;
        if (savedArtist != null) {
            artistDetails = ArtistDetails.builder()
                    .artistId(savedArtist.getArtistId())
                    .name(savedArtist.getName())
                    .created(savedArtist.getCreated())
                    .lastUpdate(savedArtist.getLastUpdate()).build();
        }
        return artistDetails;
    }

    /**
     * Gets artist details from database by artist ID
     *
     * @param id - Artist id
     * @return Returns artist details if found null otherwise
     */
    @Override
    public ArtistDetails getArtist(Long id) {
        ArtistDetails artistDetails = null;
        Artist savedArtist = artistRepo.findById(id).orElse(null);
        if (savedArtist != null) {
            artistDetails = ArtistDetails.builder().artistId(savedArtist.getArtistId()).name(savedArtist.getName())
                    .created(savedArtist.getCreated()).lastUpdate(savedArtist.getLastUpdate()).build();
        }
        return artistDetails;
    }

    /**
     * Gets list of all artists found by given input.
     *
     * @param pageNo        - page number
     * @param pageSize      - number of records per page
     * @param sortBy        - Sort by column name
     * @param sortDirection - asc for Ascending, desc for Descending
     * @param filterBy      - FilterBy column value
     * @param filterValue   - Filter by value
     * @return - List of Artist details if found by given input, null otherwise
     */
    @Override
    public List<ArtistDetails> getAllArtists(Integer pageNo, Integer pageSize, String sortBy, String sortDirection, String filterBy, String filterValue) {
        Pageable paging;
        Page<Artist> artists;
        List<ArtistDetails> artistDetailsList = null;

        if (sortDirection.equalsIgnoreCase("desc")) {
            paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
        } else {
            paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());
        }

        if (!StringUtils.isEmpty(filterBy) && !StringUtils.isEmpty(filterValue) && filterBy.equalsIgnoreCase("name")) {
            Artist artist = new Artist();
            artist.setName(filterValue);
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
            Example<Artist> artistExample = Example.of(artist, matcher);
            artists = artistRepo.findAll(artistExample, paging);
        } else {
            artists = artistRepo.findAll(paging);
        }

        if (artists.hasContent()) {
            List<ArtistDetails> finalList = new ArrayList<>();
            artists.getContent().forEach(artist ->
                    {
                        ArtistDetails artistDetails = ArtistDetails.builder().name(artist.getName()).artistId(artist.getArtistId())
                                .created(artist.getCreated()).lastUpdate(artist.getLastUpdate()).build();
                        finalList.add(artistDetails);
                    }
            );
            artistDetailsList = finalList;
        }
        return artistDetailsList;
    }

    /**
     * Search artist by given id and Updates the artist with new details.
     *
     * @param artist - new Artist details
     * @param id     - Artist id
     * @return - updated artist details if found with given id, null otherwise
     */

    @Override
    public ArtistDetails updateArtist(Artist artist, Long id) {
        ArtistDetails artistDetails = null;
        Artist savedArtist = artistRepo.findById(id).orElse(null);
        if (savedArtist != null) {
            savedArtist = artistRepo.save(artist);
            artistDetails = ArtistDetails.builder().artistId(savedArtist.getArtistId()).name(savedArtist.getName())
                    .created(savedArtist.getCreated()).lastUpdate(savedArtist.getLastUpdate()).build();
        }
        return artistDetails;
    }

    /**
     * Adds album details in the database.
     *
     * @param album    - Album details to be added in the database.
     * @param artistId - Artist Id of the album
     * @return - Album details added in the database
     */
    public AlbumDetails addAlbum(Album album, Long artistId) {
        AlbumDetails albumDetails = null;

        ArtistDetails artistDetails = getArtist(artistId);

        if (artistDetails != null) {
            album.setArtist(artistRepo.findById(artistId).orElse(null));
            Album savedAlbum = albumRepository.save(album);
            if (savedAlbum != null) {
                albumDetails = AlbumDetails.builder().title(savedAlbum.getTitle()).genres(savedAlbum.getGenres())
                        .yearOfRelease(savedAlbum.getYearOfRelease()).artist(artistDetails).created(savedAlbum.getCreated())
                        .id(savedAlbum.getAlbumId()).lastUpdate(savedAlbum.getLastUpdate()).build();
            }
        }
        return albumDetails;
    }

    /**
     * @param artistId      - Artist id for the albums
     * @param pageNo        - page number
     * @param pageSize      - number of records per page
     * @param sortBy        - Sort by column name
     * @param sortDirection - asc for Ascending, desc for Descending
     * @param filterBy      - FilterBy column value
     * @param filterValue   - Filter by value
     * @return - Returns list of albums if found by given input null otherwise
     */
    public List<AlbumDetails> getAlbums(Long artistId, Integer pageNo, Integer pageSize, String sortBy, String sortDirection, String filterBy, String filterValue) {
        List<AlbumDetails> albumDetailsList = null;
        Album album = new Album();
        Page<Album> savedAlbums;
        List<Album> albums = null;
        Pageable paging;
        Optional<Artist> artist = artistRepo.findById(artistId);
        if (artist.isPresent()) {
            album.setArtist(artist.get());
            if (sortDirection.equalsIgnoreCase("desc"))
                paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
            else
                paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).ascending());

            Example<Album> albumExample = Example.of(album);
            savedAlbums = albumRepository.findAll(albumExample, paging);
            if (!StringUtils.isEmpty(filterBy) && !StringUtils.isEmpty(filterValue) && filterBy.equalsIgnoreCase("genres"))
                albums = savedAlbums.stream().filter(albm -> albm.getGenres().contains(filterValue)).collect(Collectors.toList());
            else
                albums = savedAlbums.getContent();
        }
        if (albums != null) {
            List<AlbumDetails> finalList = new ArrayList<>();
            ArtistDetails artistDetails = ArtistDetails.builder().name(artist.get().getName()).artistId(artist.get().getArtistId())
                    .created(artist.get().getCreated()).lastUpdate(artist.get().getLastUpdate()).build();
            albums.forEach(savedAlbum ->
            {
                AlbumDetails albumDetails = AlbumDetails.builder()
                        .id(savedAlbum.getAlbumId())
                        .title(savedAlbum.getTitle())
                        .yearOfRelease(savedAlbum.getYearOfRelease())
                        .artist(artistDetails)
                        .genres(savedAlbum.getGenres())
                        .created(savedAlbum.getCreated())
                        .lastUpdate(savedAlbum.getLastUpdate()).build();
                finalList.add(albumDetails);
            });
            albumDetailsList = finalList;
        }
        return albumDetailsList;
    }

}
