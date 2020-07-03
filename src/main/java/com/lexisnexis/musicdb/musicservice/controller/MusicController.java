package com.lexisnexis.musicdb.musicservice.controller;

import com.lexisnexis.musicdb.musicservice.dto.Album;
import com.lexisnexis.musicdb.musicservice.dto.Artist;
import com.lexisnexis.musicdb.musicservice.model.AlbumDetails;
import com.lexisnexis.musicdb.musicservice.model.ArtistDetails;
import com.lexisnexis.musicdb.musicservice.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class MusicController {

    @Autowired
    private MusicService musicService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Alive");
    }

    @GetMapping("/{artistId}")
    public ResponseEntity getArtist(@PathVariable Long artistId) {
        ArtistDetails artistDetails = musicService.getArtist(artistId);
        if (artistDetails != null) {
            return ResponseEntity.ok().body(artistDetails);
        } else {
            return new ResponseEntity<>("No Artist Found for given input", HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping()
    public ResponseEntity getAllArtists(@RequestParam(defaultValue = "1") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "name") String sortBy,
                                        @RequestParam(defaultValue = "asc") String sortDirection,
                                        @RequestParam(required = false) String filterBy,
                                        @RequestParam(required = false) String filterValue) {
        List<ArtistDetails> artistDetailsList = musicService.getAllArtists(pageNo, pageSize, sortBy, sortDirection, filterBy, filterValue);
        if (!CollectionUtils.isEmpty(artistDetailsList)) {
            return ResponseEntity.ok().body(artistDetailsList);
        } else {
            return new ResponseEntity<>("No Artist Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addArtist(@RequestBody Artist artist) {
        ArtistDetails artistDetails = musicService.addArtist(artist);
        if (artistDetails != null) {
            return new ResponseEntity<>(artistDetails, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Unable to add Artist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{artistId}")
    public ResponseEntity updateArtist(@RequestBody Artist artist, @PathVariable Long artistId) {
        ArtistDetails artistDetails = musicService.updateArtist(artist, artistId);
        if (artistDetails != null) {
            return new ResponseEntity<>(artistDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Artist found to update", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{artistId}/albums")
    public ResponseEntity addAlbum(@RequestBody Album album, @PathVariable Long artistId) {

        AlbumDetails albumDetails = musicService.addAlbum(album, artistId);
        if (albumDetails != null) {
            return new ResponseEntity<>(albumDetails, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Unable to add Artist", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/{artistId}/albums")
    public ResponseEntity getAlbums(@PathVariable Long artistId,
                                    @RequestParam(defaultValue = "1") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "albumId") String sortBy,
                                    @RequestParam(defaultValue = "asc") String sortDirection,
                                    @RequestParam(required = false) String filterBy,
                                    @RequestParam(required = false) String filterValue) {
        List<AlbumDetails> albumDetailsList = musicService.getAlbums(artistId, pageNo, pageSize, sortBy, sortDirection, filterBy, filterValue);
        if (CollectionUtils.isEmpty(albumDetailsList)) {
            return new ResponseEntity<>("No Artist Found for given input", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().body(albumDetailsList);
        }

    }

}
