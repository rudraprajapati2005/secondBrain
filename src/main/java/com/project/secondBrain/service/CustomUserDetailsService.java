package com.project.secondBrain.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.project.secondBrain.repository.*;
import com.project.secondBrain.entity.User;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository ur)
    {
        this.userRepository = ur;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User usr = userRepository.findByEmail(email)
            .orElseThrow(()->
            new UsernameNotFoundException("Username not found")
        );
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usr.getEmail())
                .password(usr.getPassword())
                .authorities("USER")
                .build();
    }
}
