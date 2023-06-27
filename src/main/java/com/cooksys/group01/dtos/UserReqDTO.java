package com.cooksys.group01.dtos;

import com.cooksys.group01.entities.embeddable.Credentials;
import com.cooksys.group01.entities.embeddable.Profile;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {

    // How is this going to work with the mapper and embeddable classes...

    private ProfileReqDTO profile;

    private CredentialsReqDTO credentials;

    // Fine to just leave out creation timestamp?

    // Anything else?? Seems like this is all you need to create user, as long as embeddables working 
}
