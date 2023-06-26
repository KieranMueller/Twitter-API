package com.cooksys.group01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, insertable = false)
    private Timestamp firstUsed;

    // Good??
    @CreationTimestamp
    @Column(nullable = false, insertable = false)
    private Timestamp lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    private Set<Tweet> tweetsWithHashtag;
}
