package com.cooksys.group01.dto;

import com.cooksys.group01.entity.embeddable.Profile;

import java.sql.Timestamp;

public class UserRespDTO {

    private String username;
    private Profile profile;
    private Timestamp joined;
}
