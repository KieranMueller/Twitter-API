package com.cooksys.group01.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ContextRespDTO {
    private TweetRespDTO target;

    private List<TweetRespDTO> before;

    private List<TweetRespDTO> after;
}
