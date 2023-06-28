package com.cooksys.group01.services.impl;

import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.Tweet;
import com.cooksys.group01.entities.User;
import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.exceptions.BadRequestException;
import com.cooksys.group01.exceptions.NotAuthorizedException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;

    @Override
    public List<UserRespDTO> getActiveUsers() {
        List<User> userList = userRepository.findAll();
        return userMapper.entitiesToDTOs(userList.stream().filter( user -> !user.isDeleted()).collect(Collectors.toList()));
    }

    @Override
    public List<UserRespDTO> getFollowers(String username) {
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (opUser.isEmpty())
            throw new NotFoundException("Unable To Find Username '" + username + "'");
        User user = opUser.get();
        List<UserRespDTO> followers = new ArrayList<>();
        for (User follower : user.getFollowers())
            if (!follower.isDeleted()) {
                UserRespDTO tempUser = userMapper.entityToDTO(follower);
                tempUser.setUsername(follower.getCredentials().getUsername());
                followers.add(tempUser);
            }
        return followers;
    }

    @Override
    public List<UserRespDTO> getFollowing(String username) {
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (opUser.isEmpty())
            throw new NotFoundException("Unable To Find Username '" + username + "'");
        User user = opUser.get();
        List<UserRespDTO> followings = new ArrayList<>();
        for (User following : user.getFollowing())
            if (!following.isDeleted()) {
                UserRespDTO tempUser = userMapper.entityToDTO(following);
                tempUser.setUsername(following.getCredentials().getUsername());
                followings.add(tempUser);
            }
        return followings;
    }

    @Override
    public List<TweetRespDTO> getMentions(String username) {
        // TODO - Ensure the tweets are in reverse chronological order!
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(opUser.isEmpty())
            throw new NotFoundException("Unable To Find Username '" + username + "'");
        User user = opUser.get();
        List<TweetRespDTO> mentionedTweets = new ArrayList<>();
        for(Tweet t : user.getMentionedTweets())
            if(!t.isDeleted()) {
                TweetRespDTO tempTweet = tweetMapper.entityToDTO(t);
                tempTweet.getAuthor().setUsername(t.getAuthor().getCredentials().getUsername());
                mentionedTweets.add(tempTweet);
            }
        return mentionedTweets;
    }

    @Override
    public List<TweetRespDTO> getFeed(String username) {
        //TODO: Will need to check reply to and repost of tweets etc, Unfinished, still getting null usernames
        Optional<User> opUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(opUser.isEmpty())
            throw new NotFoundException("Unable To Find Username '" + username + "'");
        User user = opUser.get();
        List<TweetRespDTO> tweets = new ArrayList<>();
        for(Tweet userTweet : user.getTweets())
            if(!userTweet.isDeleted()) {
                TweetRespDTO tempTweet = tweetMapper.entityToDTO(userTweet);
                tempTweet.getAuthor().setUsername(userTweet.getAuthor().getCredentials().getUsername());
                tweets.add(tempTweet);
            }
        List<User> followings = user.getFollowing();
        for (User following : followings) {
            for(Tweet tweet : following.getTweets()) {
                if(!tweet.isDeleted()) {
                    TweetRespDTO tempTweet = tweetMapper.entityToDTO(tweet);
                    tempTweet.getAuthor().setUsername(tweet.getAuthor().getCredentials().getUsername());
                    tweets.add(tempTweet);
                }
                for(Tweet reply : tweet.getReplyThread()) {
                    if(reply != null && !reply.isDeleted()) {
                        TweetRespDTO tempReply = tweetMapper.entityToDTO(reply);
                        tempReply.getAuthor().setUsername(reply.getAuthor().getCredentials().getUsername());
                        tweets.add(tempReply);
                    }
                }
                for(Tweet repost : tweet.getRepostThread()) {
                    if(repost != null && !repost.isDeleted()) {
                        TweetRespDTO tempRepost = tweetMapper.entityToDTO(repost);
                        tempRepost.getAuthor().setUsername(repost.getAuthor().getCredentials().getUsername());
                        tweets.add(tempRepost);
                    }
                }
            }
        }
        return tweets;
    }

    @Override
    public UserRespDTO createUser(UserReqDTO user) {
        if(user == null)
            throw new BadRequestException("User Must Include Email, Phone, First Name, Last Name, Password, and Username");
        if(user.getCredentials() == null || user.getProfile() == null)
            throw new BadRequestException("User Must Include Email, Phone, First Name, Last Name, Password, and Username");
        if(user.getProfile().getEmail() == null || user.getProfile().getPhone() == null ||
                user.getProfile().getFirstName() == null || user.getProfile().getLastName() == null
                || user.getCredentials().getPassword() == null || user.getCredentials().getUsername() == null)
            throw new BadRequestException("User Must Include Email, Phone, First Name, Last Name, Password, and Username");
        if(user.getProfile().getEmail().isBlank() || user.getProfile().getPhone().isBlank() ||
                user.getProfile().getFirstName().isBlank() || user.getProfile().getLastName().isBlank()
                || user.getCredentials().getPassword().isBlank() || user.getCredentials().getUsername().isBlank())
            throw new BadRequestException("Email, Phone, First Name, Last Name, Password, and Username Cannot Be Left Blank");
        if(user.getCredentials().getUsername().length() > 30)
            throw new BadRequestException("Username Must Be 30 Characters Or Less");
        if(user.getCredentials().getPassword().length() < 6 || user.getCredentials().getPassword().length() > 30)
            throw new BadRequestException("Password Must Be Between 6-30 Characters In Length");
        var allowedCharacters = new ArrayList<>(List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '{', '}'));
        String username = user.getCredentials().getUsername().toUpperCase();
        for(int i = 0; i < username.length(); i++)
            if(!(allowedCharacters.contains(username.charAt(i))))
                throw new BadRequestException("Username Must Only Contain Letters And/Or Numbers");
        // find all that aren't deleted, or everyone, revisit this?
        // Better way than looping through every user and checking their usernames?
        for (User tempUser : userRepository.findAll()) {
            if (tempUser.getCredentials().getPassword().equals(user.getCredentials().getPassword())
                    && tempUser.getCredentials().getUsername().equals(user.getCredentials().getUsername())) {
                Optional<User> deletedUser = userRepository.findByCredentialsUsernameAndDeletedTrue(user.getCredentials().getUsername());
                if (deletedUser.isPresent()) {
                    deletedUser.get().setDeleted(false);
                    return userMapper.entityToDTO(userRepository.save(deletedUser.get()));
                }
            }
            if (tempUser.getCredentials().getUsername().equals(user.getCredentials().getUsername()))
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
    
    public UserRespDTO getUser(String username) {
    	User userRet = getUserByUsername(username);
    	return userMapper.entityToDTO(userRet);
    }

}
