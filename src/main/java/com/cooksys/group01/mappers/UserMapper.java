package com.cooksys.group01.mappers;

import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CredentialMapper.class, ProfileMapper.class})
public interface UserMapper {

//    @Mapping(target="username", source="credentials.username")
    UserRespDTO entityToDTO(User user);

    User dtoToEntity(UserReqDTO user);

    List<UserRespDTO> entitiesToDTOs(List<User> users);

}
