package com.cooksys.group01.mappers;

import com.cooksys.group01.dtos.TweetRespDTO;
import com.cooksys.group01.entities.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    TweetRespDTO entityToDTO(Tweet tweet);

    List<TweetRespDTO> entitiesToDTOs(List<Tweet> tweets);

}
