package com.cooksys.group01.dtos;

import com.cooksys.group01.entities.embeddable.Profile;
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
