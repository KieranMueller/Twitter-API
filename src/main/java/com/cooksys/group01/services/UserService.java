package com.cooksys.group01.services;

import com.cooksys.group01.dtos.CredentialsDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.embeddable.Credentials;

import java.util.List;

public interface UserService {

    List<UserRespDTO> getActiveUsers();

    UserRespDTO getUser(String username);

    UserRespDTO createUser(UserReqDTO user);

    List<UserRespDTO> getFollowers(String username);

    List<UserRespDTO> getFollowing(String username);

    List<TweetRespDTO> getMentions(String username);

    void followUser(String username, Credentials credentials);

    void unfollowUser(String username, Credentials credentials);

    List<TweetRespDTO> getFeed(String username);

    UserRespDTO updateUser(String username, UserReqDTO user);

    UserRespDTO deleteUser(String username, CredentialsDTO credentials);

}
