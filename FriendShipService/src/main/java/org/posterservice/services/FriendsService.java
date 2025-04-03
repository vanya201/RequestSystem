package org.posterservice.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.common.model.FriendShip;
import org.common.model.User;
import org.posterservice.repositories.FriendShipRepository;
import org.posterservice.repositories.UserSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final UserSearchRepository userSearchRepository;
    private final SearchUsersService searchUsersService;

    private final FriendShipRepository friendShipRepository;

    public List<User> searchFriendsByUser(UserDetails userDetails, Pageable page) {
        User user = searchUsersService.searchUserByName(userDetails.getUsername());
        return userSearchRepository.findByFriendsContains(user, page).getContent();
    }


    public boolean existsFriends(User user, User friend) {
        return friendShipRepository.existsByUser1AndUser2(user, friend);
    }


    @Transactional
    public void deleteFriend(UserDetails userDetails, String friendName) {
        User user = searchUsersService.searchUserByName(userDetails.getUsername());
        User friend = searchUsersService.searchUserByName(friendName);
        friendShipRepository.deleteByUser1AndUser2(user, friend);
        friendShipRepository.deleteByUser1AndUser2(friend, user);
    }



    @Transactional
    public void addFriend(UserDetails userDetails, String friendName) {
        User user = searchUsersService.searchUserByName(userDetails.getUsername());
        User friend = searchUsersService.searchUserByName(friendName);
        friendShipRepository.save(FriendShip.create(user, friend));
        friendShipRepository.save(FriendShip.create(friend, user));
    }



    public void addFriend(User user, User friend) {
        friendShipRepository.save(FriendShip.create(user, friend));
        friendShipRepository.save(FriendShip.create(friend, user));
    }
}
