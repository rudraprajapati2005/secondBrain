package com.project.secondBrain.service;
import com.project.secondBrain.repository.UserRepository;

import org.springframework.stereotype.Service;

import com.project.secondBrain.entity.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public String getCurrentUserEmail()
    {
        Authentication auth = SecurityContextHolder
                            .getContext()
                            .getAuthentication();
        System.out.println( "AUTH NAME = " + auth.getName());
        return auth.getName();
    }
}
