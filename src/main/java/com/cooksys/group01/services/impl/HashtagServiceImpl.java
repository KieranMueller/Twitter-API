package com.cooksys.group01.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.mappers.HashtagMapper;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	
	
	@Override
	public List<HashtagDTO> getAllTags() {
		return hashtagMapper.entitiesToDTOs(hashtagRepository.findAll());
	}
}
