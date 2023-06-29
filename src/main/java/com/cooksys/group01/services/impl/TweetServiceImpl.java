package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.CredentialsDTO;
import com.cooksys.group01.dtos.TweetReqDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.entities.Tweet;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.exceptions.BadRequestException;
import com.cooksys.group01.exceptions.NotAuthorizedException;
import com.cooksys.group01.exceptions.NotFoundException;
import com.cooksys.group01.mappers.TweetMapper;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.repositories.TweetRepository;
import com.cooksys.group01.repositories.UserRepository;
import com.cooksys.group01.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetRespDTO> getAllTweets() {
        List<Tweet> allTweets = tweetRepository.findByDeletedFalseOrderByPostedDesc();
        List<TweetRespDTO> allTweetsDTO = new ArrayList<>();
        for(Tweet tweet : allTweets) {
            TweetRespDTO tweetDTO = tweetMapper.entityToDTO(tweet);
            tweetDTO.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
            allTweetsDTO.add(tweetDTO);
        }
        return allTweetsDTO;
    }

    @Override
    public TweetRespDTO getTweetById(Long id) {
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty())
            throw new NotFoundException("Unable To Find Tweet With ID " + id);
        Tweet tweet = opTweet.get();
        TweetRespDTO tweetDTO = tweetMapper.entityToDTO(tweet);
        tweetDTO.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
        return tweetDTO;
    }

    @Override
    public TweetRespDTO createTweet(TweetReqDTO tweet) {
        if(tweet == null)
            throw new BadRequestException("Tweet Must Contain Content, Username, and Password");
        if(tweet.getContent() == null || tweet.getCredentials() == null)
            throw new BadRequestException("Tweet Must Contain Content, Username, and Password");
        if(tweet.getContent().isBlank())
            throw new BadRequestException("Tweet Must Contain Content");
        Optional<User> opUser = userRepository.
                findByCredentialsUsernameAndCredentialsPasswordAndDeletedFalse(
                        tweet.getCredentials().getUsername(), tweet.getCredentials().getPassword());
        if(opUser.isEmpty())
            throw new BadRequestException("Username/Password Combination Invalid!");
        User user = opUser.get();
        Tweet tweetEntity = tweetMapper.dtoToEntity(tweet);
        tweetEntity.setAuthor(user);
        var allowedCharacters = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1',
                '2', '3', '4', '5', '6', '7', '8', '9', '{', '}'));
        String[] arr = tweet.getContent().split(" ");
        Set<String> usernamesMentioned = new HashSet<>();
        Set<String> hashtags = new HashSet<>();
        for(String word : arr) {
            String toAdd = "";
            int i = 0;
            if(word.startsWith("@")) {
                i++;
                while(i < word.length() && allowedCharacters.contains(word.toUpperCase().charAt(i))) {
                    toAdd += word.charAt(i);
                    i++;
                }
                usernamesMentioned.add(toAdd);
            } else if(word.startsWith("#")) {
                toAdd += "#";
                i++;
                while(i < word.length() && allowedCharacters.contains(word.toUpperCase().charAt(i))) {
                    toAdd += word.charAt(i);
                    i++;
                }
                hashtags.add(toAdd);
            }
        }
        List<Hashtag> savedTags = new ArrayList<>();
        List<String> existingLabels = new ArrayList<>(hashtagRepository.getAllLabels());
        for(String hashtag : hashtags) {
            if(!existingLabels.contains(hashtag))
                savedTags.add(hashtagRepository.save(new Hashtag(null, hashtag, null, null, null)));
            else {
                Optional<Hashtag> tagToUpdate = hashtagRepository.findByLabel(hashtag);
                if(tagToUpdate.isPresent()) {
                    Date date = new Date();
                    tagToUpdate.get().setLastUsed(new Timestamp(date.getTime()));
                    hashtagRepository.save(tagToUpdate.get());
                }
            }
        }
        for(String possibleUser : usernamesMentioned) {
            Optional<User> foundUser = userRepository.findByCredentialsUsernameAndDeletedFalse(possibleUser);
            foundUser.ifPresent(value -> value.addMentionedTweet(tweetEntity));
        }
        tweetEntity.setHashtags(savedTags);
        TweetRespDTO savedTweet = tweetMapper.entityToDTO(tweetRepository.save(tweetEntity));
        savedTweet.getAuthor().setUsername(user.getCredentials().getUsername());
        return savedTweet;
    }

    @Override
    public ResponseEntity<HttpStatus> likeTweet(Long id, CredentialsDTO credentials) {
        if(credentials.getPassword() == null || credentials.getUsername() == null)
            throw new NotAuthorizedException("Unable To Verify Credentials");
        Optional<User> opUser = userRepository.
                findByCredentialsUsernameAndCredentialsPasswordAndDeletedFalse(credentials.getUsername(), credentials.getPassword());
        if(opUser.isEmpty())
            throw new NotAuthorizedException("Unable To Verify Credentials");
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty())
            throw new NotFoundException("Unable To Find Tweet With ID " + id);
        Tweet tweet = opTweet.get();
        User user = opUser.get();
        if(!user.getLikedTweets().contains(tweet))
            user.addLikedTweet(tweet);
        userRepository.saveAndFlush(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public TweetRespDTO deleteTweetById(Long id) {
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty())
            throw new NotFoundException("Unable To Find Tweet With ID " + id);
        Tweet tweet = opTweet.get();
        TweetRespDTO tweetDTO = tweetMapper.entityToDTO(tweet);
        tweet.setDeleted(true);
        tweetRepository.save(tweet);
        tweetDTO.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
        return tweetDTO;
    }

}
