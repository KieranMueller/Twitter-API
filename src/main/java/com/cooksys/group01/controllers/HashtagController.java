package com.cooksys.group01.controllers;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping("/randomTag")
    public HashtagDTO getRandomHashtag() {
        return hashtagService.getRandomHashtag();
    }

}
