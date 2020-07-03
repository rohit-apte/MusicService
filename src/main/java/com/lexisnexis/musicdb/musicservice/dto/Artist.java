package com.lexisnexis.musicdb.musicservice.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Artist {
    @Id
    private Long artistId;

    @Column
    private String name;

    @Column(updatable = false)
    @CreationTimestamp
    private Date created;

    @Column
    @UpdateTimestamp
    private Date lastUpdate;

}
