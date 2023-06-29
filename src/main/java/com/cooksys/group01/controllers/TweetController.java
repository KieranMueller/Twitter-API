package com.cooksys.group01.controllers;

import com.cooksys.group01.dtos.CredentialsDTO;
import com.cooksys.group01.dtos.TweetReqDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetRespDTO> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("{id}")
    public TweetRespDTO getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @GetMapping("{id}/replies")
    public List<TweetRespDTO> getRepliesById(@PathVariable Long id) {
    	return tweetService.getRepliesById(id);
    }

    @GetMapping("{id}/mentions")
    public List<UserRespDTO> getMentionsById(@PathVariable Long id) {
        return tweetService.getMentionsById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetRespDTO createTweet(@RequestBody TweetReqDTO tweet) {
        return tweetService.createTweet(tweet);
    }

    @PostMapping("{id}/like")
    public ResponseEntity<HttpStatus> likeTweet(@PathVariable Long id, @RequestBody CredentialsDTO credentials) {
        return tweetService.likeTweet(id, credentials);
    }

    @PostMapping("{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetRespDTO replyToTweet(@PathVariable Long id, @RequestBody TweetReqDTO tweet) {
        return tweetService.replyToTweet(id, tweet);
    }

    @PostMapping("{id}/repost")
    public TweetRespDTO repostById(@PathVariable Long id, @RequestBody CredentialsDTO credentials) {
    	return tweetService.repostById(id, credentials);
    }

    @DeleteMapping("{id}")
    public TweetRespDTO deleteTweetById(@PathVariable Long id) {
        return tweetService.deleteTweetById(id);
    }

}
