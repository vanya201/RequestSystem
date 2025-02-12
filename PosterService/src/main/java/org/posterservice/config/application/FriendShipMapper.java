package org.posterservice.config.application;

import org.common.models.User;
import org.mapstruct.Mapper;
import org.posterservice.dto.http.UserDTO;

import java.util.List;


@Mapper
public interface FriendShipMapper {
    List<UserDTO> toUserDTOList(List<User> users);
}