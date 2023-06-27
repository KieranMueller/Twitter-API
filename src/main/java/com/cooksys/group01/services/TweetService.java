package com.cooksys.group01.services;

import com.cooksys.group01.dtos.TweetRespDTO;

import java.util.List;

public interface TweetService {

    TweetRespDTO deleteTweetById(Long id);

    List<TweetRespDTO> getAllTweets();

    TweetRespDTO getTweetById(Long id);

}
