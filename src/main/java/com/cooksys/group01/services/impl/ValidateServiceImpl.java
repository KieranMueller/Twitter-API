package com.cooksys.group01.services.impl;

import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;

    @Override
    public boolean doesHashtagExist(String label) {
        // Have not tested yet - Kieran
        return hashtagRepository.findByLabel(label);
    }
}
