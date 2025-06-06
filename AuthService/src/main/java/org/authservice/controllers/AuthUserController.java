package org.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.SignInRequestDTO;
import org.authservice.dto.SignUpUserRequestDTO;
import org.authservice.response.Response;
import org.authservice.response.ResponseStatus;
import org.authservice.dto.SignInResponseDTO;
import org.authservice.service.AuthUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class AuthUserController {
    private final AuthUserService authUserService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Validated @RequestBody SignUpUserRequestDTO registrationUserRequestDTO) {
        try {
            authUserService.register(registrationUserRequestDTO);
            return ResponseEntity.ok(new Response(ResponseStatus.SUCCESS, null));
        }catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Validated @RequestBody SignInRequestDTO loginRequestDTO) {
        try {
            String token = authUserService.login(loginRequestDTO);
            return ResponseEntity.ok(new Response(ResponseStatus.SUCCESS, new SignInResponseDTO(token)));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }


    @GetMapping("/validate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Response> validate(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, user.getUsername()));
    }

}
