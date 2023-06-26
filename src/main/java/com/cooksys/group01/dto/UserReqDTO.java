package com.cooksys.group01.dto;

import com.cooksys.group01.entity.embeddable.Credentials;
import com.cooksys.group01.entity.embeddable.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {

    // How is this going to work with the mapper and embeddable classes...

    private Profile profile;

    private Credentials credentials;

    // Fine to just leave out creation timestamp?

    // Anything else?? Seems like this is all you need to create user, as long as embeddables working 
}
