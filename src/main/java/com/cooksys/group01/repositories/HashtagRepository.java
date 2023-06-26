package com.cooksys.group01.repositories;

import com.cooksys.group01.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    boolean findByLabel(String label);
}
