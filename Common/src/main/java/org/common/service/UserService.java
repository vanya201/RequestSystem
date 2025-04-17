package org.common.service;

import lombok.RequiredArgsConstructor;
import org.common.exceptions.EmailAlreadyExistsException;
import org.common.exceptions.UserNotFoundException;
import org.common.exceptions.UsernameAlreadyExistsException;
import org.common.model.User;
import org.common.repository.UserRepository;
import org.common.user.details.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername()))
            throw new UsernameAlreadyExistsException(user.getUsername());

        if (repository.existsByEmail(user.getEmail()))
            throw new EmailAlreadyExistsException(user.getEmail());
        return repository.save(user);
    }



    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }



    public UserDetailsImpl userDetails(String username) {
        return new UserDetailsImpl(getByUsername(username));
    }
}
