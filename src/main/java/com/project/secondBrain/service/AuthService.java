package com.project.secondBrain.service;

import org.springframework.stereotype.Service;

import com.project.secondBrain.dto.LoginRequest;
import com.project.secondBrain.dto.LoginResponse;
import com.project.secondBrain.dto.RegisterRequest;
import com.project.secondBrain.dto.UserResponse;
import com.project.secondBrain.entity.User;
import com.project.secondBrain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder ,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public LoginResponse login(LoginRequest loginRequest) {
       User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(()-> 
                new RuntimeException("Invalid email or password")
                );

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword()))
        {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(user.getEmail(),token);
    }

    

    public UserResponse register(RegisterRequest req){
      if(userRepository.findByEmail(req.getEmail()).isPresent())
      {
        throw new RuntimeException("Email already exsist");
      }

      User u = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(
                    passwordEncoder.encode(
                        req.getPassword()
                    )
                )
                .build();

         User savedUser = userRepository.save(u);
        
         return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}
