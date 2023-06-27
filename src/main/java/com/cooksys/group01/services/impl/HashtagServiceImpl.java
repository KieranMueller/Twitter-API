package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public HashtagDTO getRandomHashtag() {
        List<Hashtag> tags = hashtagRepository.findAll();

        return null;
    }
}
