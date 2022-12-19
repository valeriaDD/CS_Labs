package com.web.webserver.cs.controller;

import com.web.webserver.cs.request.LoginRequest;
import com.web.webserver.cs.request.RegisterRequest;
import com.web.webserver.cs.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody RegisterRequest registerRequest) throws Exception {
        authService.register(registerRequest);
        return registerRequest.toString();
    }

    @PostMapping(
            value = "/login",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] createQrAfterLogging(@RequestBody LoginRequest loginRequest) throws AuthException {
        return authService.login(loginRequest);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmMfa(@RequestParam String code) throws Exception {
        return authService.confirm(code);
    }
}
