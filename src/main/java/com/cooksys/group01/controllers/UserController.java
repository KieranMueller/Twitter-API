package com.cooksys.group01.controllers;

import com.cooksys.group01.dtos.CredentialsDTO;
import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserRespDTO> getUsers() {
        return userService.getActiveUsers();
    }
  
    @GetMapping("@{username}")
    public UserRespDTO getUser(@PathVariable String username) {
    	return userService.getUser(username);
    }

    @GetMapping("@{username}/followers")
    public List<UserRespDTO> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @GetMapping("@{username}/following")
    public List<UserRespDTO> getFollowing(@PathVariable String username) {
        return userService.getFollowing(username);
    }

    @GetMapping("@{username}/mentions")
    public List<TweetRespDTO> getMentions(@PathVariable String username) {
        return userService.getMentions(username);
    }

    @GetMapping("@{username}/feed")
    public List<TweetRespDTO> getFeed(@PathVariable String username) {
        return userService.getFeed(username);
    }

    @PatchMapping("@{username}")
    public UserRespDTO updateUser(@PathVariable String username, @RequestBody UserReqDTO user) {
        return userService.updateUser(username, user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO createUser(@RequestBody UserReqDTO user) {
        return userService.createUser(user);
    }
    
    @GetMapping("@{username}/tweets")
    public List<TweetRespDTO> getUserTweets(@PathVariable String username){
    	return userService.getUserTweets(username);
    }

    @PostMapping("@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody Credentials credentials) {
        userService.followUser(username, credentials);
    }

    @PostMapping("@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody Credentials credentials) {
        userService.unfollowUser(username, credentials);
    }

    @DeleteMapping("@{username}")
    public UserRespDTO deleteUser(@PathVariable String username, @RequestBody CredentialsDTO credentials) {
        return userService.deleteUser(username, credentials);
    }

}
