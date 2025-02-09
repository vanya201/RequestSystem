package org.posterservice.services;


import lombok.RequiredArgsConstructor;
import org.common.models.User;
import org.posterservice.exception.UserNotFound;
import org.posterservice.repositories.UserSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchService {
    private final UserSearchRepository userSearchRepository;

    public User searchUserByName(String username) throws UserNotFound{
        return userSearchRepository.findByUsername(username)
                .orElseThrow(UserNotFound::new);
    }

    public List<User> searchFriendsByUser(User user, Pageable page) {
        return userSearchRepository.findByFriendsContains(user, page).getContent();
    }

    public List<User> searchUsersByName(String username, Pageable page) {
        Page<User> users = userSearchRepository.findByUsernameLike(username, page);
        return users.isEmpty() ?
                userSearchRepository.findSimilarUsernames(username, page).getContent()
                : users.getContent();
    }
}
