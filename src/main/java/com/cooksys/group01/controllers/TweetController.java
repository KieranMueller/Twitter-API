package com.cooksys.group01.controllers;

import com.cooksys.group01.dtos.TweetReqDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.services.TweetService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetRespDTO> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}")
    public TweetRespDTO getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @PostMapping
    public TweetRespDTO createTweet(@RequestBody TweetReqDTO tweet) {
        return tweetService.createTweet(tweet);
    }

    @DeleteMapping("/{id}")
    public TweetRespDTO deleteTweetById(@PathVariable Long id) {
        return tweetService.deleteTweetById(id);
    }

}
