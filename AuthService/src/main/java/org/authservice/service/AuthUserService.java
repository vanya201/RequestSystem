package org.authservice.service;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.SignInRequestDTO;
import org.authservice.dto.SignUpUserRequestDTO;
import org.authservice.user.details.UserDetailsImpl;
import org.common.models.Role;
import org.common.models.RoleState;
import org.common.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public void registration(SignUpUserRequestDTO requestDTO) {

        if(!requestDTO.getPassword().equals(requestDTO.getConfirmPassword()))
            throw new IllegalArgumentException("Password does not match");

        userService.create(User.builder()
                                .username(requestDTO.getUsername())
                                .password(passwordEncoder.encode(requestDTO.getPassword()))
                                .email(requestDTO.getEmail())
                                .roles(Set.of(roleService.getByRolState(RoleState.USER)))
                                .build());
    }

    public String login(SignInRequestDTO loginRequestDTO) {
        var user = userService.getByUsername(loginRequestDTO.getUsername());

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid username or password");

        var adminRole = roleService.getByRolState(RoleState.ADMIN);

        if (user.getRoles().contains(adminRole))
            throw new IllegalArgumentException("Incorrect name");

        return jwtService.generateToken(new UserDetailsImpl(user));
    }

}
