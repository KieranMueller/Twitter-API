package com.cooksys.group01.controllers;


import java.util.List;
import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDTO> getAllTags(){
    	return hashtagService.getAllTags();
    }

    @GetMapping("/randomTag")
    public HashtagDTO getRandomHashtag() {
        return hashtagService.getRandomHashtag();
    }
}
