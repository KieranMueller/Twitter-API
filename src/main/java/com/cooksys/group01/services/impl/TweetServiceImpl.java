package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.entities.Tweet;
import com.cooksys.group01.exceptions.NotFoundException;
import com.cooksys.group01.mappers.TweetMapper;
import com.cooksys.group01.repositories.TweetRepository;
import com.cooksys.group01.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetRespDTO> getAllTweets() {
        return tweetMapper.entitiesToDTOs(tweetRepository.findByDeletedFalseOrderByPostedDesc());
    }

    @Override
    public TweetRespDTO getTweetById(Long id) {
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty()) throw new NotFoundException("Unable To Find Tweet With ID " + id);
        return tweetMapper.entityToDTO(opTweet.get());
    }

    @Override
    public TweetRespDTO deleteTweetById(Long id) {
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty()) throw new NotFoundException("Unable To Find Tweet With ID " + id);
        opTweet.get().setDeleted(true);
        return tweetMapper.entityToDTO(tweetRepository.save(opTweet.get()));
    }

}
