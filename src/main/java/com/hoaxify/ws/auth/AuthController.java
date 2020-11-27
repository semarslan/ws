package com.hoaxify.ws.auth;

import com.hoaxify.ws.user.UserRepository;
import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/1.0/auth")
    AuthResponse handleAuthentication(@RequestBody Credentials credentials) {
        return authService.authenticate(credentials);
    }
}
