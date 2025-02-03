package org.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpUserRequestDTO {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 80, message = "Password must be between 8 and 80 characters")
    String password;

    @NotNull(message = "confirmPassword cannot be null")
    @Size(min = 8, max = 80, message = "Password must be between 8 and 80 characters")
    String confirmPassword;
}
