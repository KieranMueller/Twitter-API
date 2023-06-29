package com.cooksys.group01.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.mappers.HashtagMapper;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.repositories.UserRepository;
import com.cooksys.group01.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final UserRepository userRepository;

    @Override
    public boolean doesHashtagExist(String label) {
        label = "#" + label;
        Optional<Hashtag> opHashtag = hashtagRepository.findByLabel(label);
        return opHashtag.isPresent();
    }

    @Override
    public List<HashtagDTO> getAllHashtags() {
        return hashtagMapper.entitiesToDTOs(hashtagRepository.findAll());
    }

    @Override
	public boolean doesUserExist(String username) {
		Optional<User> userEx = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        return userEx.isPresent();
    }
}
