package org.authservice.service;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.SignInRequestDTO;
import org.authservice.dto.SignUpAdminRequestDTO;
import org.authservice.user.details.UserDetailsImpl;
import org.common.models.RoleState;
import org.common.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthAdminService {

    @Value("${app.admin.secret}")
    private String secret_register_key;

    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public void registration(SignUpAdminRequestDTO requestDTO) {
        if(!requestDTO.getSecret_key().equals(secret_register_key))
            throw new IllegalArgumentException("Secret key does not match");

        if(!requestDTO.getPassword().equals(requestDTO.getConfirmPassword()))
            throw new IllegalArgumentException("Password does not match");

        userService.create(User.builder()
                                .username(requestDTO.getUsername())
                                .password(passwordEncoder.encode(requestDTO.getPassword()))
                                .email(requestDTO.getEmail())
                                .roles(new HashSet<>(Set.of(
                                        roleService.getByRolState(RoleState.ADMIN),
                                        roleService.getByRolState(RoleState.USER))
                                )).build());
    }

    public String login(SignInRequestDTO loginRequestDTO) {
        var user = userService.getByUsername(loginRequestDTO.getUsername());

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid username or password");

        return jwtService.generateToken(new UserDetailsImpl(user));
    }

}
