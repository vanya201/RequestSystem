package org.posterservice.config.application;

import org.common.models.FriendRequest;
import org.common.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.posterservice.dto.http.FriendRequestDTO;
import org.posterservice.dto.http.UserDTO;

import java.util.List;


@Mapper
public interface FriendShipMapper {
    List<UserDTO> toUserDTOList(List<User> users);

    @Mapping(source = "sender.username", target = "senderName")
    FriendRequestDTO toFriendRequestDTO(FriendRequest friendRequest);
    List<FriendRequestDTO> toFriendRequestDTOList(List<FriendRequest> requests);
}