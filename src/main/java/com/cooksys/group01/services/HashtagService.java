package com.cooksys.group01.services;

import java.util.List;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.dtos.TweetRespDTO;

public interface HashtagService {
  
    List<HashtagDTO> getAllTags();
  
    HashtagDTO getRandomHashtag();

    List<TweetRespDTO> getTweetsByTag(String label);

}
