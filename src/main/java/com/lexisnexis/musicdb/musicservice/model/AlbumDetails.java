package com.lexisnexis.musicdb.musicservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class AlbumDetails {
    private Long id;
    private String title;
    private Integer yearOfRelease;
    private ArtistDetails artist;
    private List<String> genres;
    private Date created;
    private Date lastUpdate;
}
