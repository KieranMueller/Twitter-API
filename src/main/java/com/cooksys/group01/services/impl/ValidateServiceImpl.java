package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.mappers.HashtagMapper;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public boolean doesHashtagExist(String label) {
        // Throw 404 if not doesn't exist or leave it as is? (200 OK but body returns false)
        label = "#" + label;
        List<Hashtag> opHashtag = hashtagRepository.findAllByLabel(label);
        return opHashtag.size() > 0;
    }

    @Override
    public List<HashtagDTO> getAllHashtags() {
        return hashtagMapper.entitiesToDTOs(hashtagRepository.findAll());
    }
}
