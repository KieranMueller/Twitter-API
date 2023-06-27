package com.cooksys.group01.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextRespDTO {
    
    private TweetRespDTO target;

    private List<TweetRespDTO> before;

    private List<TweetRespDTO> after;
}
