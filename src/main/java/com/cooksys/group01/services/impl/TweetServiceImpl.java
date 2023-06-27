package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.TweetReqDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.entities.Hashtag;
import com.cooksys.group01.entities.Tweet;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.exceptions.BadRequestException;
import com.cooksys.group01.exceptions.NotFoundException;
import com.cooksys.group01.mappers.TweetMapper;
import com.cooksys.group01.repositories.HashtagRepository;
import com.cooksys.group01.repositories.TweetRepository;
import com.cooksys.group01.repositories.UserRepository;
import com.cooksys.group01.services.TweetService;
import lombok.RequiredArgsConstructor;
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
        return tweetMapper.entitiesToDTOs(tweetRepository.findByDeletedFalseOrderByPostedDesc());
    }

    @Override
    public TweetRespDTO getTweetById(Long id) {
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty()) throw new NotFoundException("Unable To Find Tweet With ID " + id);
        return tweetMapper.entityToDTO(opTweet.get());
    }

    @Override
    public TweetRespDTO createTweet(TweetReqDTO tweet) {
        if(tweet == null)
            throw new BadRequestException("Tweet Must Contain Content, Username, and Password");
        if(tweet.getContent() == null || tweet.getCredentials() == null)
            throw new BadRequestException("Tweet Must Contain Content, Username, and Password");
        if(tweet.getContent().isBlank())
            throw new BadRequestException("Tweet Must Contain Content");
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(tweet.getCredentials().getUsername());
        if(user.isEmpty())
            throw new BadRequestException("Username '" + tweet.getCredentials().getUsername() + "' Invalid. Please Consider Signing Up!");
        if(!(user.get().getCredentials().getPassword().equals(tweet.getCredentials().getPassword())))
            throw new BadRequestException("Invalid Password!");
        Tweet tweetEntity = tweetMapper.dtoToEntity(tweet);
        tweetEntity.setAuthor(user.get());
        var allowedCharacters = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '{', '}'));
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
        // Temporary, semi-joking solution. Sets the password on the DTO that is returned, does not alter the actual user's password.
        savedTweet.getAuthor().getCredentials().setPassword("TOP SECRET!");
        return savedTweet;
    }

    @Override
    public TweetRespDTO deleteTweetById(Long id) {
        Optional<Tweet> opTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if(opTweet.isEmpty())
            throw new NotFoundException("Unable To Find Tweet With ID " + id);
        opTweet.get().setDeleted(true);
        return tweetMapper.entityToDTO(tweetRepository.save(opTweet.get()));
    }

}
