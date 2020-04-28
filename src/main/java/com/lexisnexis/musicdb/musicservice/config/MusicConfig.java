package com.lexisnexis.musicdb.musicservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexisnexis.musicdb.musicservice.dto.Album;
import com.lexisnexis.musicdb.musicservice.dto.Artist;
import com.lexisnexis.musicdb.musicservice.repository.ArtistRepository;
import com.lexisnexis.musicdb.musicservice.service.MusicServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MusicConfig {

    private static Logger LOG = LoggerFactory.getLogger(MusicConfig.class);
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MusicServiceImpl musicService;

    @Bean
    public List<Artist> saveArtistDetails() {
        List<Artist> stocks = new ArrayList<>();
        try {
            String data = Files.readString(Path.of("D:\\Rohit\\WorkSpace\\IdeaProjects\\MusicService\\src\\main\\resources\\ArtistData.json"));
            ObjectMapper mapper = new ObjectMapper();
            List<Artist> artistData = mapper.readValue(data, new TypeReference<>() {
            });
            artistRepository.saveAll(artistData);
            saveAlbumDetails();
        } catch (FileNotFoundException e) {
            LOG.error("Stock Data File not found at given path.");
        } catch (IOException e) {
            LOG.error("Unable to load data from file");
        }
        return stocks;
    }


    public void saveAlbumDetails() {
        List<Artist> stocks = new ArrayList<>();
        try {
            String data = Files.readString(Path.of("D:\\Rohit\\WorkSpace\\IdeaProjects\\MusicService\\src\\main\\resources\\AlbumData.json"));
            ObjectMapper mapper = new ObjectMapper();
            List<Album> albums = mapper.readValue(data, new TypeReference<>() {
            });
            for (int i = 1; i <= 18; i++) {
                int artistId = i;
                albums.forEach(album -> {
                    musicService.addAlbum(album, (long) artistId);
                });
            }
        } catch (FileNotFoundException e) {
            LOG.error("Stock Data File not found at given path.");
        } catch (IOException e) {
            LOG.error("Unable to load data from file");
        }
    }
}
