package org.authservice.service;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.SignInRequestDTO;
import org.authservice.dto.SignUpAdminRequestDTO;
import org.authservice.user.details.UserDetailsImpl;
import org.authservice.validation.ValidationService;
import org.common.models.Role;
import org.common.models.RoleState;
import org.common.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthAdminService {

    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;



    public void register(SignUpAdminRequestDTO requestDTO) {
        validationService.validateSecretKey(requestDTO.getSecretKey());
        validationService.validatePasswordsConfirm(requestDTO.getPassword(), requestDTO.getConfirmPassword());
        User user = buildUser(requestDTO);
        userService.create(user);
    }



    public String login(SignInRequestDTO loginRequestDTO) {
        User user = userService.getByUsername(loginRequestDTO.getUsername());
        validationService.validatePassword(loginRequestDTO.getPassword(), user.getPassword());
        return jwtService.generateToken(new UserDetailsImpl(user));
    }



    private User buildUser(SignUpAdminRequestDTO requestDTO) {
        Set<Role> roles = Set.of(
                roleService.getByRolState(RoleState.ADMIN),
                roleService.getByRolState(RoleState.USER)
        );

        return User.builder()
                .username(requestDTO.getUsername())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .email(requestDTO.getEmail())
                .roles(roles)
                .build();
    }
}
