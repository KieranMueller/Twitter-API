package com.cooksys.group01.repository;

import com.cooksys.group01.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    boolean findByLabel(String label);
}
