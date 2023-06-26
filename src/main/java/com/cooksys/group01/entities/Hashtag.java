package com.cooksys.group01.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @Transient
    private Date date = new Date();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private final Timestamp firstUsed = new Timestamp(date.getTime());

    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    private List<Tweet> tweetsWithHashtag;
}
