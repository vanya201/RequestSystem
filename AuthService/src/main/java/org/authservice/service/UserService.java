package org.authservice.service;

import lombok.RequiredArgsConstructor;
import org.authservice.repositories.UserRepository;
import org.authservice.user.details.UserDetailsImpl;
import org.common.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername()))
            throw new RuntimeException("Пользователь с таким именем уже существует");

        if (repository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Пользователь с таким email уже существует");
        return repository.save(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public UserDetailsImpl userDetails(String username) {
        return new UserDetailsImpl(getByUsername(username));
    }
}
