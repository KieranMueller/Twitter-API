package com.cooksys.group01.mappers;

import com.cooksys.group01.dtos.HashtagDTO;
import com.cooksys.group01.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDTO entityToDTO(Hashtag hashtag);
    List<HashtagDTO> entitiesToDTOs(List<Hashtag> hashtags);
}
