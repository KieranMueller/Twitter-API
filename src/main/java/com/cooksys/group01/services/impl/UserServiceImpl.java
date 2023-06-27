package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.User;
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

    @Override
    public List<UserRespDTO> getFollowers(String username) {
        // Issue, to do with User response DTO, figure out how to send username but not password
        // add not found exception with message
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        // Change to 404 not found with 'could not find user' message
        if(opUser.isEmpty()) throw new RuntimeException();
        User user = opUser.get();
        List<User> followers = new ArrayList<>();
        for(User follower : user.getFollowers())
            if(!follower.isDeleted())
                followers.add(follower);
        return userMapper.entitiesToDTOs(followers);
    }

    @Override
    public List<UserRespDTO> getFollowing(String username) {
        // Working fine, just need to fix issue with User DTO (need to return username but not password)
        // and add not found exception with message
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        // Change to 404 not found with 'could not find user' message
        if(opUser.isEmpty()) throw new RuntimeException();
        User user = opUser.get();
        List<User> followings = new ArrayList<>();
        for(User following : user.getFollowing())
            if(!following.isDeleted())
                followings.add(following);
        return userMapper.entitiesToDTOs(followings);
    }
}
