package com.cooksys.group01.dtos;

import com.cooksys.group01.entities.embeddable.Credentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetReqDTO {

    private String content;

    private Credentials credentials;
}
