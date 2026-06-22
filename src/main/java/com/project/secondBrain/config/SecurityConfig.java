package com.project.secondBrain.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.project.secondBrain.security.JwtAuthenticationFilter;
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuth;
    public SecurityConfig(JwtAuthenticationFilter j)
    {
        this.jwtAuth= j;
    }
    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity http) 
    throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> 
                    auth.requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
            
                )
                .addFilterBefore(
                    jwtAuth, UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement(session->

                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )   
                ;
        
        return http.build();
    
    }

     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
