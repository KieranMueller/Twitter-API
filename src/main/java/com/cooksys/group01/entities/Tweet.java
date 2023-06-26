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
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @Transient
    private Date date = new Date();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private final Timestamp posted = new Timestamp(date.getTime());

    private boolean deleted;

    private String content;

    @ManyToMany(mappedBy = "likedTweets")
    private List<User> likes;

    @ManyToMany(mappedBy = "mentionedTweets")
    private List<User> mentionedUsers;

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<Hashtag> hashtags;

    @OneToMany(mappedBy = "inReplyTo")
    private List<Tweet> replyThread;

    @ManyToOne
    @JoinColumn(name = "inReplyTo")
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "repostOf")
    private List<Tweet> repostThread;

    @ManyToOne
    @JoinColumn(name = "repostOf")
    private Tweet repostOf;

}
