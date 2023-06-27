package com.cooksys.group01.dtos;

import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.entities.embeddable.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDTO {

    private ProfileRespDTO profile;

    private CredentialsRespDTO credentials;

    private Timestamp joined;
}
