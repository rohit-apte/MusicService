package com.lexisnexis.musicdb.musicservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ArtistDetails {
    private Long artistId;
    private String name;
    private Date created;
    private Date lastUpdate;
}
