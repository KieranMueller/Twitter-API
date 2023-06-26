package com.cooksys.group01.dto;

import com.cooksys.group01.entity.embeddable.Credentials;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetReqDTO {

    private String content;

    private Credentials credentials;
}
