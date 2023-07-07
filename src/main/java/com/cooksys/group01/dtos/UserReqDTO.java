package com.cooksys.group01.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {

    private ProfileDTO profile;

    private CredentialsDTO credentials;

}
