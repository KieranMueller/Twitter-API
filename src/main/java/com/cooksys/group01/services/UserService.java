package com.cooksys.group01.services;

import com.cooksys.group01.dtos.UserRespDTO;

import java.util.List;

public interface UserService {

    List<UserRespDTO> getFollowers(String username);

    List<UserRespDTO> getFollowing(String username);
}
