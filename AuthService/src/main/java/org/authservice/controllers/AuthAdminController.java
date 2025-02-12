package org.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.SignInRequestDTO;
import org.authservice.dto.SignUpAdminRequestDTO;
import org.authservice.service.AuthAdminService;
import org.authservice.response.Response;
import org.authservice.response.ResponseStatus;
import org.authservice.dto.SignInResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AuthAdminController {

    private final AuthAdminService authAdminService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Validated @RequestBody SignUpAdminRequestDTO registrationAdminRequestDTO) {
        try {
            authAdminService.registration(registrationAdminRequestDTO);
            return ResponseEntity.ok(new Response(ResponseStatus.SUCCESS, null));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }

    @GetMapping("/login")
    public ResponseEntity<Response> login(@Validated @RequestBody SignInRequestDTO loginRequestDTO) {
        try {
            String token = authAdminService.login(loginRequestDTO);
            return ResponseEntity.ok(new Response(ResponseStatus.SUCCESS, new SignInResponseDTO(token)));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }


    @GetMapping("/validate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> validate(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, user.getUsername()));
    }
}
