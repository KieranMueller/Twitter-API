package com.cooksys.group01.dto;

import com.cooksys.group01.entity.embeddable.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDTO {

    private String username;

    private Profile profile;

    private Timestamp joined;
}
