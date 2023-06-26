package com.cooksys.group01.entity;

import com.cooksys.group01.entity.embeddable.Credentials;
import com.cooksys.group01.entity.embeddable.Profile;
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
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(nullable = false))
    })
    private Profile profile;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(nullable = false, unique = true)),
            @AttributeOverride(name = "password", column = @Column(nullable = false))
    })
    private Credentials credentials;

    @OneToMany(mappedBy = "author")
    private Set<Tweet> tweets;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, insertable = false)
    private Timestamp joined;

    private boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id"))
    private Set<Tweet> likedTweets;

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id"))
    private Set<Tweet> mentionedTweets;

    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> following;
}
