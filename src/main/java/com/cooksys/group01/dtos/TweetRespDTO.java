package com.cooksys.group01.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetRespDTO {

    private Long id;

    private UserRespDTO author;

    private Timestamp posted;

    private String content;

    private TweetRespDTO inReplyTo;

    private TweetRespDTO repostOf;
}
