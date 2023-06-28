package com.cooksys.group01.controllers;


import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public HashtagDTO getRandomHashtag() {
        return hashtagService.getRandomHashtag();
    }

    @GetMapping("/{label}")
    public List<TweetRespDTO> getTweetsByTag(@PathVariable String label) {
        return hashtagService.getTweetsByTag(label);
    }
}
