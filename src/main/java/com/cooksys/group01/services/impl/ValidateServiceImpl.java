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
        // Current description: Finished, label must be passed without hashtag character, it won't
        // accept hashtag - doesn't even come through, just throws 404. Appending hashtag to string below. Returns
        // list of hashtags with matching label, boolean returns whether that list is empty or not

        // To-Do: Implement 404 not found if it doesn't exist? Or is 200 status with response body of false
        // sufficient?
        label = "#" + label;
        List<Hashtag> opHashtag = hashtagRepository.findByLabel(label);
        return opHashtag.size() > 0;
    }

    @Override
    public List<HashtagDTO> getAllHashtags() {
        return hashtagMapper.entitiesToDTOs(hashtagRepository.findAll());
    }
}
