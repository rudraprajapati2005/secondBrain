package com.project.secondBrain.controller;
import com.project.secondBrain.service.UserService;

import com.project.secondBrain.dto.CreateArchiveRequest;
import com.project.secondBrain.entity.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.secondBrain.dto.ArchiveResponse;
import com.project.secondBrain.service.ArchiveService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final ArchiveService archiveService;

    public UserController(UserService userService, ArchiveService archiveService) {
        this.userService = userService;
        this.archiveService = archiveService;
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    
    @PostMapping("/archives")
    public ArchiveResponse createArchive(@RequestBody CreateArchiveRequest archive) {
        return archiveService.saveArchive(archive);
    }
}
