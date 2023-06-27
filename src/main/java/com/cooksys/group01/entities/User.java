package com.cooksys.group01.entities;

import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.entities.embeddable.Profile;
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
    private List<Tweet> tweets;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp joined;

    private boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id"))
    private List<Tweet> likedTweets;

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id"))
    private List<Tweet> mentionedTweets;

    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    // testing this out
    public void addMentionedTweet(Tweet tweet) {
        mentionedTweets.add(tweet);
    }
}
