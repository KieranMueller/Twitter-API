package com.cooksys.group01.services;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.entities.Hashtag;

import java.util.List;

public interface ValidateService {
    boolean doesHashtagExist(String label);

    List<HashtagDTO> getAllHashtags();
}
