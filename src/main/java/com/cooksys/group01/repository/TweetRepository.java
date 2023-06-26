package com.cooksys.group01.repository;

import com.cooksys.group01.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
