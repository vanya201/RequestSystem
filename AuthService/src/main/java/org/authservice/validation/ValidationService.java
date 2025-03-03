package org.authservice.validation;

import lombok.RequiredArgsConstructor;
import org.common.models.Role;
import org.common.models.RoleState;
import org.common.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.authservice.service.RoleService;


@Service
@RequiredArgsConstructor
public class ValidationService {

    @Value("${app.admin.secret}")
    private String secretRegisterKey;

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void validateSecretKey(String secretKey) {
        if (!secretKey.equals(secretRegisterKey))
            throw new IllegalArgumentException("Secret key does not match");
    }



    public void validatePasswordsConfirm(String password, String confirmPassword) {
        if (!password.equals(confirmPassword))
            throw new IllegalArgumentException("Passwords do not match");
    }



    public void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword))
            throw new IllegalArgumentException("Invalid username or password");

    }



    public void validateUserRole(User user, RoleState roleState) {
        Role role = roleService.getByRolState(roleState);
        if (user.getRoles().contains(role))
            throw new IllegalArgumentException("User has an invalid role: " + roleState);
    }




}
