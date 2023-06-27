package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.exceptions.BadRequestException;
import com.cooksys.group01.exceptions.NotAuthorizedException;
import com.cooksys.group01.exceptions.NotFoundException;
import com.cooksys.group01.mappers.UserMapper;
import com.cooksys.group01.repositories.UserRepository;
import com.cooksys.group01.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserRespDTO> getActiveUsers() {
        List<User> userList = userRepository.findAll();
        return userMapper.entitiesToDTOs(userList.stream().filter( user -> !user.isDeleted()).collect(Collectors.toList()));
    }

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

    @Override
    public UserRespDTO createUser(UserReqDTO user) {
        // Unfinished, still failing a couple tests
        // There's gotta be a better way to do this... Can I make req DTO fields required with annotations? Test it after
        if(user == null) throw new BadRequestException("User Must Include Email, Phone, First Name, Last Name, Password, and Username");
        if(user.getCredentials() == null || user.getProfile() == null) throw new BadRequestException("User Must Include Email, Phone, First Name, Last Name, Password, and Username");
        if(user.getProfile().getEmail() == null || user.getProfile().getPhone() == null ||
                user.getProfile().getFirstName() == null || user.getProfile().getLastName() == null
                || user.getCredentials().getPassword() == null || user.getCredentials().getUsername() == null)
            throw new BadRequestException("User Must Include Email, Phone, First Name, Last Name, Password, and Username");
        if(user.getProfile().getEmail().isBlank() || user.getProfile().getPhone().isBlank() ||
                user.getProfile().getFirstName().isBlank() || user.getProfile().getLastName().isBlank()
                || user.getCredentials().getPassword().isBlank() || user.getCredentials().getUsername().isBlank())
            throw new BadRequestException("Email, Phone, First Name, Last Name, Password, and Username Cannot Be Left Blank");

        // find all that aren't deleted, or everyone, revisit this?
        // Better way than looping through every user and checking their usernames?
        for(User tempUser : userRepository.findAll()) {
            if(tempUser.getCredentials().getPassword().equals(user.getCredentials().getPassword())
                && tempUser.getCredentials().getUsername().equals(user.getCredentials().getUsername())) {
                Optional<User> deletedUser = userRepository.findByCredentialsUsernameAndDeletedTrue(user.getCredentials().getUsername());
                if(deletedUser.isPresent()) {
                    deletedUser.get().setDeleted(false);
                    return userMapper.entityToDTO(userRepository.save(deletedUser.get()));
                }
            }
            if(tempUser.getCredentials().getUsername().equals(user.getCredentials().getUsername()))
                throw new BadRequestException("Sorry, Username " + user.getCredentials().getUsername() + " Already Exists!");
        }
        return userMapper.entityToDTO(userRepository.save(userMapper.dtoToEntity(user)));
    }

    @Override
    public void followUser(String username, Credentials credentials) {
        User toBeFollowed = getUserByUsername(username);
        User follower = authorizeCredentials(credentials);
        if (!isActive(toBeFollowed))
            throw new BadRequestException("User + " + username + " not found!");
        if (follower.getFollowing().contains(toBeFollowed))
            throw new BadRequestException("Already following " + username +"!");
        follower.addFollowing(toBeFollowed);
        userRepository.saveAndFlush(follower);
        userRepository.saveAndFlush(toBeFollowed);
    }

    @Override
    public void unfollowUser(String username, Credentials credentials) {
        User toBeUnfollowed = getUserByUsername(username);
        User follower = authorizeCredentials(credentials);
        if (!isActive(toBeUnfollowed))
            throw new BadRequestException("User " + username + " not found!");
        if (!follower.getFollowing().contains(toBeUnfollowed))
            throw new BadRequestException("Currently not following " + username +"!");
        follower.removeFollowing(toBeUnfollowed);
        userRepository.saveAndFlush(follower);
        userRepository.saveAndFlush(toBeUnfollowed);
    }

    // HELPER FUNCTIONS

    private boolean isActive(User user) {
        return user != null && !user.isDeleted();
    }

    private User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByCredentialsUsername(username);
        return userOptional.orElse(null);
    }

    private User authorizeCredentials(Credentials credentials) {
        Optional<User> userOptional = userRepository.findOneByCredentials(credentials);
        if (userOptional.isEmpty() || userOptional.get().isDeleted())
            throw new NotAuthorizedException("Not authorized: Bad credentials!");
        return userOptional.get();
    }
}
