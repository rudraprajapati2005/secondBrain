package com.project.secondBrain.service;
import com.project.secondBrain.repository.UserRepository;

import org.springframework.stereotype.Service;

import com.project.secondBrain.entity.User;
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
