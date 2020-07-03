package com.lexisnexis.musicdb.musicservice.service;

import com.lexisnexis.musicdb.musicservice.dto.Album;
import com.lexisnexis.musicdb.musicservice.dto.Artist;
import com.lexisnexis.musicdb.musicservice.repository.AlbumRepository;
import com.lexisnexis.musicdb.musicservice.repository.ArtistRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MusickServiceImplTest {

    @InjectMocks
    private MusicServiceImpl musicService;

    @Mock
    private ArtistRepository artistRepo;

    @Mock
    private AlbumRepository albumRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addArtistTest() {
        Artist artist = Artist.builder().artistId(1L).name("Eminem").created(new Date()).lastUpdate(new Date()).build();
        Mockito.when(artistRepo.save(Mockito.any())).thenReturn(artist);
        assertEquals(1L, musicService.addArtist(artist).getArtistId());
    }

    @Test
    public void addAlbumTest() {
        String[] genres = {"Pop", "Rock"};
        Artist artist = Artist.builder().artistId(1L).name("Enrique").build();
        Album album = Album.builder().albumId(5L).title("Album1").genres(Arrays.asList(genres)).created(new Date()).lastUpdate(new Date()).build();
        Mockito.when(albumRepo.save(Mockito.any())).thenReturn(album);
        Mockito.when(artistRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artist));
        assertEquals("Album1", musicService.addAlbum(album, 1L).getTitle());
    }

    @Test
    public void getArtistTest() {
        Artist artist = Artist.builder().artistId(1L).name("Enrique").build();
        Mockito.when(artistRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artist));
        assertEquals("Enrique", musicService.getArtist(1L).getName());
    }


    @Test
    public void getAllArtistsTest() {
        Artist artist = Artist.builder().artistId(1L).name("Enrique").build();
        List<Artist> artists = new ArrayList<>(1);
        artists.add(artist);
        Page<Artist> artistPage = new PageImpl<Artist>(artists);
        Mockito.when(artistRepo.findAll(Mockito.any(), (Pageable)Mockito.any())).thenReturn(artistPage);
        assertNotNull(musicService.getAllArtists(1, 5, "name", "asc", "name",  "Enri"));
    }


    @Test
    public void getAlbumsTest() {
        Artist artist = Artist.builder().artistId(1L).name("Enrique").build();
        String[] genres = {"Pop", "Rock"};
        Mockito.when(artistRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artist));

        Album album = Album.builder().albumId(5L).title("Album1").genres(Arrays.asList(genres)).artist(artist).created(new Date()).lastUpdate(new Date()).build();
        List<Album> albums = new ArrayList<>();
        albums.add(album);
        Page<Album> albumPage = new PageImpl<Album>(albums);
        Mockito.when(albumRepo.findAll(Mockito.any(), (Pageable) Mockito.any())).thenReturn(albumPage);
        assertNotNull(musicService.getAlbums(1L, 1, 5, "title", "asc", "genres","Pop"));
    }



    @Test
    public void updateArtistTest() {
        Artist artist = Artist.builder().artistId(1L).name("Enrique").build();
        Mockito.when(artistRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artist));
        Mockito.when(artistRepo.save(Mockito.any())).thenReturn(artist);
        assertEquals("Enrique", musicService.updateArtist(artist,1L).getName());
    }
}
