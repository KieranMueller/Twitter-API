package com.cooksys.group01.services;

import com.cooksys.group01.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TweetService {

    TweetRespDTO deleteTweetById(Long id);

    List<TweetRespDTO> getAllTweets();

    TweetRespDTO getTweetById(Long id);

    List<UserRespDTO> getMentionsById(Long id);

    List<TweetRespDTO> getRepliesById(Long id);

    List<TweetRespDTO> getRepostsById(Long id);

    List<UserRespDTO> getUsersByLikedTweet(Long id);

    List<HashtagDTO> getTagsByTweetId(Long id);

    ContextRespDTO getContextById(Long id);

    TweetRespDTO createTweet(TweetReqDTO tweet);

    ResponseEntity<HttpStatus> likeTweet(Long id, CredentialsDTO credentials);

	TweetRespDTO repostById(Long id, CredentialsDTO credentials);

    TweetRespDTO replyToTweet(Long id, TweetReqDTO tweet);

}
