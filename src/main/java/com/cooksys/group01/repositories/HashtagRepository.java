package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    List<Hashtag> findAll();

    Optional<Hashtag> findByLabel(String label);

    @Query("select label from Hashtag")
    List<String> getAllLabels();

}
