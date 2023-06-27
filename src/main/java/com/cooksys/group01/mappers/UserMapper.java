package com.cooksys.group01.mappers;

import com.cooksys.group01.dtos.UserReqDTO;
import com.cooksys.group01.dtos.UserRespDTO;
import com.cooksys.group01.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CredentialMapper.class, ProfileMapper.class})
public interface UserMapper {

    UserRespDTO entityToDTO(User user);

    User dtoToEntity(UserReqDTO user);

    List<UserRespDTO> entitiesToDTOs(List<User> users);

}
