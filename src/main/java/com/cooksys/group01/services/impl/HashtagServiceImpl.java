package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.entities.Tweet;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.exceptions.NotFoundException;
import com.cooksys.group01.mappers.HashtagMapper;
import com.cooksys.group01.mappers.TweetMapper;
import com.cooksys.group01.mappers.UserMapper;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.repositories.TweetRepository;
import com.cooksys.group01.repositories.UserRepository;
import com.cooksys.group01.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;

    @Override
    public List<HashtagDTO> getAllTags() {
        List<Hashtag> hashList = hashtagRepository.findAll();
        for (Hashtag hash: hashList) {
            hash.setLabel(hash.getLabel().replace("#", ""));
        }
        return hashtagMapper.entitiesToDTOs(hashList);
    }

    @Override
    public HashtagDTO getRandomHashtag() {
        List<Hashtag> tags = hashtagRepository.findAll();
        return hashtagMapper.entityToDTO(tags.get(new Random().nextInt(tags.size())));
    }
    @Override
    public List<TweetRespDTO> getTweetsByTag(String label) {

        Optional<Hashtag> opHashtag = hashtagRepository.findByLabel("#" + label);
        if(opHashtag.isEmpty()) {
            throw new NotFoundException("No Hashtag with the label #" + label + " Found!");
        }

        List<Tweet> tweets = tweetRepository.findByDeletedFalseOrderByPostedDesc();
        List<TweetRespDTO> tweetsWithTag = new ArrayList<>();

        for(Tweet tweet : tweets) {
            String[] content = tweet.getContent().split(" ");
            Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(tweet.getAuthor().getCredentials().getUsername());
            if(opUser.isEmpty()) {
                break;
            }

            User user = opUser.get();
            for(String word : content) {
                if(word.startsWith("#") && word.substring(1).equals(label)) {
                    TweetRespDTO tweetRespDTO = tweetMapper.entityToDTO(tweet);
                    tweetRespDTO.getAuthor().setUsername(user.getCredentials().getUsername());
                    tweetsWithTag.add(tweetRespDTO);
                }
            }
        }

        if(tweetsWithTag.isEmpty()) {
            throw new NotFoundException("No Tweets With The Hashtag #" + label + " Exists!");
        }
        return tweetsWithTag;
    }

}
