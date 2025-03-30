package org.posterservice.config.application;

import org.common.model.FriendRequest;
import org.common.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.posterservice.dto.http.FriendRequestDTO;
import org.posterservice.dto.http.UserDTO;
import org.posterservice.model.FriendRequestCache;
import org.posterservice.notify.impl.friend.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.SendFriendRequestDTO;

import java.util.List;


@Mapper
public interface FriendShipMapper {
    List<UserDTO> toUserDTOList(List<User> users);

    @Mapping(source = "sender.username", target = "senderName")
    FriendRequestDTO toFriendRequestDTO(FriendRequest friendRequest);
    List<FriendRequestDTO> toFriendRequestDTOList(List<FriendRequest> requests);

    @Mapping(source = "sender.username", target = "sender")
    @Mapping(source = "receiver.username", target = "accepter")
    AcceptFriendRequestDTO toAcceptFriendRequestDTO(FriendRequest friendRequest);

    @Mapping(source = "sender.username", target = "requester")
    @Mapping(source = "receiver.username", target = "decliner")
    DeclineFriendRequestDTO toDeclineFriendRequestDTO(FriendRequest friendRequest);

    @Mapping(source = "sender.username", target = "sender")
    @Mapping(source = "receiver.username", target = "recipient")
    SendFriendRequestDTO toSendFriendRequestDTO(FriendRequest friendRequest);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "sender.username", target = "sender")
    @Mapping(source = "receiver.username", target = "receiver")
    FriendRequestCache toFriendRequestCache(FriendRequest friendRequest);
}