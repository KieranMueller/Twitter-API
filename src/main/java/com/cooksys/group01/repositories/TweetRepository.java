package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    List<Tweet> findByDeletedFalseOrderByPostedDesc();
}
