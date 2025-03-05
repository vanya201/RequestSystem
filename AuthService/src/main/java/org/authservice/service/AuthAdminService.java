package org.authservice.service;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.SignInRequestDTO;
import org.authservice.dto.SignUpAdminRequestDTO;
import org.authservice.validation.ValidationService;
import org.common.model.Role;
import org.common.model.RoleState;
import org.common.model.User;
import org.common.service.JwtService;
import org.common.service.UserService;
import org.common.user.details.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthAdminService {

    private final JwtService jwtService;
    private final UserService userService;
    private final RoleService roleService;
    private final ValidationService validationService;


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
                .password(requestDTO.getPassword())
                .email(requestDTO.getEmail())
                .roles(roles)
                .build();
    }
}
