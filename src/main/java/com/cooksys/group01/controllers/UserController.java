package com.cooksys.group01.controllers;

import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
<<<<<<< HEAD
<<<<<<< HEAD
import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.services.UserService;
=======
import com.cooksys.group01.services.UserService;
import lombok.RequiredArgsConstructor;
>>>>>>> 96fbe32832b69cae39f7bd613226f2b69eabbf80
=======
import com.cooksys.group01.services.UserService;
import lombok.RequiredArgsConstructor;
>>>>>>> 96fbe32832b69cae39f7bd613226f2b69eabbf80
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserRespDTO> getUsers() {
        return userService.getActiveUsers();
    }
  
    @GetMapping("/@{username}")
    public UserRespDTO getUser(@PathVariable String username) {
    	return userService.getUserByUsername(username);
    }
   

    @GetMapping("/@{username}/followers")
    public List<UserRespDTO> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @GetMapping("/@{username}/following")
    public List<UserRespDTO> getFollowing(@PathVariable String username) {
        return userService.getFollowing(username);
    }

    @GetMapping("/@{username}/mentions")
    public List<TweetRespDTO> getMentions(@PathVariable String username) {
        return userService.getMentions(username);
    }

    @GetMapping("/@{username}/feed")
    public List<TweetRespDTO> getFeed(@PathVariable String username) {
        return userService.getFeed(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO createUser(@RequestBody UserReqDTO user) {
        return userService.createUser(user);
    }

    @PostMapping("/@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody Credentials credentials) {
        userService.followUser(username, credentials);
    }

    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody Credentials credentials) {
        userService.unfollowUser(username, credentials);
    }

}
