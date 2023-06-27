package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    List<Tweet> findByDeletedFalseOrderByPostedDesc();
}
