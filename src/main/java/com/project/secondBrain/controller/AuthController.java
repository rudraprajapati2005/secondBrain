package com.project.secondBrain.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.secondBrain.service.AuthService;
import com.project.secondBrain.entity.User;
import com.project.secondBrain.dto.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest rq )
    {
        return authService.register(rq);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req)
    {
        return authService.login(req);
    }
    
}
