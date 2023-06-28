package com.cooksys.group01.services;

import com.cooksys.group01.dtos.CredentialsDTO;
import com.cooksys.group01.dtos.TweetReqDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TweetService {

    TweetRespDTO deleteTweetById(Long id);

    List<TweetRespDTO> getAllTweets();

    TweetRespDTO getTweetById(Long id);

    TweetRespDTO createTweet(TweetReqDTO tweet);

    ResponseEntity<HttpStatus> likeTweet(Long id, CredentialsDTO credentials);
}
