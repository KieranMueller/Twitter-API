package com.cooksys.group01.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDTO {

    private ProfileDTO profile;

    private CredentialsDTO credentials;

    private Timestamp joined;
}
