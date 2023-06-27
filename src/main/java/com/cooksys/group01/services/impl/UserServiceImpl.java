package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.Tweet;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.exceptions.NotFoundException;
import com.cooksys.group01.mappers.TweetMapper;
import com.cooksys.group01.mappers.UserMapper;
import com.cooksys.group01.repositories.UserRepository;
import com.cooksys.group01.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;

    @Override
    public List<UserRespDTO> getFollowers(String username) {
        // Working fine, just need to fix issue with User DTO (need to return username but not password)
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(opUser.isEmpty()) throw new NotFoundException("Unable To Find User With Username " + username);
        List<User> followers = new ArrayList<>();
        for(User follower : opUser.get().getFollowers())
            if(!follower.isDeleted())
                followers.add(follower);
        return userMapper.entitiesToDTOs(followers);
    }

    @Override
    public List<UserRespDTO> getFollowing(String username) {
        // Working fine, just need to fix issue with User DTO (need to return username but not password)
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(opUser.isEmpty()) throw new NotFoundException("Unable To Find User With Username " + username);
        List<User> followings = new ArrayList<>();
        for(User following : opUser.get().getFollowing())
            if(!following.isDeleted())
                followings.add(following);
        return userMapper.entitiesToDTOs(followings);
    }

    @Override
    public List<TweetRespDTO> getMentions(String username) {
        // Hmmm, a user is considered mentioned if their username is included in the content
        // of a tweet prefixed with @ symbol, don't think I set up this logic, seeder doesn't seem to contain
        // any tweets that even mention users, maybe add one myself to the seeder for testing
        return null;
    }
}
